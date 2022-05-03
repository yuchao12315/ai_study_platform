package com.csuft.uc.service;

import com.csuft.common.response.R;

public interface IUserExService {
    R sendMailCode(String mailAddress,boolean mustRegister);
}
