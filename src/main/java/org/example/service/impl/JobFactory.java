package org.example.service.impl;

import org.example.constant.JobTypeEnum;
import org.example.service.BaseJob;

public class JobFactory {

    public BaseJob getJob(String jobTypeCode){
        if(jobTypeCode == null){
            return null;
        }
        if(jobTypeCode.equalsIgnoreCase(JobTypeEnum.ITEST.getCode())){
            return new ITJob();
        }
        return null;
    }
}
