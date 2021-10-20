package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.example.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author AI
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TpBuild对象", description = "TpBuild对象")
@TableName("tp_build")
public class TpBuild extends BaseEntity {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 任务id
     */
  @ApiModelProperty(value = "任务id")
  private Integer jobId;
    /**
     * 任务名称
     */
  @ApiModelProperty(value = "任务名称")
  private String jobName;
    /**
     * 任务类型
     */
  @ApiModelProperty(value = "任务类型")
  private String jobType;
    /**
     * 构建编号
     */
  @ApiModelProperty(value = "构建编号")
  private Integer buildNumber;
    /**
     * 构建参数
     */
  @ApiModelProperty(value = "构建参数")
  private String buildParam;
    /**
     * 构建结果
     */
  @ApiModelProperty(value = "构建结果")
  private String result;
    /**
     * 构建状态
     */
  @ApiModelProperty(value = "构建状态")
  private String buildStatus;
    /**
     * 构建人
     */
  @ApiModelProperty(value = "构建人")
  private String triggerPeople;
    /**
     * 构建报告
     */
  @ApiModelProperty(value = "构建报告")
  private String buildReport;
    /**
     * 耗时
     */
  @ApiModelProperty(value = "耗时")
  private Double duration;


}
