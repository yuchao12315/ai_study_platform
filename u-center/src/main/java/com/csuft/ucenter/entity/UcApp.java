package com.csuft.ucenter.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * uc_app 
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UcApp对象", description="uc_app ")
public class UcApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建人")
    private String userId;

    @ApiModelProperty(value = "应用名称")
    @TableField("appName")
    private String appname;

    @ApiModelProperty(value = "图片url")
    private String url;

    @ApiModelProperty(value = "应用类型 normal普通类型，own 自己的应用")
    @TableField("appType")
    private String apptype;

    @ApiModelProperty(value = "状态：0表示正常，1表示删除")
    private Integer deleted;

    @ApiModelProperty(value = "状态：0表示可用，1表示审核中，3表示禁用")
    private Integer status;

    @ApiModelProperty(value = "app唯一标识")
    @TableField("appKey")
    private String appkey;

    @ApiModelProperty(value = "回调地址")
    @TableField("appIcon")
    private String appicon;

    @ApiModelProperty(value = "应用描述")
    @TableField("appDescription")
    private String appdescription;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
