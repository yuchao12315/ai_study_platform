package com.csuft.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csuft.uc.entity.UcSettings;
import com.csuft.uc.mapper.UcSettingsMapper;
import com.csuft.uc.service.IUcSettingsService;
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
