package com.mindtree.task.listners;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.service.TaskService;

@WebListener
public class AppContextListener implements ServletContextListener {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	
	private static final Logger log = Logger.getLogger(AppContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);		
		
		try{
			log.info("Updating all users login status to N");
			taskService.updateUsersLoginStatus();
			
			Scheduler scheduler = schedulerFactory.getScheduler();
			log.info("-------------------------Listing Cron Job Details on Startup------------------------");
			for (String groupName : scheduler.getJobGroupNames()) {

			     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

				  String jobName = jobKey.getName();
				  String jobGroup = jobKey.getGroup();

				  //get job's trigger
				  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
				  Date nextFireTime = triggers.get(0).getNextFireTime();
				  log.info("JobName : " + jobName +  " GroupName : " + jobGroup + " - " + nextFireTime);
				  }
			    }
			log.info("------------------------------------------------------------------------------------");
			
		}catch (Exception ex) {
			log.error("ERROR Occurred", ex);
			ApplicationException ae = new ApplicationException(ex.getMessage(), ex);
			throw ae;
		}		
	}

}
