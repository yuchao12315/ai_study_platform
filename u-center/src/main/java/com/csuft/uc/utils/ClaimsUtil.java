package com.csuft.uc.utils;

import com.csuft.uc.entity.UcUser;
import com.csuft.uc.vo.UserVo;

import java.util.HashMap;
import java.util.Map;

public class ClaimsUtil {

    public static Map<String, Object> user2Claims(UcUser userByAccount) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userByAccount.getId());
        claims.put("status", userByAccount.getStatus());
        claims.put("avatar", userByAccount.getAvatar());
        claims.put("sex", userByAccount.getSex());
        claims.put("userName", userByAccount.getUserName());
        return claims;
    }


    public static UserVo claims2User(Map<String, Object> claims) {
        UserVo userVo = new UserVo();
        userVo.setId((String) claims.get("id"));
        userVo.setStatus((String) claims.get("status"));
        userVo.setAvatar((String) claims.get("avatar"));
        userVo.setSex((String) claims.get("sex"));
        userVo.setUserName((String) claims.get("userName"));
        return userVo;
    }
}
