package com.csuft.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.csuft.common.response.R;
import com.csuft.common.utils.Constants;
import com.csuft.common.utils.RedisUtil;
import com.csuft.common.utils.TextUtils;
import com.csuft.ucenter.base.BaseService;
import com.csuft.ucenter.entity.UcRefreshToken;
import com.csuft.ucenter.entity.UcUser;
import com.csuft.ucenter.entity.UcUserInfo;
import com.csuft.ucenter.mapper.UcUserMapper;
import com.csuft.ucenter.service.IUcTokenService;
import com.csuft.ucenter.service.IUcUserInfoService;
import com.csuft.ucenter.service.IUcUserService;
import com.csuft.ucenter.utils.ClaimsUtil;
import com.csuft.ucenter.utils.CookieUtils;
import com.csuft.ucenter.utils.JwtUtil;
import com.csuft.ucenter.vo.RegisterVo;
import com.csuft.ucenter.vo.UserVo;
import com.csuft.ucenter.vo.loginVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
        UcUser targetUser = new UcUser();
        targetUser.setAvatar(Constants.User.DEFAULT_AVATAR);
        targetUser.setUserName(nickname);
        targetUser.setPassword(targetPassword);
        targetUser.setType(registerVo.type);
        targetUser.setStatus(Constants.User.DEFAULT_STATUS);
        // 入库
        UcUserInfo targetuserInfo = new UcUserInfo();
        targetuserInfo.setEmail(mail);
        targetuserInfo.setUserId(targetUser.getId());
        targetuserInfo.setSalt(IdWorker.getIdStr());
        iUserInfoService.save(targetuserInfo);
        this.baseMapper.insert(targetUser);
        //返回注册结果
        log.info("mailCode ===>{}，register ===>{} " + mailCode, registerVo);
        return R.SUCCESS("注册成功");
    }

    @Override
    public R doLogin(loginVo loginVo) {
        log.info("mailCode ===>{}，UserVo ===>{} " + loginVo);
        //校验数据
        String name = loginVo.getName();
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
        UserVo userByAccount = this.baseMapper.getUserByAccount(name);
        if (userByAccount == null) {
            return R.FAILED("账号或者密码错误");
        }

        // 对比密码
        boolean matches = bCryptPasswordEncoder.matches(password, userByAccount.getPassword());
        if (!matches) {
            return R.FAILED("账号或者密码不正确");
        }
        // 创建token
        createToken(userByAccount);
        // 返回登录结果
        return R.SUCCESS("注册成功");
    }


    /**
     * 创建token
     *
     * @param userByAccount
     */
    private void createToken(UserVo userByAccount) {
        Map<String, Object> claims = ClaimsUtil.user2Claims(userByAccount);

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
                QueryWrapper<UcRefreshToken> refreshTokenQueryWrapper = new QueryWrapper<>();
                refreshTokenQueryWrapper.eq("user_id", userId);
                tokenService.remove(refreshTokenQueryWrapper);
                //创建新的
                UcUser user = getById(userId);
                UserVo userVo = new UserVo();
                userVo.setId(userId);
                userVo.setUserName(user.getUserName());
                userVo.setAvatar(userVo.getAvatar());
                userVo.setStatus(userVo.getStatus());
                userVo.setSalt(userVo.getSalt());
                createToken(userVo);
                return R.SUCCESS("已登录").setData(userVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.NOT_LOGIN();
    }

}
