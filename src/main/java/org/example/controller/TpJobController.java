package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.example.common.R;
import org.example.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.example.entity.TpJob;
import org.example.dto.TpJobDTO;

import org.example.service.ITpJobService;

/**
 *  控制器
 *
 * @author AI
 * @since 2021-10-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("tpJob")
@Api(description = "相关接口")
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
		return R.data(tpJobService.save(dto));
	}

	/**
	 * 修改 
	 */
	@PutMapping("/update")
	@ApiOperation(value = "修改", notes = "传入tpJob")
	public R update(@RequestBody TpJobDTO dto) {
		return R.data(tpJobService.updateById(dto));
	}

	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入id")
	public R remove(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		return R.data(tpJobService.deleteLogic(Integer.valueOf(id)));
	}

}
