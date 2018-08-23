<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
</head>

<div style="background: #E0E0E0; height: 55px; padding: 15px;">
  <div style="float: left">
     <h1>Task Management</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right;">
 	Welcome : <span><c:out value='${sessionScope.user.firstName} ${sessionScope.user.lastName}'/></span><br/><br/>	
	
	<c:choose> 
	  <c:when test = "${sessionScope.loginAs == true}">
	 	 <span id='returnAdminHome'><a href='adminReturnHome.do' title='ReturnToAdminHome'>Return to Admin Home</a></span>
	  </c:when>
	  <c:otherwise>
	    <span id='logout'><a href='logoff.do' title='Logout'>Logout</a></span>
	  </c:otherwise>
	</c:choose>   
 
  </div>
 
</div>