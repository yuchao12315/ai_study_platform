package com.csuft.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yc
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学生账号")
    private String account;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "学生名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "学号")
    private String num;

    @ApiModelProperty(value = "所属学院")
    private Integer collegeId;

    @ApiModelProperty(value = "手机号码")
    private String telphone;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
