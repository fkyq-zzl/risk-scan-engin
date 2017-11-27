package com.cjhx.risk.scan.quartz.endofday;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;  
  
@Configuration  
/**
 * Quartz配置类 @//Qualifier
 *
 * @author lujinfu
 * @date 2017年10月10日
 */
public class QuartzConfigration {  
    /** 
     * attention: 
     * Details：配置定时任务 
     */  
    @Bean(name = "scanJobDetail")  
    public MethodInvokingJobDetailFactoryBean detailFactoryBean1(ScheduleTask scheduleTask) {// ScheduleTask task为需要执行的任务  
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();  
        /* 
         *  是否并发执行 
         *  例如每5s执行一次任务，但是当前任务还没有执行完，就已经过了5s了， 
         *  如果此处为true，则下一个任务会执行，如果此处为false，则下一个任务会等待上一个任务执行完后，再开始执行 
         */  
        jobDetail.setConcurrent(true);     
        jobDetail.setName("RISK-TASK-SCAN");// 设置任务的名字  
        jobDetail.setGroup("RISK-TASK-GROUP");// 设置任务的分组，这些属性都可以存储在数据库中，在多任务的时候使用  
          
        /* 
         * 为需要执行的实体类对应的对象 
         */  
        jobDetail.setTargetObject(scheduleTask);  
          
        /* 
         * sayHello为需要执行的方法 
         * 通过这几个配置，告诉JobDetailFactoryBean我们需要执行定时执行ScheduleTask类中的sayHello方法 
         */  
        jobDetail.setTargetMethod(scheduleTask.getTargetMethod());  
        return jobDetail;  
    } 
    
    /** 
     * attention: 
     * Details：配置定时任务 
     */  
    @Bean(name = "singleScanJobDetail")  
    public MethodInvokingJobDetailFactoryBean detailFactoryBean2(ScheduleTask scheduleTask) {// ScheduleTask task为需要执行的任务  
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();  
        /* 
         *  是否并发执行 
         *  例如每5s执行一次任务，但是当前任务还没有执行完，就已经过了5s了， 
         *  如果此处为true，则下一个任务会执行，如果此处为false，则下一个任务会等待上一个任务执行完后，再开始执行 
         */  
        jobDetail.setConcurrent(true);     
        jobDetail.setName("RISK-TASK-SINGLE_SCAN");// 设置任务的名字  
        jobDetail.setGroup("RISK-TASK-GROUP");// 设置任务的分组，这些属性都可以存储在数据库中，在多任务的时候使用  
          
        /* 
         * 为需要执行的实体类对应的对象 
         */  
        jobDetail.setTargetObject(scheduleTask);  
          
        /* 
         * sayHello为需要执行的方法 
         * 通过这几个配置，告诉JobDetailFactoryBean我们需要执行定时执行ScheduleTask类中的sayHello方法 
         */  
        jobDetail.setTargetMethod("singleScheduleTask");  
        return jobDetail;  
    }
    
    /** 
     * attention: 
     * Details：配置定时任务的触发器，也就是什么时候触发执行定时任务 
     */  
    @Bean(name = "scanJobTrigger")  
    public CronTriggerFactoryBean cronJobTrigger1(@Qualifier("scanJobDetail")MethodInvokingJobDetailFactoryBean jobDetail) {  
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();  
        tigger.setJobDetail(jobDetail.getObject());  
        tigger.setCronExpression("0 0 5 * * ?");// 初始时的cron表达式  
        tigger.setName("RISK-TRIGGER-SCAN");// trigger的name  
        tigger.setGroup("RISK-TRIGGER-GROUP");
        return tigger;  
  
    }  
  
    /** 
     * attention: 
     * Details：配置定时任务的触发器，也就是什么时候触发执行定时任务 
     */  
    @Bean(name = "singleScanJobTrigger")  
    public CronTriggerFactoryBean cronJobTrigger2(@Qualifier("singleScanJobDetail")MethodInvokingJobDetailFactoryBean jobDetail) {  
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();  
        tigger.setJobDetail(jobDetail.getObject());  
        tigger.setCronExpression("0 0/3 * * * ?");// 初始时的cron表达式  
        tigger.setName("RISK-TRIGGER-SINGLE_SCAN");// trigger的name  
        tigger.setGroup("RISK-TRIGGER-GROUP");
        return tigger;  
    } 
    
    /** 
     * attention: 
     * Details：定义quartz调度工厂 
     */  
    @Bean(name = "scanScheduler")  
    public SchedulerFactoryBean schedulerFactory(@Qualifier("scanJobTrigger")Trigger scanJobTrigger,
    		@Qualifier("singleScanJobTrigger")Trigger singleScanJobTrigger) {  
        SchedulerFactoryBean bean = new SchedulerFactoryBean();  
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job  
        bean.setOverwriteExistingJobs(false);// true  
        // 延时启动，应用启动1秒后  
        bean.setStartupDelay(60);  
        // 注册触发器  
        bean.setTriggers(scanJobTrigger,singleScanJobTrigger);
        return bean;  
    }  
} 
