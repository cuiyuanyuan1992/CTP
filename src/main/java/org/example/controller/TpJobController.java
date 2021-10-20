package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.example.exception.ErrorCode;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.example.entity.TpJob;
import org.example.dto.TpJobDTO;

import org.example.service.ITpJobService;

/**
 *  任务控制器
 *
 * @author AI
 * @since 2021-10-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("tpJob")
@Slf4j
@Api(description = "任务相关接口")
public class TpJobController {

	private ITpJobService tpJobService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入tpJob")
	public R<TpJob> detail(TpJobDTO dto) {
		TpJob detail = tpJobService.getOne(dto);
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入tpJob")
	public R<IPage<TpJob>> page(TpJobDTO dto) {
		IPage<TpJob> pages = tpJobService.page(dto);
		return R.data(pages);
	}
	/**
	 * 不分页 
	 */
	@GetMapping("/list")
	@ApiOperation(value = "不分页", notes = "传入tpJob")
	public R<List<TpJob>> list(TpJobDTO dto) {
		List<TpJob> list = tpJobService.list(dto);
		return R.data(list);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入tpJob")
	public R save(@RequestBody TpJobDTO dto) {
		//名称唯一性校验
		TpJobDTO query = new TpJobDTO();
		query.setJobName(dto.getJobName());
		TpJob job = tpJobService.getOne(query);
		if(!ObjectUtils.isEmpty(job)){
			log.error("job={} name is not unique",dto.getJobName());
			return R.data(ErrorCode.PARAM_NOT_UNIQUE.getErrorCode(),null,ErrorCode.PARAM_NOT_UNIQUE.getErrorMessage());
		}
		Integer result = tpJobService.save(dto);
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}

	/**
	 * 修改 
	 */
	@PutMapping("/update")
	@ApiOperation(value = "修改", notes = "传入tpJob")
	public R update(@RequestBody TpJobDTO dto) {
		Integer result = tpJobService.updateById(dto);
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}

	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入id")
	public R remove(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		Integer result = tpJobService.deleteLogic(Integer.valueOf(id));
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}

	@PostMapping("/disable")
	@ApiOperation(value = "禁用job", notes = "传入任务名称")
	public R disable(@ApiParam(value = "任务名称", required = true) @RequestParam String jobName) {
		Integer result = tpJobService.disableJob(jobName);
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}

	@PostMapping("/enable")
	@ApiOperation(value = "启用job", notes = "传入任务名称")
	public R enable(@ApiParam(value = "任务名称", required = true) @RequestParam String jobName) {
		Integer result = tpJobService.enableJob(jobName);
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}

	@PostMapping("/build")
	@ApiOperation(value = "构建job", notes = "传入任务名称,触发人")
	public R build(@ApiParam(value = "任务名称", required = true) @RequestParam String jobName,@ApiParam(value = "触发人", required = true) @RequestParam String trigger) {
		Integer result = tpJobService.buildJob(jobName,trigger);
		if(ObjectUtils.isEmpty(result) || result.intValue()<=0){
			return R.data(ErrorCode.OPERATION_FAILURE.getErrorCode(),null,ErrorCode.OPERATION_FAILURE.getErrorMessage());
		}
		return R.data(result);
	}
}
