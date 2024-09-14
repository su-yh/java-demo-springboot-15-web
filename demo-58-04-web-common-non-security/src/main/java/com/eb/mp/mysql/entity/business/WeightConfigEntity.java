package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.group.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
@TableName(value = "weight_config", autoResultMap = true)
public class WeightConfigEntity {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Null(groups = ValidationGroups.Req.Create.class)
    @NotNull(groups = ValidationGroups.Req.Update.class)
    private Long id;

    /**
     * 维度
     */
    @TableField("weight_dimension")
    @Size(max = 50)
    private String weightDimension;

    /**
     * 比较状态
     */
    @TableField("compare_status")
    private Byte compareStatus;

    /**
     * 分数
     */
    @TableField("score")
    private Integer score;

    /**
     * 值
     */
    @TableField("weight_value")
    private Integer weightValue;

    /**
     * 备注
     */
    @TableField("remark")
    @Size(max = 200)
    private String remark;
}
