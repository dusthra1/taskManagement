<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>
</head>
<body>
<center>
 		
 		<c:choose> 		
 		<c:when test="${not empty errorMessage}">
 			<table>
   				<tr><td><img src="images/error-icon1.png" alt="" width="30" height="30" /> </td>
   				<td><fmt:message key="${errorMessage}"/></td></tr>
   			</table>
 		</c:when>
 		<c:when test="${not empty errorCode}">
 			<table>
   				<tr><td><img src="images/error-icon1.png" alt="" width="30" height="30" /> </td>
   				<td><fmt:message key="${errorCode}"/></td></tr>
   			</table>
 		</c:when>
 		<c:otherwise>
 		<table>
   				<tr>
	   				<td><img src="images/error-icon1.png" alt="" width="30" height="30" /> </td>
		   			<td>
		   				<p>Error Occurred. Unable To Process Your Request. Please report this issue to administrator </p>
		   			</td>
   				</tr>
   		</table>
        </c:otherwise>
        </c:choose>
        
 </center>

</body>
</html>