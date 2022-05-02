package com.csuft.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@ApiModel(value="CourseFile对象", description="")
public class CourseFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程文档的路径")
    private String path;

    @ApiModelProperty(value = "所属课程")
    private Integer courseId;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime recordTime;

    @ApiModelProperty(value = "所属教师")
    private Integer teacherId;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
