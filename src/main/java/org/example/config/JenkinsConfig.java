package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JenkinsConfig {
    //@Value无法直接给static变量赋值需要特殊处理
    public static String JENKINS_HOST;
    @Value("${jenkins.host}")
    public void setJenkinsHost(String jenkinsHost){
        this.JENKINS_HOST = jenkinsHost;
    }

    public static String JENKINS_USERNAME;
    @Value("${jenkins.username}")
    public void setJenkinsUsername(String jenkinsUsername){
        this.JENKINS_USERNAME = jenkinsUsername;
    }

    public static String JENKINS_PASSWORD;
    @Value("${jenkins.password}")
    public void setJenkinsPassword(String jenkinsPassword){
        this.JENKINS_PASSWORD = jenkinsPassword;
    }
}
