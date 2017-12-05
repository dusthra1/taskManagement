package com.mindtree.task.scheduler.tasks;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FirstTask {
     
    private static Logger log = Logger.getLogger(FirstTask.class);
     
    /**
     * Execute this task
     * 
     */
    public void execute() {
        log.debug("FirstTask runs successfully...");
    }   
}
