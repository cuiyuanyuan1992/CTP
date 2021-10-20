package org.example.service.impl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.example.dto.TpJobDTO;
import org.example.service.BaseJob;
import org.example.utils.JenkinsUtil;
import org.springframework.util.ObjectUtils;

public class ITJob implements BaseJob {
    private final String gitCredentialsId = "7eb52f73-3c6d-44e0-847d-8dc0c5e74cc5";
    private final String mailHtml = "<!DOCTYPE html>\n" +
            "<html>\n" +
            " <head> \n" +
            "  <meta charset=\"UTF-8\" /> \n" +
            "  <title>${ENV, var=&quot;JOB_NAME&quot;}-第${BUILD_NUMBER}次构建日志</title> \n" +
            " </head> \n" +
            " <body leftmargin=\"8\" marginwidth=\"0\" topmargin=\"8\" marginheight=\"4\" offset=\"0\"> \n" +
            "  <div> \n" +
            "   <br />\n" +
            "   <br />\n" +
            "   <table width=\"95%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif\"> \n" +
            "    <tbody>\n" +
            "     <tr>\n" +
            "       本邮件由系统自动发出，无需回复！ 各位同事，大家好，以下为${PROJECT_NAME }项目构建信息 \n" +
            "      <td><font color=\"#CC0000\">构建结果 - ${BUILD_STATUS}</font></td> \n" +
            "     </tr> \n" +
            "     <tr> \n" +
            "      <td><br /> <b><font color=\"#0B610B\">构建信息</font></b> \n" +
            "       <hr size=\"2\" width=\"100%\" align=\"center\" /></td> \n" +
            "     </tr> \n" +
            "     <tr> \n" +
            "      <td> \n" +
            "       <ul> \n" +
            "        <li>项目名称 ： ${PROJECT_NAME}</li> \n" +
            "        <li>构建编号 ： 第${BUILD_NUMBER}次构建</li> \n" +
            "        <li>触发原因： ${CAUSE}</li> \n" +
            "        <li>构建状态： ${BUILD_STATUS}</li> \n" +
            "        <li>构建日志： <a href=\"${BUILD_URL}console\">${BUILD_URL}console</a></li> \n" +
            "        <li>构建 Url ： <a href=\"${BUILD_URL}\">${BUILD_URL}</a></li> \n" +
            "        <li>工作目录 ： <a href=\"${PROJECT_URL}ws\">${PROJECT_URL}ws</a></li> \n" +
            "        <li>项目 Url ： <a href=\"${PROJECT_URL}\">${PROJECT_URL}</a></li> \n" +
            "        <li>测试报告： <a href=\"${PROJECT_URL}allure\">${PROJECT_URL}allure</a></li> \n" +
            "       </ul> </td> \n" +
            "     </tr> \n" +
            "    </tbody>\n" +
            "   </table> \n" +
            "  </div>  \n" +
            " </body>\n" +
            "</html>";

    private String itJobXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+"<project>\n" +
            "<actions/>\n" +
            "<description/>\n" +
            "<keepDependencies>false</keepDependencies>\n" +
            "<properties>\n" +
            "<jenkins.model.BuildDiscarderProperty>\n" +
            "<strategy class=\"hudson.tasks.LogRotator\">\n" +
            "<daysToKeep>7</daysToKeep>\n" +
            "<numToKeep>10</numToKeep>\n" +
            "<artifactDaysToKeep>-1</artifactDaysToKeep>\n" +
            "<artifactNumToKeep>-1</artifactNumToKeep>\n" +
            "</strategy>\n" +
            "</jenkins.model.BuildDiscarderProperty>\n" +
            "</properties>\n" +
            "<scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@4.8.2\">\n" +
            "<configVersion>2</configVersion>\n" +
            "<userRemoteConfigs>\n" +
            "<hudson.plugins.git.UserRemoteConfig>\n" +
            "<url></url>\n" +
            "<credentialsId>"+gitCredentialsId+"</credentialsId>\n" +
            "</hudson.plugins.git.UserRemoteConfig>\n" +
            "</userRemoteConfigs>\n" +
            "<branches>\n" +
            "<hudson.plugins.git.BranchSpec>\n" +
            "<name></name>\n" +
            "</hudson.plugins.git.BranchSpec>\n" +
            "</branches>\n" +
            "<doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
            "<submoduleCfg class=\"empty-list\"/>\n" +
            "<extensions/>\n" +
            "</scm>\n" +
            "<canRoam>true</canRoam>\n" +
            "<disabled>false</disabled>\n" +
            "<blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
            "<blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
            "<triggers/>\n" +
            "<concurrentBuild>false</concurrentBuild>\n" +
            "<builders>\n" +
            "<hudson.tasks.Shell>\n" +
            "<command></command>\n" +
            "<configuredLocalRules/>\n" +
            "</hudson.tasks.Shell>\n" +
            "</builders>\n" +
            "<publishers>\n" +
            "<ru.yandex.qatools.allure.jenkins.AllureReportPublisher plugin=\"allure-jenkins-plugin@2.29.0\">\n" +
            "<configPath/>\n" +
            "<jdk/>\n" +
            "<properties/>\n" +
            "<results>\n" +
            "<ru.yandex.qatools.allure.jenkins.config.ResultsConfig>\n" +
            "<path>allure-results</path>\n" +
            "</ru.yandex.qatools.allure.jenkins.config.ResultsConfig>\n" +
            "</results>\n" +
            "<reportBuildPolicy>ALWAYS</reportBuildPolicy>\n" +
            "<includeProperties>false</includeProperties>\n" +
            "<disabled>false</disabled>\n" +
            "<report>allure-report</report>\n" +
            "</ru.yandex.qatools.allure.jenkins.AllureReportPublisher>\n" +
            "<hudson.plugins.emailext.ExtendedEmailPublisher plugin=\"email-ext@2.83\">\n" +
            "<recipientList></recipientList>\n" +
            "<configuredTriggers>\n" +
            "<hudson.plugins.emailext.plugins.trigger.AlwaysTrigger>\n" +
            "<email>\n" +
            "<subject>$PROJECT_DEFAULT_SUBJECT</subject>\n" +
            "<body>$PROJECT_DEFAULT_CONTENT</body>\n" +
            "<recipientProviders>\n" +
            "<hudson.plugins.emailext.plugins.recipients.DevelopersRecipientProvider/>\n" +
            "<hudson.plugins.emailext.plugins.recipients.ListRecipientProvider/>\n" +
            "</recipientProviders>\n" +
            "<attachmentsPattern/>\n" +
            "<attachBuildLog>false</attachBuildLog>\n" +
            "<compressBuildLog>false</compressBuildLog>\n" +
            "<replyTo>$PROJECT_DEFAULT_REPLYTO</replyTo>\n" +
            "<contentType>project</contentType>\n" +
            "</email>\n" +
            "</hudson.plugins.emailext.plugins.trigger.AlwaysTrigger>\n" +
            "</configuredTriggers>\n" +
            "<contentType>text/html</contentType>\n" +
            "<defaultSubject>$DEFAULT_SUBJECT</defaultSubject>\n" +
            "<defaultContent></defaultContent>\n" +
            "<attachmentsPattern/>\n" +
            "<presendScript>$DEFAULT_PRESEND_SCRIPT</presendScript>\n" +
            "<postsendScript>$DEFAULT_POSTSEND_SCRIPT</postsendScript>\n" +
            "<attachBuildLog>false</attachBuildLog>\n" +
            "<compressBuildLog>false</compressBuildLog>\n" +
            "<replyTo>$DEFAULT_REPLYTO</replyTo>\n" +
            "<from/>\n" +
            "<saveOutput>false</saveOutput>\n" +
            "<disabled>false</disabled>\n" +
            "</hudson.plugins.emailext.ExtendedEmailPublisher>\n" +
            "</publishers>\n" +
            "<buildWrappers>\n" +
            "<hudson.plugins.build__timeout.BuildTimeoutWrapper plugin=\"build-timeout@1.20\">\n" +
            "<strategy class=\"hudson.plugins.build_timeout.impl.AbsoluteTimeOutStrategy\">\n" +
            "<timeoutMinutes></timeoutMinutes>\n" +
            "</strategy>\n" +
            "<operationList>\n" +
            "<hudson.plugins.build__timeout.operations.FailOperation/>\n" +
            "</operationList>\n" +
            "</hudson.plugins.build__timeout.BuildTimeoutWrapper>\n" +
            "</buildWrappers>\n" +
            "</project>";
    @Override
    public String getJobXml(TpJobDTO jobDTO) {
        Document document = this.setXml(jobDTO,itJobXml);
        return document.asXML();
    }

    @Override
    public String updateJobXml(TpJobDTO jobDTO) {
        //获取任务配置的xml
        JenkinsUtil jenkinsUtil = new JenkinsUtil();
        String jobXml = jenkinsUtil.getJobConfig(jobDTO.getJobName());
        Document document = this.setXml(jobDTO,jobXml);
        return document.asXML();
    }

    private Document setXml(TpJobDTO jobDTO,String jobXml){
        Document document = null;
        try {
            document = DocumentHelper.parseText(jobXml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取文档根节点
        Element root = document.getRootElement();
        if(!ObjectUtils.isEmpty(jobDTO.getJobDesc())){
            Element description = root.element("description");
            description.setText(jobDTO.getJobDesc());
        }
        if(!(ObjectUtils.isEmpty(jobDTO.getGitUrl()) || ObjectUtils.isEmpty(jobDTO.getGitBranch()))){
            Element url = root.element("scm").element("userRemoteConfigs").element("hudson.plugins.git.UserRemoteConfig").element("url");
            url.setText(jobDTO.getGitUrl());

            Element branch = root.element("scm").element("branches").element("hudson.plugins.git.BranchSpec").element("name");
            branch.setText(jobDTO.getGitBranch());
        }
        if(!ObjectUtils.isEmpty(jobDTO.getCommand())){
            Element command = root.element("builders").element("hudson.tasks.Shell").element("command");
            command.setText(jobDTO.getCommand());
        }
        if(!ObjectUtils.isEmpty(jobDTO.getRecipients())){
            Element recipientList = root.element("publishers").element("hudson.plugins.emailext.ExtendedEmailPublisher").element("recipientList");
            recipientList.setText(jobDTO.getRecipients());
        }
        if(!ObjectUtils.isEmpty(jobDTO.getTimeoutMinutes())){
            Element timeoutMinutes = root.element("buildWrappers").element("hudson.plugins.build__timeout.BuildTimeoutWrapper").element("strategy").element("timeoutMinutes");
            timeoutMinutes.setText(jobDTO.getTimeoutMinutes().toString());
        }
        Element defaultContent = root.element("publishers").element("hudson.plugins.emailext.ExtendedEmailPublisher").element("defaultContent");
        if(ObjectUtils.isEmpty(defaultContent.getText())){
            defaultContent.setText(mailHtml);
        }
        return document;
    }
}
