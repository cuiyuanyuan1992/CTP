package org.example.job;

import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TpBuildDTO;
import org.example.entity.TpBuild;
import org.example.service.ITpBuildService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.JenkinsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JenkinsPollJob {
    @Autowired
    private ITpBuildService tpBuildService;
    @Autowired
    JenkinsUtil jenkinsUtil;

    @Scheduled(cron = "0 0/2 * * * ?") //每2分钟执行
    public void doTask(){
        //查询数据库中未完结的构建
        TpBuildDTO buildDTO = new TpBuildDTO();
        buildDTO.setBuildStatus(BuildResult.BUILDING.name());
        List<TpBuild> buildingList = tpBuildService.list(buildDTO);

        buildDTO.setBuildStatus(BuildResult.REBUILDING.name());
        List<TpBuild> rebuildingList = tpBuildService.list(buildDTO);

        buildingList.addAll(rebuildingList);
        //更新构建记录
        for(TpBuild build : buildingList){
            try{
                //获取jenkins结果
                Build jenkinsBuild = jenkinsUtil.getJobByNumber(build.getJobName(),build.getBuildNumber());
                String result = tpBuildService.getBuildResult(jenkinsBuild,build.getJobName(),build.getJobType());
                if(!ObjectUtils.isEmpty(result)){
                    //构建结果对象
                    build.setResult(result);
                }
                build.setBuildReport(jenkinsUtil.getJobBuildReport(build.getJobName(),build.getBuildNumber()));
                build.setDuration(Double.valueOf(String.valueOf(jenkinsBuild.details().getDuration())));
                build.setBuildStatus(jenkinsBuild.details().getResult().name());

                tpBuildService.updateById(BeanCopyUtils.copy(build,TpBuildDTO.class));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
