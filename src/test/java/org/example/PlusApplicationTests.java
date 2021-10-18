package org.example;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.TestReport;
import org.example.utils.JenkinsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;

@SpringBootTest(classes = PlusApplication.class)
class PlusApplicationTests {


    /**
     * QueryWrapper : allEq(=),eq(=),ne(<>),gt(>),ge(>=),lt(<),le(<=)
     */
    @Test
    void contextLoads() throws IOException {

        JenkinsUtil jenkinsUtil = new JenkinsUtil();
        BuildResult report = jenkinsUtil.getJobLastBuild("cyytest").details().getResult();
        System.out.println(report);
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
