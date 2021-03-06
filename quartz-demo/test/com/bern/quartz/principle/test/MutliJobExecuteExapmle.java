package com.bern.quartz.principle.test;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

public class MutliJobExecuteExapmle {
	public static void main( String[] args ) throws Exception {
 
		JobKey jobKeyA = new JobKey("jobA", "group1");
    	JobDetail jobA = JobBuilder.newJob(JobA.class)
		.withIdentity(jobKeyA).build();
 
    	JobKey jobKeyB = new JobKey("jobB", "group1");
    	JobDetail jobB = JobBuilder.newJob(JobB.class)
		.withIdentity(jobKeyB).build();
 
    	JobKey jobKeyC = new JobKey("jobC", "group1");
    	JobDetail jobC = JobBuilder.newJob(JobC.class)
		.withIdentity(jobKeyC).build();
 
 
    	Trigger trigger1 = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName1", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();
 
    	Trigger trigger2 = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName2", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();
 
    	Trigger trigger3 = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName3", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();
    	
    	StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
    	schedulerFactory.initialize("resources/quartz.properties");
    	Scheduler scheduler = schedulerFactory.getScheduler();
    	
    	scheduler.start();
//    	scheduler.scheduleJob(jobA, trigger1);
//    	scheduler.scheduleJob(jobB, trigger2);
//    	scheduler.scheduleJob(jobC, trigger3);
    	
    	scheduler.deleteJob(jobKeyA);
//    	scheduler.pauseJob(jobKeyB);
    	scheduler.resumeJob(jobKeyB);
    }
}
