package com.csuft.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csuft.common.response.R;
import com.csuft.ucenter.entity.UcUser;
import com.csuft.ucenter.vo.RegisterVo;
import com.csuft.ucenter.vo.loginVo;

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

    R doLogin(loginVo loginVo);

    R checkToken();
}
