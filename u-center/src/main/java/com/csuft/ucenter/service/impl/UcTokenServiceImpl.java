package com.csuft.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.ucenter.entity.UcRefreshToken;
import com.csuft.ucenter.mapper.UcTokenMapper;
import com.csuft.ucenter.service.IUcTokenService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * uc_token  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Service
public class UcTokenServiceImpl extends ServiceImpl<UcTokenMapper, UcRefreshToken> implements IUcTokenService {

}
