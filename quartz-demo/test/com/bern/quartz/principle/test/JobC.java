package com.bern.quartz.principle.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobC implements Job {
	
	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		System.out.println("Job C is runing");
	}
}
