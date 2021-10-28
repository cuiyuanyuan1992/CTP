package org.example.service;

import com.offbytwo.jenkins.model.Build;
import org.example.entity.TpBuild;
import org.example.dto.TpBuildDTO;

import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author AI
 * @since 2021-10-18
 */
public interface ITpBuildService  {

        IPage<TpBuild> page(TpBuildDTO dto) ;

        List<TpBuild> list(TpBuildDTO dto) ;

        Integer save(TpBuildDTO dto) ;

        Integer updateById(TpBuildDTO dto) ;

        Integer deleteLogic(List<Integer> toIntList) ;

        TpBuild getOne(TpBuildDTO dto) ;

        Integer stopJob(String jobName)throws IOException;

        String getJobBuildLog(String jobName,int buildNumber);

        String getBuildResult(Build build,String jobName,String jobType) throws IOException;
}
