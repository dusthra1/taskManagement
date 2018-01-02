package com.mindtree.task.scheduler.jobs;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class FirstJob implements Job{
	
	private static Logger log = Logger.getLogger(FirstJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		 log.debug("Firstjob runs successfully...");
	}

}
