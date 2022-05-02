package com.csuft.ucenter.controller.portal;


import com.csuft.common.response.R;
import com.csuft.ucenter.service.IUcUserService;
import com.csuft.ucenter.vo.RegisterVo;
import com.csuft.ucenter.vo.loginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yc
 * @since 2022-04-17
 */
@RestController
public class UcUserController {

    @Autowired
    private IUcUserService userService;

    /*
     *  /uc/user/test
     */

    @PostMapping( "/uc/user/register")
    public R register(@RequestParam("mailCode") String mailCode, @RequestBody RegisterVo registerVo) {
        return userService.addUser(mailCode, registerVo);
    }

    @PostMapping("/uc/user/login")
    public R login(@RequestBody loginVo loginVo) {
        return userService.doLogin(loginVo);
    }

    @GetMapping("/uc/user/token")
    public R checkToken() {
        return userService.checkToken();
    }
}

