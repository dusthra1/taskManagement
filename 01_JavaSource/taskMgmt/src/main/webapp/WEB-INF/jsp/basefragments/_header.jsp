<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
  <div style="float: left">
     <h1>Task Management</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right;">
 	Welcome : <span><c:out value='${sessionScope.user.firstName} ${sessionScope.user.lastName}'/></span><br/><br/>	
	
	<c:choose> 
	  <c:when test = "${sessionScope.loginAs == true}">
	 	 <span id='returnAdminHome'><a href='returnAdminHome.do' title='ReturnToAdminHome'>Return to Admin Home</a></span>
	  </c:when>
	  <c:otherwise>
	    <span id='logout'><a href='logoff.do?st=0' title='Logout'>Logout</a></span>
	  </c:otherwise>
	</c:choose>   
 
  </div>
 
</div>