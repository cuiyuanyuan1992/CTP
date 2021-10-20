package org.example;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.TestReport;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.example.utils.JenkinsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;

@SpringBootTest(classes = PlusApplication.class)
class PlusApplicationTests {
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
            "<credentialsId>"+0000+"</credentialsId>\n" +
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
            "<defaultContent>"+"mailHtml"+"</defaultContent>\n" +
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


    /**
     * QueryWrapper : allEq(=),eq(=),ne(<>),gt(>),ge(>=),lt(<),le(<=)
     */
    @Test
    void contextLoads() throws IOException {
        Document document = null;
        try {
            document = DocumentHelper.parseText(itJobXml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取文档根节点
        Element root = document.getRootElement();
//        JenkinsUtil jenkinsUtil = new JenkinsUtil();
//        BuildResult report = jenkinsUtil.getJobLastBuild("cyytest").details().getResult();
//        System.out.println(report);
    }

    /**
     * 输出引导语
     */
    public void welcome() {
        System.out.println();
        System.out.println("   .  ._______.______. _____._______.__ _ _   ");
        System.out.println("  /\\\\ |__   __|  ____|/ ____|__   __|\\ \\ \\ \\  ");
        System.out.println(" ( ( )   | |  | |____/ /____   | |    \\ \\ \\ \\ ");
        System.out.println("  \\\\/    | |  |  ____| '___ \\  | |     ) ) ) )");
        System.out.println("   '     | |  | |____._____) | | |    / / / / ");
        System.out.println("  =======|_|==|______.\\_____/==|_|===/_/ /_/  ");
        System.out.println("  :: Spring Test ::            (v2.1.3.RELEASE)");
        System.out.println();
    }
}
