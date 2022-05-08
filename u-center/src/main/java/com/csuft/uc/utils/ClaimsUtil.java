package com.csuft.uc.utils;

import com.csuft.uc.vo.UserVo;

import java.util.HashMap;
import java.util.Map;

public class ClaimsUtil {

    public static final String ID = "id";
    public static final String STATUS = "status";
    public static final String AVATAR = "avatar";
    public static final String ROLES = "roles";
    public static final String SEX = "sex";
    public static final String USERNAME = "userName";

    public static Map<String, Object> user2Claims(UserVo userByAccount) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, userByAccount.getId());
        claims.put(STATUS, userByAccount.getStatus());
        claims.put(AVATAR, userByAccount.getAvatar());
        claims.put(SEX, userByAccount.getSex());
        claims.put(USERNAME, userByAccount.getUserName());
        claims.put(ROLES, userByAccount.getRoles());
        return claims;
    }


    public static UserVo claims2User(Map<String, Object> claims) {
        UserVo userVo = new UserVo();
        userVo.setId((String) claims.get(ID));
        userVo.setStatus((String) claims.get(STATUS));
        userVo.setAvatar((String) claims.get(AVATAR));
        userVo.setSex((String) claims.get(SEX));
        userVo.setUserName((String) claims.get(USERNAME));
        userVo.setRoles((String) claims.get(ROLES));
        return userVo;
    }
}
