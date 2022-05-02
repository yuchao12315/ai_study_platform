package com.csuft.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="TaskQuestion对象", description="")
public class TaskQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "考试的题目")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目的问题")
    private String title;

    @ApiModelProperty(value = "选项A")
    private String itemA;

    @ApiModelProperty(value = "选项B")
    private String itemB;

    @ApiModelProperty(value = "选项C")
    private String itemC;

    @ApiModelProperty(value = "选项D")
    private String itemD;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "所属的考试")
    private Integer taskId;

    @ApiModelProperty(value = "题目的分值")
    private Integer score;

    private String state;

    @TableField("isDel")
    private Integer isdel;


}
