package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.TpJobDTO;
import org.example.entity.TpJob;
import org.example.service.ITpJobService;
import org.example.utils.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
@Slf4j
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {
    @Autowired
    private ITpJobService tpJobService;

    @Override
    public void run(String... args) throws Exception {
        //获取状态为enable的任务，有定时的初始化定时设置
        TpJobDTO tpJobDTO = new TpJobDTO();
        tpJobDTO.setJobStatus("enable");
        List<TpJob> list = tpJobService.list(tpJobDTO);
        for(TpJob tpJob : list){
            if(!ObjectUtils.isEmpty(tpJob.getTimer())){
                QuartzManager.addJob(tpJob.getJobName(), JenkinsJob.class,tpJob.getTimer());
            }
        }
        log.info("quartz job init finish");
    }
}
