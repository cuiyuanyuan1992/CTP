package org.example.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.example.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.example.common.CommonDto;
/**
 * 数据传输对象实体类
 *
 * @author AI
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TpJob对象", description = "TpJob对象")
public class TpJobDTO extends CommonDto {

					private Integer id;
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
		 * git路径
		 */
			@ApiModelProperty(value = "git路径")
					private String gitUrl;
		/**
		 * git分支
		 */
			@ApiModelProperty(value = "git分支")
					private String gitBranch;
		/**
		 * 执行命令
		 */
			@ApiModelProperty(value = "执行命令")
					private String command;
		/**
		 * 通知人(邮件地址，英文逗号分隔)
		 */
			@ApiModelProperty(value = "通知人(邮件地址，英文逗号分隔)")
					private String recipients;
		/**
		 * 超时时间
		 */
			@ApiModelProperty(value = "超时时间")
					private Integer timeoutMinutes;
		/**
		 * 参数化构建入参
		 */
			@ApiModelProperty(value = "参数化构建入参")
					private String buildParam;
		/**
		 * 定时器
		 */
			@ApiModelProperty(value = "定时器")
					private String timer;
		/**
		 * 创建人
		 */
			@ApiModelProperty(value = "创建人")
					private String creator;
		/**
		 * 更新人
		 */
			@ApiModelProperty(value = "更新人")
					private String updator;
		/**
		 * 任务描述
		 */
			@ApiModelProperty(value = "任务描述")
					private String jobDesc;

		/**
		 * 任务状态
		 */
		@ApiModelProperty(value = "任务状态")
		private String jobStatus;

			@ApiModelProperty(value = "是否删除")
					private Integer deleted;


		}
