package com.csuft.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.csuft.common.response.R;
import com.csuft.common.utils.Constants;
import com.csuft.common.utils.RedisUtil;
import com.csuft.common.utils.TextUtils;
import com.csuft.uc.base.BaseService;
import com.csuft.uc.entity.*;
import com.csuft.uc.mapper.UcUserMapper;
import com.csuft.uc.service.IUcTokenService;
import com.csuft.uc.service.IUcUserInfoService;
import com.csuft.uc.service.IUcUserService;
import com.csuft.uc.utils.ClaimsUtil;
import com.csuft.uc.utils.CookieUtils;
import com.csuft.uc.utils.JwtUtil;
import com.csuft.uc.vo.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * uc_user  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Slf4j
@Service
public class UcUserServiceImpl extends BaseService<UcUserMapper, UcUser> implements IUcUserService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IUcUserInfoService iUserInfoService;
    @Autowired
    private IUcTokenService tokenService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UcTokenServiceImpl tokenServiceImpl;

    @Autowired
    private UcSettingsServiceImpl settingsService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    /**
     * 添加用户
     *
     * @param mailCode
     * @param registerVo
     * @return
     */
    @Override
    public R addUser(String mailCode, RegisterVo registerVo) {
        String mail = registerVo.getMail();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (TextUtils.isEmpty(mail)) {
            return R.FAILED("注册邮箱不可为空");
        }
        if (TextUtils.isEmpty(nickname)) {
            return R.FAILED("昵称不可为空");
        }
        if (TextUtils.isEmpty(password)) {
            return R.FAILED("密码不可为空");
        }
        //对数据进行校验
        String mailCodeRecord = (String) redisUtil.get(Constants.User.KEY_MAIL_CODE + mail);
        if (TextUtils.isEmpty(mailCodeRecord) || !mailCodeRecord.equals(mailCode)) {
            return R.FAILED("邮箱验证码不正确");
        }
        //MD5校验
        if (password.length() != 32) {
            return R.FAILED("请用MD5摘要算法进行演算");
        }
        // 判断邮箱死否有被注册
        QueryWrapper<UcUserInfo> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("email", mail);
        infoQueryWrapper.select("id");
        UcUserInfo ucUserInfo = iUserInfoService.getBaseMapper().selectOne(infoQueryWrapper);
        if (ucUserInfo != null) {
            return R.FAILED("邮箱已被注册");
        }
        // 判断用户名是否被使用过
        QueryWrapper<UcUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", nickname);
        userQueryWrapper.select("id");
        UcUser user = this.getBaseMapper().selectOne(userQueryWrapper);
        if (user != null) {
            return R.FAILED("用户名已被使用");
        }
        // 加密
        String targetPassword = bCryptPasswordEncoder.encode(password);
        //设置默认头像和状态
        String salt = IdWorker.getIdStr();
        UcUser targetUser = new UcUser();
        targetUser.setAvatar(Constants.User.DEFAULT_AVATAR);
        targetUser.setEmail(mail);
        targetUser.setUserName(nickname);
        targetUser.setUserId(IdWorker.getIdStr());
        targetUser.setPassword(targetPassword);
        targetUser.setType(registerVo.type);
        targetUser.setSalt(salt);
        targetUser.setStatus(Constants.User.DEFAULT_STATUS);
        save(targetUser);
        // 入库
        UcUserInfo targetuserInfo = new UcUserInfo();
        targetuserInfo.setEmail(mail);
        targetuserInfo.setUserId(targetUser.getUserId());
        targetuserInfo.setSalt(salt);
        iUserInfoService.save(targetuserInfo);
        //返回注册结果
        log.info("mailCode ===>{}，register ===>{} " + mailCode, registerVo);
        if (registerVo.type == 2) {
            initTeacherAccount(registerVo);
        } else if (registerVo.type == 3) {
            initAdminAccount(registerVo);
        }
        return R.SUCCESS("注册成功");
    }

    @Override
    public R doLogin(LoginVo loginVo) {
        log.info("mailCode ===>{}，UserVo ===>{} " + loginVo);
        //校验数据
        String name = loginVo.getUserName();
        if (TextUtils.isEmpty(name)) {
            return R.FAILED("账号不可为空");
        }
        String password = loginVo.getPassword();
        if (TextUtils.isEmpty(password)) {
            return R.FAILED("密码不可为空");
        }
        if (password.length() != 32) {
            return R.FAILED("请使用MD5算法");
        }

        // 查询用户
        UcUser userByAccount = this.baseMapper.getUserByAccount(name);
        if (userByAccount == null) {
            return R.FAILED("账号或者密码错误");
        }
        if (Constants.User.DISABLE_STATUS.equals(userByAccount.getStatus())) {
            return R.FAILED("该账号已经被禁用");
        }
        // 对比密码
        boolean matches = bCryptPasswordEncoder.matches(password, userByAccount.getPassword());
        if (!matches) {
            return R.FAILED("账号或者密码不正确");
        }
        // 创建token
        createToken(userByAccount);
        // 返回登录结果
        UserVo userVo = new UserVo();
        userVo.setUserName(userByAccount.getUserName());
        userVo.setAvatar(userByAccount.getAvatar());
        userVo.setLev(userByAccount.getLev());
        return R.SUCCESS("登录成功").setData(userVo);
    }


    /**
     * 创建token
     *
     * @param userByAccount
     */
    private void createToken(UcUser userByAccount) {
        //删除当前用户的refreshToken  后面会重新创建
        QueryWrapper<UcRefreshToken> refreshTokenQueryWrapper = new QueryWrapper<>();
        refreshTokenQueryWrapper.eq("user_id", userByAccount.getId());
        tokenService.remove(refreshTokenQueryWrapper);
        //把用户角色查出来
        UserVo userVo = this.baseMapper.getUserVo(userByAccount.getId());

        Map<String, Object> claims = ClaimsUtil.user2Claims(userVo);

        String token = JwtUtil.createToken(claims, Constants.Millions.TWO_HOUR, userByAccount.getSalt());
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());
        String refreshToken = JwtUtil.createRefreshToken(userByAccount.getId(), Constants.Millions.MONTH, userByAccount.getSalt());
        //入库
        redisUtil.set(Constants.User.KEY_TOKEN + tokenKey, token, Constants.TimeSecond.TWO_HOUR);

        CookieUtils.setUpCookie(getResponse(), Constants.User.KEY_STUDY_TOKEN, tokenKey);

        UcRefreshToken targetRefreshToken = new UcRefreshToken();
        targetRefreshToken.setRefreshtoken(refreshToken);
        targetRefreshToken.setTokenkey(tokenKey);
        targetRefreshToken.setUserId(userByAccount.getId());
        tokenService.save(targetRefreshToken);
        log.info("refreshtoken 创建成功");
        //保存盐值salt
        String salt = userByAccount.getSalt();
        redisUtil.set(Constants.User.KEY_SALT + tokenKey
                , salt, Constants.TimeSecond.DAY);
    }

    /**
     * 解析token
     *
     * @return
     */
    @Override
    public R checkToken() {
        String tokenKey = CookieUtils.getCookie(getRequest(), Constants.User.KEY_STUDY_TOKEN);
        if (TextUtils.isEmpty(tokenKey)) {
            return R.NOT_LOGIN();
        }
        //先去redis拿数据
        String token = (String) redisUtil.get(Constants.User.KEY_TOKEN + tokenKey);
        String salt = (String) redisUtil.get(Constants.User.KEY_SALT + tokenKey);
        if (TextUtils.isEmpty(salt)) {
            return R.NOT_LOGIN();
        }
        if (!TextUtils.isEmpty(token)) {
            try {
                Claims claims = JwtUtil.parseJWT(token, salt);
                UserVo userVo = ClaimsUtil.claims2User(claims);
                return R.SUCCESS("已登录").setData(userVo);
            } catch (Exception e) {
                e.printStackTrace();
                //走检查refreshtoken
                return checkRefreshToken(tokenKey, salt);
            }
        } else {
            return checkRefreshToken(tokenKey, salt);
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public R doLogout() {
        String tokenKey = CookieUtils.getCookie(getRequest(), Constants.User.KEY_STUDY_TOKEN);
        if (TextUtils.isEmpty(tokenKey)) {
            return R.NOT_LOGIN();
        }
        //删除
        redisUtil.del(Constants.User.KEY_TOKEN + tokenKey);
        QueryWrapper<UcRefreshToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token_key", tokenKey);
        tokenService.remove(queryWrapper);
        redisUtil.del(Constants.User.KEY_SALT + tokenKey);
        //cookie
        CookieUtils.setUpCookie(getResponse(), Constants.User.KEY_STUDY_TOKEN, "");
        return R.SUCCESS("退出登录成功");
    }

    /**
     * 重设密码
     *
     * @param mailCode
     * @param registerVo
     * @return
     */
    @Override
    public R resetPassword(String mailCode, RegisterVo registerVo) {
        // 检查数据
        String mail = registerVo.getMail();
        if (TextUtils.isEmpty(mail)) {
            return R.FAILED("邮箱不可为空");
        }
        String password = registerVo.getPassword();
        if (TextUtils.isEmpty(password)) {
            return R.FAILED("密码不可为空");
        }
        if (password.length() != 32) {
            return R.FAILED("请使用MD5算法");
        }
        //先检查邮箱验证码是否正确，
        String mailCodeRecord = (String) redisUtil.get(Constants.User.KEY_MAIL_CODE + mail);
        if (mailCodeRecord == null || !mailCodeRecord.equals(mailCode)) {
            return R.FAILED("邮箱验证码不正确");
        }
        // 更新数据
        UcUser user = this.baseMapper.getUserByAccount(mail);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        this.baseMapper.updateById(user);
        // 退出登录
        doLogout();
        // 返回结果
        return R.SUCCESS("重置密码成功");
    }

    /**
     * 获取用户列表
     *
     * @param page
     * @param phone
     * @param email
     * @param userName
     * @param userId
     * @param status
     * @return
     */
    @Override
    public R listUser(int page,
                      String phone, String email,
                      String userName, String userId,
                      String status) {
        //检查页码 不过小，
        page = checkPage(page);
        int size = Constants.DEFAULT_SIZE;
        int offset = (page - 1) * size;
        List<UserAdminVo> userAdminVo = this.baseMapper.listUser(size, offset, page, phone, email, userName, userId, status);
        log.info("===>" + userAdminVo);
        //有总数量
        PageVo<UserAdminVo> pageVo = new PageVo<>();
        pageVo.setList(userAdminVo);
        pageVo.setCurrentPage(page);
        pageVo.setListSize(size);
        pageVo.setHasPrePage(page != 1);
        //总数 总页数 是否下一页
        //总数查询
        //总页数 =总数/size
        Long totalCount = this.baseMapper.listUserCount(phone, email, userName, userId, status);
        pageVo.setTotalCount(totalCount);
        //计算页数
        float tempTotalPage = (totalCount * 1.0f / size) + 0.49f;
        int totalPage = Math.round(tempTotalPage);
        pageVo.setTotalPage(totalPage);
        pageVo.setHasNextPage(page != totalPage);
        log.info("===>" + pageVo);
        return R.SUCCESS("查询用户列表成功").setData(pageVo);
    }

    /**
     * 禁止用户
     * 用户是否存在
     * 修改用户状态
     * 让用户退出登录
     * 登录代码检查状态
     *
     * @param userId
     * @return
     */
    @Override
    public R disableUser(String userId) {
        QueryWrapper<UcUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        UcUser user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            return R.FAILED("用户不存在");
        }
        user.setStatus(Constants.User.DISABLE_STATUS);
        this.baseMapper.updateById(user);
        //让用户退出登录,不能直接调用logout
        doLogoutByUid(user.getId());
//        CookieUtils.setUpCookie(getResponse(), Constants.User.KEY_STUDY_TOKEN, "");
        return R.SUCCESS("已经禁用当前用户");
    }

    private void doLogoutByUid(String userId) {
        //通过userid查询相关数据，如何删除
        QueryWrapper<UcRefreshToken> tokenQueryWrapper = new QueryWrapper<>();
        tokenQueryWrapper.eq("user_id", userId);

        UcRefreshToken refreshToken = tokenServiceImpl.getOne(tokenQueryWrapper);
        if (refreshToken != null) {
            //删除
            String tokenkey = refreshToken.getTokenkey();
            redisUtil.del(Constants.User.KEY_TOKEN + tokenkey);

            redisUtil.del(Constants.User.KEY_SALT + tokenkey);
            //cookie
            tokenServiceImpl.remove(tokenQueryWrapper);
        }
    }

    /**
     * 管理员 重置密码
     * 查询用户是否存在
     * 对密码进行判断处理
     * 修改用户密码
     * 让用户退出登录
     *
     * @param userId
     * @param registerVo
     * @return
     */
    @Override
    public R resetPasswordByUid(String userId, RegisterVo registerVo) {
        QueryWrapper<UcUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UcUser user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            return R.FAILED("用户不存在");
        }
        if (TextUtils.isEmpty(registerVo.getPassword()) || registerVo.getPassword().length() != 32) {
            return R.FAILED("密码格式不正确");
        }
        String encodePwd = bCryptPasswordEncoder.encode(registerVo.getPassword());
        user.setPassword(encodePwd);
        this.baseMapper.updateById(user);
        //让用户退出登录
        doLogoutByUid(userId);
        log.info("用户密码重置成功");
        return R.SUCCESS("用户密码重置成功");
    }

    /**
     * 创建管理员
     * 数据检查（也包含检查是否已被初始化过了）
     * 密码加密
     * 超级管理员 角色 初始化
     * 修改设置项 表示已经初始化了
     *
     * @param registerVo
     * @return
     */
    @Override
    public R initAdminAccount(RegisterVo registerVo) {
        QueryWrapper<UcSettings> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`key`", Constants.User.KEY_ADMIN_INIT_STATE);
        UcSettings one = settingsService.getOne(queryWrapper);
        if (one != null) {
            return R.FAILED("已初始化过了");
        }
        if (TextUtils.isEmpty(registerVo.getNickname())) {
            return R.FAILED("管理员名称不存在");
        }

        if (TextUtils.isEmpty(registerVo.getPassword()) || registerVo.getPassword().length() != 32) {
            return R.FAILED("管理员密码不存在或者格式错误");
        }
        UcUser user = new UcUser();
        user.setPassword(bCryptPasswordEncoder.encode(registerVo.getPassword()));
        user.setUserName((registerVo.getNickname()));
        String salt = IdWorker.getIdStr();
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setUserId(IdWorker.getIdStr());
        user.setType(registerVo.type);
        user.setSalt(salt);
        user.setStatus(Constants.User.DEFAULT_STATUS);
        save(user);
        // 入库
        UcUserInfo targetuserInfo = new UcUserInfo();
        targetuserInfo.setUserId(user.getUserId());
        targetuserInfo.setSalt(salt);
        iUserInfoService.save(targetuserInfo);
        //添加管理员
        Role role = new Role();
        role.setLabel(Constants.User.SUPER_ROLE_LABEL);
        role.setName(Constants.User.SUPER_ROLE_NAME);
        roleService.save(role);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);
        log.info("超级管理员创建成功");
        //修改设置
        UcSettings ucSettings = new UcSettings();
        ucSettings.setKey(Constants.User.KEY_ADMIN_INIT_STATE);
        ucSettings.setValue("1");
        settingsService.save(ucSettings);
        log.info("超级管理员存储成功");
        return R.SUCCESS("超级管理员创建成功");
    }

    @Override
    public R initTeacherAccount(RegisterVo registerVo) {
        QueryWrapper<UcSettings> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`key`", Constants.User.KEY_TEACHER_INIT_STATE);
        UcSettings settings = settingsService.getOne(queryWrapper);
        if (settings != null) {
            return R.FAILED("已初始化过了");
        }
        if (TextUtils.isEmpty(registerVo.getNickname())) {
            return R.FAILED("管理员名称不存在");
        }

        if (TextUtils.isEmpty(registerVo.getPassword()) || registerVo.getPassword().length() != 32) {
            return R.FAILED("管理员密码不存在或者格式错误");
        }
        UcUser user = new UcUser();
        user.setPassword(bCryptPasswordEncoder.encode(registerVo.getPassword()));
        user.setUserName((registerVo.getNickname()));
        String salt = IdWorker.getIdStr();
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setUserId(IdWorker.getIdStr());
        user.setType(registerVo.type);
        user.setSalt(salt);
        user.setStatus(Constants.User.DEFAULT_STATUS);
        save(user);
        // 入库
        UcUserInfo targetuserInfo = new UcUserInfo();
        targetuserInfo.setUserId(user.getUserId());
        targetuserInfo.setSalt(salt);
        iUserInfoService.save(targetuserInfo);
        //添加管理员
        Role role = new Role();
        role.setLabel(Constants.User.TEACHER_ROLE_LABEL);
        role.setName(Constants.User.TEACHER_ROLE_NAME);
        roleService.save(role);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);
        log.info("老师创建成功");
        //修改设置
        UcSettings ucSettings = new UcSettings();
        ucSettings.setKey(Constants.User.KEY_TEACHER_INIT_STATE);
        ucSettings.setValue("1");
        settingsService.save(ucSettings);
        log.info("老师存储成功");
        return R.SUCCESS("老师创建成功");
    }

    @Override
    public R updateUserInfo(UcUser user) {
        String userName = user.getUserName();
        String avatar = user.getAvatar();
        String sex = user.getSex();
        //设置修改的数据
        //根据条件来更新数据
        UcUser targetUser = new UcUser();
        targetUser.setAvatar(avatar);
        targetUser.setSex(sex);
        //封装数据
        //更新条件
        QueryWrapper<UcUser> queryWrapper = new QueryWrapper<>();
        //封装条件
        queryWrapper.eq("user_name", userName);
        this.baseMapper.update(targetUser, queryWrapper);
        return R.SUCCESS("个人信息更新成功");
    }


    /**
     * 从数据库中找到RefreshToken
     *
     * @param tokenKey
     * @return
     */
    private R checkRefreshToken(String tokenKey, String salt) {
        QueryWrapper<UcRefreshToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token_key", tokenKey);
        queryWrapper.select("refresh_token", "user_id");
        UcRefreshToken refreshToken = tokenService.getOne(queryWrapper);
        if (refreshToken != null) {
            try {
                JwtUtil.parseJWT(refreshToken.getRefreshtoken(), salt);
                String userId = refreshToken.getUserId();
                //先删除原来的
                redisUtil.del(Constants.User.KEY_TOKEN + tokenKey);

                //创建新的
                UcUser user = getById(userId);
                UserVo userVo = new UserVo();
                userVo.setId(userId);
                userVo.setUserName(user.getUserName());
                userVo.setAvatar(user.getAvatar());
                userVo.setStatus(user.getStatus());
                userVo.setSex(user.getSex());
                createToken(user);
                return R.SUCCESS("已登录").setData(userVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.NOT_LOGIN();
    }

}
