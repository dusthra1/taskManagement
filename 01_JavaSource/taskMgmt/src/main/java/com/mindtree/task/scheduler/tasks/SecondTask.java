package com.mindtree.task.scheduler.tasks;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SecondTask {
 
    private static Logger log = Logger.getLogger(SecondTask.class);
     
    /**
     * Execute this task
     * 
     */
    public void execute() {
        log.debug("SecondTask runs successfully...");
    }   
}
