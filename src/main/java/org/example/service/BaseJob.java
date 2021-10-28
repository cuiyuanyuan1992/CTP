package org.example.service;

import com.offbytwo.jenkins.model.Build;
import org.example.dto.TpJobDTO;

/**
 * 基础任务接口，定义通用方法，便于扩展专项
 */
public interface BaseJob {

    public String getJobXml(TpJobDTO jobDTO);

    public String updateJobXml(TpJobDTO jobDTO);

    public String getBuildResult(String jobName,String buildNumber);
}
