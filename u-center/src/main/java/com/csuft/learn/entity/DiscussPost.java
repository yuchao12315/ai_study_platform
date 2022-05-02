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
@ApiModel(value="DiscussPost对象", description="")
public class DiscussPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "回复内容")
    private String content;

    @ApiModelProperty(value = "回复话题")
    private Integer discussId;

    @ApiModelProperty(value = "回复的学生")
    private Integer studentId;

    @ApiModelProperty(value = "回复时间")
    private LocalDateTime recordTime;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
