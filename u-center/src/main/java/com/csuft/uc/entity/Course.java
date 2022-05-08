package com.csuft.uc.entity;

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
 * @since 2022-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Course对象", description="")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程名")
    private String name;

    @ApiModelProperty(value = "所属学院")
    private Integer collegeId;

    @ApiModelProperty(value = "课程编号")
    private String num;

    @ApiModelProperty(value = "讲师")
    private String lecturer;

    @ApiModelProperty(value = "课程介绍")
    private String intro;

    @ApiModelProperty(value = "封面")
    private String cover;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
