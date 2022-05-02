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
 * uc_images 
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UcImages对象", description="uc_images ")
public class UcImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String userId;

    @ApiModelProperty(value = "图片名称")
    @TableField("imageName")
    private String imagename;

    @ApiModelProperty(value = "图片url")
    private String url;

    @ApiModelProperty(value = "图片类型（avatar,app_icon,cover）")
    private String type;

    @ApiModelProperty(value = "MD5值")
    private String md5;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
