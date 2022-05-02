package com.csuft.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.ucenter.entity.UcSettings;
import com.csuft.ucenter.mapper.UcSettingsMapper;
import com.csuft.ucenter.service.IUcSettingsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * uc_settings  服务实现类
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Service
public class UcSettingsServiceImpl extends ServiceImpl<UcSettingsMapper, UcSettings> implements IUcSettingsService {

}
