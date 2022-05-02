package com.csuft.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuft.ucenter.entity.UcUser;
import com.csuft.ucenter.vo.UserVo;

/**
 * <p>
 * uc_user  Mapper 接口
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
public interface UcUserMapper extends BaseMapper<UcUser> {

    UserVo getUserByAccount(String name);
}
