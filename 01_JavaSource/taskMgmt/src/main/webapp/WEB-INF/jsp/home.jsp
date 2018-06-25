<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title>Task Management System</title>
<link rel="stylesheet" type="text/css" href="css/task.css">
</head>
<body>

	<div align="center">
	<c:if test="${message!=null && message!=''}">
   				<table>
   				<tr><td><img src="images/green-tickmark.jpg" alt="" width="30" height="30" /> </td>
   				<td>${message}</td></tr>
   				</table>
	</c:if>
	
	<c:if test="${errorMessage!=null && errorMessage!=''}">
	   				<table>
	   				<tr><td></td></tr>
	   				<tr>
	   				<td><img src="images/error-icon1.png" alt="" width="30" height="30" /></td>
	   				<td>${errorMessage}</td>
	   				</tr>
	   				</table>
	</c:if> 
		
	</div>
</body>
</html>