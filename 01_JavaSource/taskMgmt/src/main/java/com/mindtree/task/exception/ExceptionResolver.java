package com.mindtree.task.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.constants.MessageCode;

@ControllerAdvice
public class ExceptionResolver extends DefaultHandlerExceptionResolver {
	
	private static final Logger log = Logger.getLogger(ExceptionResolver.class);

	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String ERROR_CODE = "errorCode";
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		super.doResolveException(request, response, handler, ex);
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		log.error("--ALERT-- Error Occured: " + stacktrace);

		ModelAndView mav = new ModelAndView(ApplicationConstants.ERROR_PAGE);

		if (ex instanceof HibernateException) {
			mav.addObject(ERROR_CODE, MessageCode.GENERIC_ERROR);

		} else if (ex instanceof ApplicationException) {
			ApplicationException appExp = (ApplicationException) ex;
			mav.addObject(ERROR_MESSAGE, appExp.getErrorMessage());
			mav.addObject(ERROR_CODE, appExp.getErrorCode());

		}  else if (ex instanceof AccessDeniedException) {
			mav.addObject(ERROR_CODE, MessageCode.ACCESS_DENIED);

		} else {
			// Default case when Exception does not match any of the above
			// instances
			mav.addObject(ERROR_CODE, MessageCode.GENERIC_ERROR);
		}
		return mav;
	}

}
