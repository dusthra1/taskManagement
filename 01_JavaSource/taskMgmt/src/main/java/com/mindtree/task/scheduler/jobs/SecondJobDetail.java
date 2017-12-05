package com.mindtree.task.scheduler.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mindtree.task.scheduler.ISchedulerService;

public class SecondJobDetail extends QuartzJobBean {
	
	private static final Logger log = Logger.getLogger(SecondJobDetail.class);
	
	  private ISchedulerService schedulerService;
	  private JobExecutionContext jobExecutionContext;

	@Override
	protected void executeInternal(JobExecutionContext jobExecContext) throws JobExecutionException {

        //JobExecutionContext is being set...
        setJobExecutionContext(jobExecContext);
         
        //Second Task is being executing...
        getSchedulerService().executeSecondTask();
        
        printJobDetails(jobExecContext);
    
	}

	  private void  printJobDetails(JobExecutionContext jobExecContext){
	    	
	    	log.info("---------Job History-------------------");
	    	log.info("Job Name: SecondTask");
	    	log.info("Job fired at: "+jobExecContext.getFireTime());
	    	log.info("Job Run Time in milliSec: "+jobExecContext.getJobRunTime());
	    	log.info("Next fire time: "+jobExecContext.getNextFireTime());
	    	log.info("---------------------------------------");
	    	
	    }
	
	public ISchedulerService getSchedulerService() {
		return schedulerService;
	}

	public JobExecutionContext getJobExecutionContext() {
		return jobExecutionContext;
	}

	public void setSchedulerService(ISchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	public void setJobExecutionContext(JobExecutionContext jobExecutionContext) {
		this.jobExecutionContext = jobExecutionContext;
	}
	
	

}
