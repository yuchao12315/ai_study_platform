package com.csuft.ucenter.vo;

import lombok.Data;

@Data
public class RegisterVo {
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
     * 用户类型  1 教师  2 家长  3 管理员
     */
    public int type;

}
