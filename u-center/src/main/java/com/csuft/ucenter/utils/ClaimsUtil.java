package com.csuft.ucenter.utils;

import com.csuft.ucenter.vo.UserVo;

import java.util.HashMap;
import java.util.Map;

public class ClaimsUtil {

    public static Map<String, Object> user2Claims(UserVo userByAccount) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userByAccount.getId());
        claims.put("salt", userByAccount.getSalt());
        claims.put("status", userByAccount.getStatus());
        claims.put("avatar", userByAccount.getAvatar());
        claims.put("password", userByAccount.getPassword());
        claims.put("userName", userByAccount.getUserName());
        return claims;
    }


    public static com.csuft.ucenter.vo.UserVo claims2User(Map<String, Object> claims) {
        UserVo userVo = new UserVo();
        userVo.setId((String) claims.get("id"));
        userVo.setSalt((String) claims.get("salt"));
        userVo.setStatus((String) claims.get("status"));
        userVo.setAvatar((String) claims.get("avatar"));
        userVo.setPassword((String) claims.get("password"));
        userVo.setUserName((String) claims.get("userName"));
        return userVo;
    }
}
