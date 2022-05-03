package com.csuft.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuft.common.response.R;
import com.csuft.common.utils.Constants;
import com.csuft.common.utils.RedisUtil;
import com.csuft.common.utils.TextUtils;
import com.csuft.uc.base.BaseService;
import com.csuft.uc.entity.UcUserInfo;
import com.csuft.uc.entity.UserEx;
import com.csuft.uc.mapper.UserExMapper;
import com.csuft.uc.service.IUcUserInfoService;
import com.csuft.uc.service.IUserExService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserExServiceImpl  extends BaseService<UserExMapper, UserEx> implements IUserExService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IUcUserInfoService userInfoService;

    /**
     * 发送邮箱验证码
     *
     * @param mailAddress
     * @param mustRegister
     * @return
     */
    @Override
    public R sendMailCode(String mailAddress, boolean mustRegister) {
        log.info("mail address {} must register {}" + mailAddress, mustRegister);
        //先检查数据
        if (TextUtils.isEmpty(mailAddress)) {
            return R.FAILED("邮箱地址不可为空");
        }
        //对邮箱地址进行校验
        //TODO：
        String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mailAddress);
        if (!m.matches()) {
            return R.FAILED("请检查邮箱格式是否正确");
        }
        QueryWrapper<UcUserInfo> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("email", mailAddress);
        infoQueryWrapper.select("id");
        UcUserInfo ucUserInfo = userInfoService.getBaseMapper().selectOne(infoQueryWrapper);
        //根据比较检查该邮箱是否必须注册
        if (!mustRegister && ucUserInfo != null) {
            return R.FAILED("邮箱已被注册");
        }
        if (mustRegister && ucUserInfo == null) {
            return R.FAILED("邮箱已被注册");
        }
        //防止恶意频繁调用,通过ip， 邮箱地址
        String remoteAddr = getRequest().getRemoteAddr();
        //TODO:log
        remoteAddr = remoteAddr.replaceAll(":", "-");
        String ipKey = Constants.User.KEY_MAIL_CODE_IP + remoteAddr;
        //判断ip是否有多次请求 15次
        String ipTime = (String) redisUtil.get(ipKey);
        if (!TextUtils.isEmpty(ipTime)) {
            int i = Integer.parseInt(ipTime);
            if (i > 10000) {
                return R.FAILED("请不要重复发送请求，请稍后再试");
            } else {
                i++;
                redisUtil.set(ipKey, String.valueOf(i), Constants.TimeSecond.TWO_HOUR);
            }
        } else {
            redisUtil.set(ipKey, "1", Constants.TimeSecond.TWO_HOUR);
        }
        String addressKey = Constants.User.KEY_MAIL_CODE_ADDRESS + mailAddress;
        //判断地址是否有多次请求 15次
        String addressTime = (String) redisUtil.get(addressKey);
        if (!TextUtils.isEmpty(addressTime)) {
            int i = Integer.parseInt(addressTime);
            if (i > 102131) {
                return R.FAILED("重复发送过多请求，请稍后再试");
            } else {
                i++;
                redisUtil.set(addressKey, String.valueOf(i), Constants.TimeSecond.TWO_HOUR);
            }
        } else {
            redisUtil.set(addressKey, "1", Constants.TimeSecond.TWO_HOUR);
        }

        //产生验证码，记录验证码
        Random random = new Random();
        int mailCode = random.nextInt(999999);
        if (mailCode < 100001) {
            mailCode += 100001;
        }
        log.info("mailCode ====>" + mailCode);
        //
        redisUtil.set(Constants.User.KEY_MAIL_CODE + mailAddress, String.valueOf(mailCode), Constants.TimeSecond.FIVE_MIN);
        // 发送验证码
//        EmailSender.sendMailCode(mailAddress, "这是发送的内容：" + mailCode + "5分钟内有效");
        return R.SUCCESS("验证码发送成功", mailCode);
    }
}
