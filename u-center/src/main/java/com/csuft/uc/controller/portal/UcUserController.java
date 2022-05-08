package com.csuft.uc.controller.portal;


import com.csuft.common.response.R;
import com.csuft.uc.entity.UcUser;
import com.csuft.uc.service.IUcUserService;
import com.csuft.uc.vo.LoginVo;
import com.csuft.uc.vo.RegisterVo;
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
    public R login(@RequestBody LoginVo loginVo) {
        return userService.doLogin(loginVo);
    }

    @GetMapping("/uc/user/token")
    public R checkToken() {
        return userService.checkToken();
    }

    @GetMapping("/uc/user/logout")
    public R logout() {
        return userService.doLogout();
    }

    @PutMapping("/uc/user/reset")
    public  R resetPassword(@RequestParam("mailCode") String mailCode, @RequestBody RegisterVo registerVo){
        return  userService.resetPassword(mailCode,registerVo);
    }

    @PutMapping("/uc/user/updateUserInfo")
    public  R updateUserInfo(@RequestBody UcUser user){
        return  userService.updateUserInfo(user);
    }
}

