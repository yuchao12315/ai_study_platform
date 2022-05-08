package com.csuft.uc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StudentCourse对象", description="")
public class StudentCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学生姓名")
    private String userName;

    @ApiModelProperty(value = "课程名")
    private String courseName;

    private String cover;

    @ApiModelProperty(value = "所属学院")
    private Integer collegeId;

    @ApiModelProperty(value = "讲师")
    private String lecturer;

    @ApiModelProperty(value = "课程介绍")
    private String intro;


}
