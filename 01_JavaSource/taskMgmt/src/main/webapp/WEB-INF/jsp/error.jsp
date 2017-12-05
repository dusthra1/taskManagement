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
 			<h3><fmt:message key="${errorMessage}"/></h3>
 		</c:when>
 		<c:when test="${not empty errorCode}">
 			<h3><fmt:message key="${errorCode}"/></h3>
 		</c:when>
 		<c:otherwise>
	        <h3>Error Occurred. Unable To Process Your Request.</h3>
	        <h3>Please report this issue to administrator</h3>
        </c:otherwise>
        </c:choose>
        
 </center>

</body>
</html>