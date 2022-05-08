package com.csuft.uc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="CourseVideo对象", description="")
public class CourseVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程视频的路径")
    private String path;

    @ApiModelProperty(value = "所属课程")
    private String courseId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "上传时间")
    private Date recordTime;

    @ApiModelProperty(value = "所属教师")
    private String teacherId;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
