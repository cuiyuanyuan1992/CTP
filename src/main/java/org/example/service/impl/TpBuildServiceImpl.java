package org.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResultDto;
import org.example.entity.TpBuild;
import org.example.mapper.TpBuildMapper;
import org.example.service.ITpBuildService;
import org.example.dto.TpBuildDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.common.Condition;
import org.example.utils.BeanCopyUtils;
import org.example.utils.JenkinsUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *  构建记录服务实现类
 *
 * @author AI
 * @since 2021-10-18
 */
@Service
@AllArgsConstructor
@Slf4j
public class TpBuildServiceImpl implements ITpBuildService {

    protected TpBuildMapper tpBuildMapper;
    JenkinsUtil jenkinsUtil;

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
        Integer result = tpBuildMapper.insert(BeanCopyUtils.copy(dto,TpBuild.class));
        log.info("job={} number={} build start",dto.getJobName(),dto.getBuildNumber());
        return result;
    }

    @Override
    public Integer updateById(TpBuildDTO dto) {
        Integer result = tpBuildMapper.updateById(BeanCopyUtils.copy(dto,TpBuild.class));
        log.info("job={} number={} build update success",dto.getJobName(),dto.getBuildNumber());
        return result;
    }


    @Override
    public Integer deleteLogic(List<Integer> toIntList) {
        Integer result = tpBuildMapper.deleteBatchIds(toIntList);
        log.info("builds={} delete success",toIntList.toString());
        return result;
    }

    @Override
    public TpBuild getOne(TpBuildDTO dto) {
        return tpBuildMapper.selectOne(Condition.getQueryWrapper(BeanCopyUtils.copy(dto,TpBuild.class)));
    }

    @Override
    public Integer stopJob(String jobName) throws IOException {
        jenkinsUtil.stopLastJobBuild(jobName);
        Build jenkinsBuild = jenkinsUtil.getJobLastBuild(jobName);
        //更新构建信息
        TpBuildDTO dto = new TpBuildDTO();
        dto.setJobName(jobName);
        dto.setBuildNumber(jenkinsBuild.getNumber());
        TpBuild build = this.getOne(dto);

        String result = this.getBuildResult(jenkinsBuild);
        if(!ObjectUtils.isEmpty(result)){
            //构建结果对象
            build.setResult(result);
            build.setBuildReport(jenkinsUtil.getJobBuildReport(jobName,jenkinsBuild.getNumber()));
            build.setDuration(jenkinsBuild.details().getTestResult().getDuration());
        }
        build.setBuildStatus(jenkinsBuild.details().getResult().name());

        Integer update = this.updateById(BeanCopyUtils.copy(build,TpBuildDTO.class));
        return update;
    }

    @Override
    public String getJobBuildLog(String jobName, int buildNumber) {
        return jenkinsUtil.getJobBuildLog(jobName,buildNumber);
    }

    @Override
    public String getBuildResult(Build build){
        ResultDto resultDto = new ResultDto();
        TestResult testResult = null;
        try {
            testResult = build.details().getTestResult();
            int failCount = testResult.getFailCount();
            int passCount = testResult.getPassCount();
            int skipCount = testResult.getSkipCount();
            int total = failCount + passCount + skipCount;
            float successRate = new BigDecimal(passCount).divide(new BigDecimal(total),2, BigDecimal.ROUND_HALF_UP).floatValue();

            resultDto.setFailCount(failCount);
            resultDto.setPassCount(passCount);
            resultDto.setSkipCount(skipCount);
            resultDto.setSuccessRate(successRate);
            return JSON.toJSONString(resultDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
