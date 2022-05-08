package com.csuft.uc.vo;

import lombok.Data;

@Data
public class RegisterVo {

    private  String id;
    /**
     * 用户名
     */
    public String mail;
    /**
     * 用户密码
     */
    public String password;

    /**
     * 昵称
     */
    public String nickname;
    /**
     * 用户类型  1学生 2教师  3 管理员
     */
    public int type;

}
