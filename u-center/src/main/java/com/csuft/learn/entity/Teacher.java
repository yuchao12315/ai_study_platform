package com.csuft.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="Teacher对象", description="")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师表")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "教师登录账号")
    private String account;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "教师名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "教师编号")
    private String num;

    @ApiModelProperty(value = "所属学院")
    private Integer collegeId;

    @ApiModelProperty(value = "教授课程")
    private Integer courseId;

    @ApiModelProperty(value = "手机号码")
    private String telphone;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
