package com.csuft.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csuft.common.response.R;
import com.csuft.uc.entity.UcUser;
import com.csuft.uc.vo.LoginVo;
import com.csuft.uc.vo.RegisterVo;

/**
 * <p>
 * uc_user  服务类
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
public interface IUcUserService extends IService<UcUser> {

    R addUser(String mailCode, RegisterVo registerVo);

    R doLogin(LoginVo loginVo);

    R checkToken();

    R doLogout();

    R resetPassword(String mailCode, RegisterVo registerVo);

    R listUser(int page, String phone, String email, String userName, String userId, String status);

    R disableUser(String userId);

    R resetPasswordByUid(String userId, RegisterVo registerVo);

    R initAdminAccount(RegisterVo registerVo);

    R initTeacherAccount(RegisterVo registerVo);

    R updateUserInfo(UcUser user);
}
