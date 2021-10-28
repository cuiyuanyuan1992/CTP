package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.example.service.ITpJobService;
import org.example.utils.ApplicationContextHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Slf4j
public class JenkinsJob implements Job {
    private ITpJobService tpJobService = ApplicationContextHelper.getBean(ITpJobService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        tpJobService.buildJob(jobExecutionContext.getJobDetail().getKey().getName(),"jenkins");
    }
}
