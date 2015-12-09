package com.bern.quartz.principle.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobA implements Job {
	
	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		System.out.println("Job A is runing //every 5 seconds ");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("JobA sleep end");
	}
}
