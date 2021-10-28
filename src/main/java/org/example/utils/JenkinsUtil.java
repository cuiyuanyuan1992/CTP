package org.example.utils;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JenkinsUtil {
    // 连接 Jenkins 需要设置的信息
    static final String JENKINS_HOST = "192.168.31.142:8080";
    static final String JENKINS_USERNAME = "admin";
    static final String JENKINS_PASSWORD = "1136825d669b60f83d2149a022670b98e0";
    static final String JENKINS_USER = JENKINS_USERNAME+":"+JENKINS_PASSWORD;
    static final String JENKINS_URL = "http://"+JENKINS_USER+"@"+JENKINS_HOST+"/";

    HttpUtils httpUtils = new HttpUtils();
//    public static String jenkinsUrl = "http://192.168.31.142:8080/";

    /**
     * 获取构建xml结果
     * @param jobName
     * @param buildNumber
     * @return
     */
    public String getBuildResult(String jobName,String buildNumber){
        String url = JENKINS_URL+"job/"+jobName+"/"+buildNumber+"/api/xml";
        String result = httpUtils.doPost(url,null,JENKINS_USERNAME,JENKINS_PASSWORD);
        return result;
    }

    private static JenkinsServer jenkins ;

    static{
        try {
            jenkins= new JenkinsServer(new URI(JENKINS_URL),JENKINS_USERNAME , JENKINS_PASSWORD);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建任务
     * @param jobName
     * @param jobXml
     */
    public boolean ceateJob(String jobName,String jobXml){
        try {
            jenkins.createJob(jobName,jobXml);
            log.info("job={} create success",jobName);
            return true;
        } catch (IOException e) {
            log.error("job={} create failure",jobName);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新job
     * @param jobName
     * @param jobXml
     */
    public boolean updateJob(String jobName,String jobXml){
        try {
            jenkins.updateJob(jobName,jobXml);
            log.info("job={} update success",jobName);
            return true;
        } catch (IOException e) {
            log.error("job={} update failure",jobName);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取job详情
     * @param jobName
     * @return
     */
    public JobWithDetails getJob(String jobName){
        try {
            JobWithDetails job = jenkins.getJob(jobName);
            log.info("job detail={}",job.toString());
            return job;
        } catch (IOException e) {
            log.error("job={} get detail failure",jobName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取job列表
     * @return
     */
    public Map<String, Job> getJobList(){
        try {
            // 获取 Job 列表
            Map<String, Job> jobs = jenkins.getJobs();
            return jobs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *获取job xml配置
     * @param jobName
     */
    public String getJobConfig(String jobName){
        try {
            String xml = jenkins.getJobXml(jobName);
            log.info("job xml={}",xml);
            return xml;
        } catch (IOException e) {
            log.error("job={} get job xml failure",jobName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建任务
     * @param jobName
     * @return
     */
    public Integer buildJob(String jobName) {
        try{
            JobWithDetails detail = jenkins.getJob(jobName);
            int nextBuildNum = detail.getNextBuildNumber();
            detail.build();
            log.info("job={} build success,build number={}",jobName,nextBuildNum);
            return nextBuildNum;
        }catch (Exception e){
            log.error("job={} build failure",jobName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建有参数的job
     * @param jobName
     * @param param 任务构建入参
     * @return
     */
    public Integer buildParamJob(String jobName,Map<String,String> param) {
        try {
            // 执行 build 任务
            JobWithDetails detail = jenkins.getJob(jobName);
            int nextBuildNum = detail.getNextBuildNumber();
            detail.build(param);
            log.info("job={} build success,build number={},param={}", jobName, nextBuildNum, param.toString());
            return nextBuildNum;
        } catch (IOException e) {
            log.error("job={} build failure",jobName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 停止最后的构建任务
     * @param jobName
     * @return
     */
    public String stopLastJobBuild(String jobName){
        try {
            // 获取最后的 build 信息
            Build build = jenkins.getJob(jobName).getLastBuild();
            log.info("last job info={}",build.details().toString());
            // 停止最后的 build
            String result = build.Stop();
            log.info("job={} stop result={}",jobName,result);
            return result;
        } catch (IOException e) {
            log.error("job={} stop failure",jobName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除job
     * @param jobName
     */
    public void deleteJob(String jobName){
        try {
            jenkins.deleteJob(jobName);
            log.info("job={} delete success",jobName);
        } catch (IOException e) {
            log.error("job={} delete failure",jobName);
            e.printStackTrace();
        }
    }

    /**
     * 禁用job
     * @param jobName
     */
    public void disableJob(String jobName){
        try {
            jenkins.disableJob(jobName);
            log.info("job={} disable success",jobName);
        } catch (IOException e) {
            log.error("job={} disable failure",jobName);
            e.printStackTrace();
        }
    }

    /**
     * 启用job
     * @param jobName
     */
    public void enableJob(String jobName){
        try {
            jenkins.enableJob(jobName);
            log.info("job={} enable success",jobName);
        } catch (IOException e) {
            log.error("job={} enable failure",jobName);
            e.printStackTrace();
        }
    }

    /**
     * 获取最后构建的任务
     * @param jobName
     * @return
     */
    public Build getJobLastBuild(String jobName){
        try {
            // 获取 Job 信息
            JobWithDetails job = jenkins.getJob(jobName);
            // 获得最后编译信息
            Build lastBuild = job.getLastBuild();
            log.info("job={} last build info={}",jobName,lastBuild.details().toString());
            return lastBuild;
        } catch (IOException e) {
            log.error("job={} get last build failure",jobName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *根据buildNumber获取构建信息
     * @param jobName
     * @param buildNumber
     * @return
     */
    public Build getJobByNumber(String jobName,int buildNumber){
        try {
            // 获取 Job 信息
            JobWithDetails job = jenkins.getJob(jobName);
            // 根据
            Build numberBuild = job.getBuildByNumber(buildNumber);
            log.info("job={} number={} build info={}",jobName,buildNumber,numberBuild.details().toString());
            return numberBuild;
        } catch (IOException e) {
            log.error("job={} buildNumber={} get detail failure",jobName,buildNumber);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取构建日志
     * @param jobName
     * @param buildNumber
     * @return
     */
    public String getJobBuildLog(String jobName,int buildNumber){
        try {
            BuildWithDetails build = this.getJobByNumber(jobName,buildNumber).details();
            // 获取构建的日志，如果正在执行构建，则会只获取已经执行的过程日志
            return build.getConsoleOutputText();
//            // Html格式日志
//            System.out.println(build.getConsoleOutputHtml());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取构建报告链接
     * @param jobName
     * @param buildNumber
     * @return
     */
    public String getJobBuildReport(String jobName,int buildNumber){
        try {
            String buildUrl = this.getJobByNumber(jobName,buildNumber).getUrl();
            String reportUrl =  buildUrl+"allure";
            log.info("build report url={}",reportUrl);
            return reportUrl;
        }catch (Exception e) {
            log.error("job={} buildNumber={} get build report failure",jobName,buildNumber);
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String[] args) {
//        JenkinsUtil jenkinsUtil = new JenkinsUtil();
//        String resultXml = jenkinsUtil.getBuildResult("test","13");
//
//        Document document = null;
//        try {
//            document = DocumentHelper.parseText(resultXml);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        //获取文档根节点
//        Element root = document.getRootElement();
//        List<Element> elementList = root.elements();
//        for(Element e : elementList){
//            if(e.attribute("_class")!=null && e.attribute("_class").getValue().equals("hudson.plugins.testng.TestNGTestResultBuildAction")){
//                int fail = Integer.parseInt(e.element("failCount").getText());
//                System.out.println(fail);
//            }
//        }
//    }
}
