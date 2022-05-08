package com.csuft.uc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuft.uc.entity.UcUser;
import com.csuft.uc.vo.UserAdminVo;
import com.csuft.uc.vo.UserVo;

import java.util.List;

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

    List<UserAdminVo> listUser(int size, int offset, int page, String phone, String email, String userName, String userId, String status);

    Long listUserCount(String phone, String email, String userName, String userId, String status);

    UserVo getUserVo(String userId);
}
