package com.mindtree.task.scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import com.mindtree.task.scheduler.tasks.FirstTask;
import com.mindtree.task.scheduler.tasks.SecondTask;

public class SchedulerService implements ISchedulerService {
	
	@Autowired
	private FirstTask  firstTask;
	
	@Autowired
	private SecondTask secondTask;

	@Override
	public void executeFirstTask() {
		firstTask.execute();
	}

	@Override
	public void executeSecondTask() {
		 secondTask.execute();
	}
}
