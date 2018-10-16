<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
 <head>
 	 <sec:csrfMetaTags/>
	 <title>Manage Employees</title>	  
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	 
	 <link type="text/css" rel="stylesheet" href="javascript/jsgrid/jsgrid.min.css" />
	 <link type="text/css" rel="stylesheet" href="javascript/jsgrid/jsgrid-theme.min.css" />
	
 </head>

 <body>
  
  <div id="jsGrid"></div>
  
 	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
 	<script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
	<script type="text/javascript" src="javascript/jsgrid/jsgrid.min.js"></script>
  	<script type="text/javascript" src="javascript/viewEmployees.js"></script>
 </body>
</html>