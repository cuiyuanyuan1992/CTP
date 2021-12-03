package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.example.exception.ErrorCode;
import org.example.utils.Func;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.example.entity.TpBuild;
import org.example.dto.TpBuildDTO;

import org.example.service.ITpBuildService;

/**
 *  控制器
 *
 * @author AI
 * @since 2021-10-18
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("tpBuild")
@Api(description = "相关接口")
public class TpBuildController {

	private ITpBuildService tpBuildService;

	/**
	 * 详情
	 */
	@PostMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入tpBuild")
	public R<TpBuild> detail(@RequestBody TpBuildDTO dto) {
		TpBuild detail = tpBuildService.getOne(dto);
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@PostMapping("/page")
	@ApiOperation(value = "分页", notes = "传入tpBuild")
	public R<IPage<TpBuild>> page(@RequestBody TpBuildDTO dto) {
		if(StringUtils.isEmpty(dto.getJobName())){
			dto.setJobName(null);
		}else{
			dto.setColumn("job_name");
			dto.setKeywords(dto.getJobName());
			dto.setJobName(null);
		}
		if(StringUtils.isEmpty(dto.getTriggerPeople())){
			dto.setTriggerPeople(null);
		}else{
			dto.setColumn("trigger_people");
			dto.setKeywords(dto.getTriggerPeople());
			dto.setTriggerPeople(null);
		}
		dto.setOrderDesc("id");
		IPage<TpBuild> pages = tpBuildService.page(dto);
		return R.data(pages);
	}
	/**
	 * 不分页 
	 */
	@PostMapping("/list")
	@ApiOperation(value = "不分页", notes = "传入tpBuild")
	public R<List<TpBuild>> list(@RequestBody TpBuildDTO dto) {
		List<TpBuild> list = tpBuildService.list(dto);
		return R.data(list);
	}

	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		Integer result = tpBuildService.deleteLogic(Func.toIntList(ids));
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}

	/**
	 * 停止任务
	 */
	@PostMapping("/stop")
	@ApiOperation(value = "停止job", notes = "传入任务名称")
	public R disable(@ApiParam(value = "任务名称", required = true) @RequestParam String jobName) {
		Integer result = 0;
		try{
			result = tpBuildService.stopJob(jobName);
			if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
				return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
			}
		}catch (Exception e){
			e.printStackTrace();
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,e.getMessage());
		}
		return R.data(result);
	}

	/**
	 * 获取日志
	 */
	@PostMapping("/getlog")
	@ApiOperation(value = "获取构建日志", notes = "获取构建日志")
	public R getLog(@ApiParam(value = "任务名称", required = true) @RequestParam String jobName,@ApiParam(value = "构建编号", required = true) @RequestParam int buildNumber) {
		String log = tpBuildService.getJobBuildLog(jobName, buildNumber);
		if (ObjectUtils.isEmpty(log)) {
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(), null, ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(log);
	}
}
