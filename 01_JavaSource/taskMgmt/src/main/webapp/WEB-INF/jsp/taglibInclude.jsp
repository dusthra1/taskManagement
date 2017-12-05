<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

	<c:set var="url" value=""/>
	<c:choose>
	<c:when test="${not empty refreshUrl && ''!=refreshUrl}">
		<c:set var="url" value="${refreshUrl}" />
	</c:when>
	<c:otherwise>
		<c:set var="url" value="${sessionScope.refreshUrl}" />
	</c:otherwise>
	</c:choose>


	<meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url=<c:out value="${url}" />">		
	<meta http-equiv="Content-Type" content="text/html; utf-8">
	<meta http-equiv="Content-Language" content="en-us" /> 
	
	<script type="text/javascript" src="javascript/jquery-3.2.1.min.js"></script>
	<link rel="stylesheet"  href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="javascript/task.js"></script>

<title></title>
</head>
<body>

</body>
</html>