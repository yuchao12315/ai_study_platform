package com.csuft.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value="Discuss对象", description="")
public class Discuss implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "话题题目")
    private String title;

    @ApiModelProperty(value = "话题内容")
    private String content;

    @ApiModelProperty(value = "发布的教师")
    private Integer teacherId;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime recordTime;

    @ApiModelProperty(value = "所属课程")
    private Integer courseId;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
