package org.example.utils;

import java.text.ParseException;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzManager {
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param jobName 任务名
     * @param jobClass 任务
     * @param time 时间设置
     */
    public static void addJob(String jobName, Class jobClass, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();// 任务名，任务组，任务执行类
            // 触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().
                    withIdentity(jobName, TRIGGER_GROUP_NAME).
                    withSchedule(CronScheduleBuilder.cronSchedule(time)).
                    build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()){
                sched.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass 任务
     * @param time 时间设置
     */
    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
            // 触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().
                    withIdentity(triggerName, triggerGroupName).
                    withSchedule(CronScheduleBuilder.cronSchedule(time)).
                    build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()){
                sched.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     * @param time
     */
    public static void modifyJobTime(String jobName, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(new TriggerKey(jobName,TRIGGER_GROUP_NAME));
            if(trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobDetail jobDetail = sched.getJobDetail(new JobKey(jobName, JOB_GROUP_NAME));
                Class objJobClass = jobDetail.getJobClass();
                removeJob(jobName);

                addJob(jobName, objJobClass, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param time
     */
    public static void modifyJobTime(String jobName,String jobGroupName,String triggerName,
                                     String triggerGroupName, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(new TriggerKey(triggerName,triggerGroupName));
            if(trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobDetail jobDetail = sched.getJobDetail(new JobKey(jobName, jobGroupName));
                Class objJobClass = jobDetail.getJobClass();
                removeJob(jobName,jobGroupName,triggerName, triggerGroupName);

                addJob(jobName,jobGroupName,triggerName, triggerGroupName,objJobClass, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 停止触发器
            sched.unscheduleJob(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 移除触发器
            sched.deleteJob(new JobKey(jobName, JOB_GROUP_NAME));// 删除任务
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(new TriggerKey(triggerName, triggerGroupName));// 停止触发器
            sched.unscheduleJob(new TriggerKey(triggerName, triggerGroupName));// 移除触发器
            sched.deleteJob(new JobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if(!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
