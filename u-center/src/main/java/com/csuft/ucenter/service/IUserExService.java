package com.csuft.ucenter.service;

import com.csuft.common.response.R;

public interface IUserExService {
    R sendMailCode(String mailAddress,boolean mustRegister);
}
