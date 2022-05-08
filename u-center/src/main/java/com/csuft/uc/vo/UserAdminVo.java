package com.csuft.uc.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserAdminVo extends  UserVo{

    private  String phoneName;
    private  String email;
    private  int lev;
    private Date createTime;
}
