package com.csuft.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.uc.entity.UcUserInfo;
import com.csuft.uc.mapper.UcUserInfoMapper;
import com.csuft.uc.service.IUcUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * uc_user_info  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Service
public class UcUserInfoServiceImpl extends ServiceImpl<UcUserInfoMapper, UcUserInfo> implements IUcUserInfoService {

}
