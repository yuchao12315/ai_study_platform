package com.csuft.uc.vo;

import lombok.Data;

@Data
public class UserVo {
    protected String id;
    protected String status;
    protected String avatar;
    protected String roles;
    protected String userName;
    protected String sex;
    protected int lev;
}
