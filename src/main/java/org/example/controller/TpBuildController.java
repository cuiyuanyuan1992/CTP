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
@AllArgsConstructor
@RequestMapping("tpBuild")
@Api(description = "相关接口")
public class TpBuildController {

	private ITpBuildService tpBuildService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入tpBuild")
	public R<TpBuild> detail(TpBuildDTO dto) {
		TpBuild detail = tpBuildService.getOne(dto);
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入tpBuild")
	public R<IPage<TpBuild>> page(TpBuildDTO dto) {
		IPage<TpBuild> pages = tpBuildService.page(dto);
		return R.data(pages);
	}
	/**
	 * 不分页 
	 */
	@GetMapping("/list")
	@ApiOperation(value = "不分页", notes = "传入tpBuild")
	public R<List<TpBuild>> list(TpBuildDTO dto) {
		List<TpBuild> list = tpBuildService.list(dto);
		return R.data(list);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入tpBuild")
	public R save(@RequestBody TpBuildDTO dto) {
		return R.data(tpBuildService.save(dto));
	}

	/**
	 * 修改 
	 */
	@PutMapping("/update")
	@ApiOperation(value = "修改", notes = "传入tpBuild")
	public R update(@RequestBody TpBuildDTO dto) {
		return R.data(tpBuildService.updateById(dto));
	}

	/**
	 * 删除 
	 */
	@DeleteMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.data(tpBuildService.deleteLogic(Func.toIntList(ids)));
	}

}
