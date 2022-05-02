package com.csuft.ucenter.controller.portal;

import com.csuft.common.response.R;
import com.csuft.ucenter.service.IUserExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 扩展接口
 *   发送与邮箱验证码
 *   检查邮箱是否有注册
 *   检查用户名是否有重复
 *
 */
@RestController
public class UserExController {

    @Autowired
    private IUserExService iUserExService;

    /**
     *
     * @param mailAddress 邮箱地址
     * @return
     */
    @GetMapping("/uc/user/ex/mail-code")
    public R sendMailCode(@RequestParam("mail") String mailAddress){
        return iUserExService.sendMailCode(mailAddress,false);
    }
}
