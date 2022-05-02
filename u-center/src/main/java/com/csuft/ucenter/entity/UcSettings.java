package com.csuft.ucenter.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * uc_settings 
 * </p>
 *
 * @author yc
 * @since 2022-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UcSettings对象", description="uc_settings ")
public class UcSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
