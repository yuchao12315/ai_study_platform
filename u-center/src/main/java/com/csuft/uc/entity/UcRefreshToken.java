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
 * uc_token 
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UcToken对象", description="uc_token ")
public class UcRefreshToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建人")
    private String userId;

    @ApiModelProperty(value = "刷新的token")
    @TableField("refreshToken")
    private String refreshtoken;

    @ApiModelProperty(value = "应用ID")
    @TableField("appId")
    private String appid;

    @ApiModelProperty(value = "token的MD5值")
    @TableField("tokenKey")
    private String tokenkey;

    @ApiModelProperty(value = "登录来源")
    @TableField("loginFrom")
    private String loginfrom;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
