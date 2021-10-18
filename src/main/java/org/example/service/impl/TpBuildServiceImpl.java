package org.example.service.impl;

import org.example.entity.TpBuild;
import org.example.mapper.TpBuildMapper;
import org.example.service.ITpBuildService;
import org.example.dto.TpBuildDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.common.Condition;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import cn.hutool.core.util.StrUtil;
import java.util.List;

/**
 *  服务实现类
 *
 * @author AI
 * @since 2021-10-18
 */
@Service
@AllArgsConstructor
public class TpBuildServiceImpl implements ITpBuildService {

    protected TpBuildMapper tpBuildMapper;
    @Override
    public IPage<TpBuild> page(TpBuildDTO dto) {
        IPage<TpBuild> page = Condition.getPage(dto);
        QueryWrapper<TpBuild> queryWrapper = Condition.getQueryWrapper(BeanCopyUtils.copy(dto, TpBuild.class));
        if (StrUtil.isNotEmpty(dto.getColumn()) && StrUtil.isNotEmpty(dto.getKeywords())) {
        queryWrapper.like(dto.getColumn(),dto.getKeywords());
        }
        if (StrUtil.isNotEmpty(dto.getOrderAsc())) {
        queryWrapper.orderByAsc(dto.getOrderAsc());
        }
        if (StrUtil.isNotEmpty(dto.getOrderDesc())) {
        queryWrapper.orderByDesc(dto.getOrderDesc());
        }
        if (dto.getStartTime() != null) {
        queryWrapper.gt("created",dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
        queryWrapper.lt("created",dto.getEndTime());
        }
        return tpBuildMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<TpBuild> list(TpBuildDTO dto) {
        QueryWrapper<TpBuild> queryWrapper = Condition.getQueryWrapper(BeanCopyUtils.copy(dto, TpBuild.class));
        if (StrUtil.isNotEmpty(dto.getColumn()) && StrUtil.isNotEmpty(dto.getKeywords())) {
        queryWrapper.like(dto.getColumn(),dto.getKeywords());
        }
        if (StrUtil.isNotEmpty(dto.getOrderAsc())) {
        queryWrapper.orderByAsc(dto.getOrderAsc());
        }
        if (StrUtil.isNotEmpty(dto.getOrderDesc())) {
        queryWrapper.orderByDesc(dto.getOrderDesc());
        }
        if (dto.getStartTime() != null) {
        queryWrapper.gt("created",dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
        queryWrapper.lt("created",dto.getEndTime());
        }
        return tpBuildMapper.selectList(queryWrapper);
    }

    @Override
    public Integer save(TpBuildDTO dto) {
        return tpBuildMapper.insert(BeanCopyUtils.copy(dto,TpBuild.class));
    }

    @Override
    public Integer updateById(TpBuildDTO dto) {
        return tpBuildMapper.updateById(BeanCopyUtils.copy(dto,TpBuild.class));
    }


    @Override
    public Integer deleteLogic(List<Integer> toIntList) {
        return tpBuildMapper.deleteBatchIds(toIntList);
    }

    @Override
    public TpBuild getOne(TpBuildDTO dto) {
        return tpBuildMapper.selectOne(Condition.getQueryWrapper(BeanCopyUtils.copy(dto,TpBuild.class)));
    }
}
