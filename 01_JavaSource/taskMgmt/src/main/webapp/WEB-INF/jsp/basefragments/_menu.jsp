  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="padding: 5px;">
 
   <ul>
 
       <li><a href="assignTask.do">Assign Task</a></li>
       <li><a href="viewTasks.do">View Tasks</a></li>
       <li><a href="viewProjects.do">Manage Projects</a></li>
       <li><a href="empreport.do">Employees Report</a></li>
       <li><a href="welcome-flow.do">Start welcome flow</a></li>
        <li><a href="fileUpload.do">Upload File</a></li>
       
       <sec:authorize access="hasAuthority('ADMIN')">
	    <c:choose> 
		  <c:when test = "${sessionScope.loginAs == true}">
		  	 <li><a href="returnAdminHome.do">Return to Admin Home</a></li>
		  </c:when>
		  <c:otherwise>
		  		 <li><a href="adminHome.do">Admin Home</a></li>
		  </c:otherwise>
		</c:choose>
        </sec:authorize>
   </ul>
 
</div>