package com.csuft.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuft.uc.entity.UcUser;

/**
 * <p>
 * uc_user  Mapper 接口
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
public interface UcUserMapper extends BaseMapper<UcUser> {

    UcUser getUserByAccount(String name);

}
