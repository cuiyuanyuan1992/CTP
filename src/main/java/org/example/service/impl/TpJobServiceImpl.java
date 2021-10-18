package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.TpJob;
import org.example.mapper.TpJobMapper;
import org.example.service.BaseJob;
import org.example.service.ITpJobService;
import org.example.dto.TpJobDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.common.Condition;
import org.example.utils.BeanCopyUtils;
import org.example.utils.JenkinsUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import cn.hutool.core.util.StrUtil;
import java.util.List;

/**
 *  任务服务实现类
 *
 * @author AI
 * @since 2021-10-18
 */
@Service
@AllArgsConstructor
@Slf4j
public class TpJobServiceImpl implements ITpJobService {

    protected TpJobMapper tpJobMapper;
    JenkinsUtil jenkinsUtil = new JenkinsUtil();

    @Override
    public IPage<TpJob> page(TpJobDTO dto) {
        IPage<TpJob> page = Condition.getPage(dto);
        QueryWrapper<TpJob> queryWrapper = Condition.getQueryWrapper(BeanCopyUtils.copy(dto, TpJob.class));
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
        return tpJobMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<TpJob> list(TpJobDTO dto) {
        QueryWrapper<TpJob> queryWrapper = Condition.getQueryWrapper(BeanCopyUtils.copy(dto, TpJob.class));
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
        return tpJobMapper.selectList(queryWrapper);
    }

    @Override
    public Integer save(TpJobDTO dto) {
        /// TODO: 2021/10/18 添加名称唯一性校验 
        JobFactory jobFactory = new JobFactory();
        BaseJob baseJob = jobFactory.getJob(dto.getJobType());

        boolean result = jenkinsUtil.ceateJob(dto.getJobName(),baseJob.getJobXml(dto));

        if(result){
            log.info("job={} create success!",dto.getJobName());
            return tpJobMapper.insert(BeanCopyUtils.copy(dto,TpJob.class));
        }
        return null;
    }

    @Override
    public Integer updateById(TpJobDTO dto) {
        JobFactory jobFactory = new JobFactory();
        BaseJob baseJob = jobFactory.getJob(dto.getJobType());

        boolean result = jenkinsUtil.updateJob(dto.getJobName(),baseJob.updateJobXml(dto));
        if(result){
            log.info("job={} update success!",dto.getJobName());
            return tpJobMapper.updateById(BeanCopyUtils.copy(dto,TpJob.class));
        }
        return null;
    }


    @Override
    public Integer deleteLogic(Integer id) {
        TpJobDTO dto = new TpJobDTO();
        dto.setId(id);
        String jobName = this.getOne(dto).getJobName();
        jenkinsUtil.deleteJob(jobName);
        log.info("job={} delete success!",jobName);

        return tpJobMapper.deleteById(id);
    }

    @Override
    public TpJob getOne(TpJobDTO dto) {
        return tpJobMapper.selectOne(Condition.getQueryWrapper(BeanCopyUtils.copy(dto,TpJob.class)));
    }

    @Override
    public Integer buildJob(String jobName) {
        Integer buildNumber = jenkinsUtil.buildJob(jobName);
        /// TODO: 2021/10/18 插入构建记录
        return buildNumber;
    }

    @Override
    public Integer disableJob(String jobName) {
        jenkinsUtil.disableJob(jobName);

        //更新任务状态
        TpJobDTO dto = new TpJobDTO();
        dto.setJobName(jobName);
        TpJob job = this.getOne(dto);

        job.setJobStatus("disable");
        return tpJobMapper.updateById(BeanCopyUtils.copy(job,TpJob.class));
    }

    @Override
    public Integer enableJob(String jobName) {
        jenkinsUtil.enableJob(jobName);

        //更新任务状态
        TpJobDTO dto = new TpJobDTO();
        dto.setJobName(jobName);
        TpJob job = this.getOne(dto);

        job.setJobStatus("enable");
        return tpJobMapper.updateById(BeanCopyUtils.copy(job,TpJob.class));
    }
}
