package com.mindtree.task.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSONUtil {

	private static final Log log = LogFactory.getLog(JSONUtil.class);

	public void handleJSONResponse(HttpServletResponse response, String jsonString) {
		response.setContentType("application/json");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			log.error("Exception while rendering JSONResponse : ", e);
		}
	}
	
	public void handleHTMLResponse(HttpServletResponse response, String htmlString) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(htmlString);
			out.flush();
		} catch (IOException e) {
			log.error("Exception while rendering HTMLResponse : ", e);
		}
	}
}