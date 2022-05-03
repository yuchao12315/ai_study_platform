package com.csuft.uc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * uc_user_info 
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UcUserInfo对象", description="uc_user_info ")
public class UcUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String userId;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "等级")
    private String lev;

    @ApiModelProperty(value = "性别")
    private String sex;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
