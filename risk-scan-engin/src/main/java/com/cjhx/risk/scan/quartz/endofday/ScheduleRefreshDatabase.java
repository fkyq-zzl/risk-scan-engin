package com.cjhx.risk.scan.quartz.endofday;

import java.text.ParseException;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.cjhx.risk.scan.dao.RcRuleItemMapper;

@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshDatabase {
	private static Logger logger = LoggerFactory.getLogger(ScheduleRefreshDatabase.class);

	@Autowired
	private RcRuleItemMapper rcRuleItemMapper;

	@Autowired
	private ScheduleTask scheduleTask;
	
//	@Resource(name = "scanJobDetail")
//	private JobDetail scanJobDetail;

	@Resource(name = "scanJobTrigger")
	private CronTrigger scanJobTrigger;

	@Resource(name = "singleScanJobTrigger")
	private CronTrigger singleScanJobTrigger;

	@Resource(name = "scanScheduler")
	private Scheduler scanScheduler;
	
	// @Scheduled(fixedRate = 60000) // 每隔60s查库，并根据查询结果决定是否重新设置定时任务
	@Scheduled(cron = "0 0 0 * * ?") // "0 0 0 * * ?"每天凌晨0点执行 TODO
	public void scheduleUpdateCronTrigger() {
		//更新持仓数据的日期偏移天数
		this.scanDatabaseDay();
		//更新定时任务执行时间
		this.scanCronTrigger();
		//更新执行手动任务查询频率
		this.singleScanCronTrigger();
	}
	
	private void scanDatabaseDay(){
		try {
			String dayStr = rcRuleItemMapper.selectScanDatabaseDayFromDomainValue();
			if(StringUtils.isEmpty(dayStr)){
				logger.error("======= scanDatabaseDay() RC_DOMAIN_VALUE.scan_database_day 持仓数据的日期偏移天数 为空 =======");
				return;
			}
			int dayInt = Integer.valueOf(dayStr);
			int baseDay = scheduleTask.getScanDatabaseDay();
			if(dayInt != baseDay){
				scheduleTask.setScanDatabaseDay(dayInt);
				logger.info("====== 持仓数据的日期偏移天数scanDatabaseDay() 更新前：" + baseDay+ " 更新后：" + dayInt + " ======");
			}
		} catch (Exception e) {
			logger.error("更新持仓数据的日期偏移天数 scanDatabaseDay() error:" + e);
			e.printStackTrace();
			return;
		}
	}
	
	private void scanCronTrigger(){
		CronTrigger trigger = null;
		try {
			trigger = (CronTrigger) scanScheduler.getTrigger(scanJobTrigger.getKey());

			String currentCron = trigger.getCronExpression();// 当前Trigger使用的
			//从数据库查询出来的表达式  "0 0/10 * * * ?"
			String searchCron = rcRuleItemMapper.selectScanQuartzCronFromDomainValue();
			if (searchCron!=null&&!currentCron.equals(searchCron)) {
				// 如果当前使用的cron表达式和从数据库中查询出来的cron表达式不一致，则刷新任务
				logger.info(scanJobTrigger.getKey().getName() + " 当前Trigger表达式:" + currentCron);
				logger.info(scanJobTrigger.getKey().getName() + " 更新Trigger表达式:" + searchCron);
				// 表达式调度构建器
//				增加：withMisfireHandlingInstructionDoNothing()方法 
//				1，不触发立即执行 
//				2，等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron).withMisfireHandlingInstructionDoNothing();
				// 按新的cronExpression表达式重新构建trigger
				// trigger = (CronTrigger)
				// scanScheduler.getTrigger(scanJobTrigger.getKey());
				trigger = trigger.getTriggerBuilder().withIdentity(scanJobTrigger.getKey()).withSchedule(scheduleBuilder)
						.build();
				// 按新的trigger重新设置job执行
				scanScheduler.rescheduleJob(scanJobTrigger.getKey(), trigger);
				currentCron = searchCron;
			}
		} catch (Exception e) {
			logger.error("scheduleUpdateCronTrigger() error:" + e);
			e.printStackTrace();
			return;
		}
	}
	
	
	private void singleScanCronTrigger(){
		CronTrigger trigger = null;
		try {
			trigger = (CronTrigger) scanScheduler.getTrigger(singleScanJobTrigger.getKey());

			String currentCron = trigger.getCronExpression();// 当前Trigger使用的
			//从数据库查询出来的表达式  "0 0/10 * * * ?"
			String searchCron = rcRuleItemMapper.selectSingleScanQuartzCronFromDomainValue();
			if (searchCron!=null&&!currentCron.equals(searchCron)) {
				// 如果当前使用的cron表达式和从数据库中查询出来的cron表达式不一致，则刷新任务
				logger.info(singleScanJobTrigger.getKey().getName() + " 当前Trigger表达式:" + currentCron);
				logger.info(singleScanJobTrigger.getKey().getName() + " 更新Trigger表达式:" + searchCron);
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron).withMisfireHandlingInstructionDoNothing();
				// 按新的cronExpression表达式重新构建trigger
				// trigger = (CronTrigger)
				// scanScheduler.getTrigger(scanJobTrigger.getKey());
				trigger = trigger.getTriggerBuilder().withIdentity(scanJobTrigger.getKey()).withSchedule(scheduleBuilder)
						.build();
				// 按新的trigger重新设置job执行
				scanScheduler.rescheduleJob(singleScanJobTrigger.getKey(), trigger);
				currentCron = searchCron;
			}
		} catch (Exception e) {
			logger.error("scheduleUpdateCronTrigger() error:" + e);
			e.printStackTrace();
			return;
		}
	}
	
//	@Scheduled(cron = "19 25 17 * * ?") 
	public void scheduleSingleTask(){
		scheduleTask.scheduleTask();
		
	}
	
	private void test() {
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(true);
		jobDetail.setName("RISK-TASK-NAME");
		jobDetail.setGroup("RISK-TASK-GROUP");
		jobDetail.setTargetObject(new ScheduleTask());
		jobDetail.setTargetMethod("sayHello");
		try {
			jobDetail.afterPropertiesSet();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		tigger.setCronExpression("0/2 * * * * ?");// 初始时的cron表达式
		// tigger.setStartTime(new Date());
		tigger.setName("RISK-TRIGGER-NAME1");
		tigger.setGroup("RISK-TRIGGER-GROUP");
		try {
			tigger.afterPropertiesSet();
			System.out.println("tigger.getObject():" + tigger.getObject());
			System.out.println("tigger.getObject().getKey():" + tigger.getObject().getKey());
			System.out.println("scanScheduler:" + scanScheduler);
			scanScheduler.addJob(jobDetail.getObject(), true);
			scanScheduler.scheduleJob(tigger.getObject());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void test1() {
		// try {
		// scanScheduler.pauseTrigger(scanJobTrigger.getKey());
		// scanScheduler.unscheduleJob(scanJobTrigger.getKey());
		// } catch (SchedulerException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }

		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(true);
		jobDetail.setName("RISK-TASK-NAME1");
		jobDetail.setGroup("RISK-TASK-GROUP");
		jobDetail.setTargetObject(new ScheduleTask());
		jobDetail.setTargetMethod("sayHello");
		try {
			jobDetail.afterPropertiesSet();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SimpleTriggerFactoryBean tigger = new SimpleTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		tigger.setRepeatCount(0);// 重复执行2次
		tigger.setRepeatInterval(0);// 每3000毫秒一次
		// tigger.setStartTime(new Date());
		tigger.setName("RISK-TRIGGER-NAME1");
		tigger.setGroup("RISK-TRIGGER-GROUP");
		try {
			tigger.afterPropertiesSet();
			scanScheduler.addJob(jobDetail.getObject(), true);
			scanScheduler.scheduleJob(tigger.getObject());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
