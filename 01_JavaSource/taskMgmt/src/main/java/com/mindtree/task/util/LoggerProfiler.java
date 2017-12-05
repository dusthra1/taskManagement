package com.mindtree.task.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Component
@Aspect
public class LoggerProfiler {	
	private static final Logger log = Logger.getLogger(LoggerProfiler.class);
	
	@Around("controllerLayer() || serviceLayer() || daoLayer()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();		
		log.debug("Entered :" + pjp.getTarget().getClass().getName()+" | "+pjp.getSignature().getName());		
		Object output = pjp.proceed();		
		log.debug("Exiting :" + pjp.getTarget().getClass().getName()+" | "+pjp.getSignature().getName());
		long elapsedTime = (System.currentTimeMillis() - start);		
		log.trace("Execution_Time: "+ pjp.getTarget().getClass().getName()+" | "+pjp.getSignature().getName()+"| " + elapsedTime+ " ms");		
		return output;
	}
	
	//@Pointcut("execution(public * com.mindtree.task.controller.FrontController.*(..))")
	
	@Pointcut("within(com.mindtree.task.controller.*)")
	public void controllerLayer(){}
	
	@Pointcut("within(com.mindtree.task.service.*)")
	public void serviceLayer(){}
	
	@Pointcut("within(com.mindtree.task.dao.*)")
	public void daoLayer(){}
	
	
}
