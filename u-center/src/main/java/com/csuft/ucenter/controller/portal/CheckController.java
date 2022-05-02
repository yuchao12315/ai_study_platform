package com.csuft.ucenter.controller.portal;

import com.csuft.common.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 专门去获取： Check
 *    获取图灵验证 captcha (get)
 *    检查当前手机号码是否有被注册 phone_num (get)
 *    获取手机验证码 phone_verify_code （get）
 *    检查当前邮箱是否有被注册 email (get)
 *    获取邮箱验证码 email_verify_code (get)
 *    检查昵称是否有被使用 nickname (get)
 *    提交注册信息 sign_up (post)
 */
@RestController
public class CheckController {

    @GetMapping("/uc/check/captcha")
    public R getCaptchaCode(){
        return  null;
    }
    @GetMapping("/uc/check/phone_num")
    public R getPhoneNum(){
        return  null;
    }

    @GetMapping("/uc/check/phone_verify_code")
    public R getPhoneVerifyCode(){
        return  null;
    }

    @GetMapping("/uc/check/email")
    public R getEmail(){
        return  null;
    }
    @GetMapping("/uc/check/email_verify_code")
    public R getEmailVerifyCode(){
        return  null;
    }
}
