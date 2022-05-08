package com.csuft.uc.controller.admin;

import com.csuft.common.response.R;
import com.csuft.uc.service.impl.UcUserServiceImpl;
import com.csuft.uc.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户列表（有条件，搜索）
 * 用户禁止
 * 重置密码
 */
@RestController
public class UserAdminController {

    @Autowired
    private UcUserServiceImpl userService;

    @GetMapping("/uc/admin/user/list/{page}")
    public R listUser(@PathVariable("page") int page,
                      @RequestParam(value = "phone", required = false) String phone,
                      @RequestParam(value = "email", required = false) String email,
                      @RequestParam(value = "name", required = false) String userName,
                      @RequestParam(value = "id", required = false) String userId,
                      @RequestParam(value = "status", required = false) String status) {
        return userService.listUser(page, phone, email, userName, userId, status);
    }

    @PutMapping("/uc/admin/user/disable/{userId}")
    public R disableUser(@PathVariable("userId") String userId) {
        return userService.disableUser(userId);
    }

    @PutMapping("/uc/admin/user/reset/{userId}")
    public R resetPassword(@PathVariable("userId") String userId,
                           @RequestBody RegisterVo registerVo) {
        return userService.resetPasswordByUid(userId, registerVo);
    }

    @PostMapping("/uc/admin/user/init")
    public R initAdminAccount(@RequestBody RegisterVo registerVo) {
        return userService.initAdminAccount(registerVo);
    }

    @PostMapping("/uc/teacher/user/init")
    public R initTeacherAccount(@RequestBody RegisterVo registerVo) {
        return userService.initTeacherAccount(registerVo);
    }
}
