<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<title>Task Management System</title>
<link rel="stylesheet" type="text/css" href="css/task.css">
</head>

<script type="text/javascript" src="javascript/task.js"></script>

<body>
	<div align="left">
<form:form action="addTask.do" modelAttribute="addTaskFormDetails" method="POST">
		
		<table align="center">
			<tr>
			<td><spring:message code="label.project" />: </td>
			<td>
				<form:errors path="projId" class="error" /><br/>
				<form:select path="projId"  name="projId" id="projId" onchange="loadProjEmp(this.value)">
				 <form:option value="0"> --SELECT--</form:option>				
				 <form:options items="${allProjList}" itemLabel="name" itemValue="id"/>				
				</form:select>
				</td>
			</tr>
			<tr>
			<td><spring:message code="label.taskName" />:</td>			
			<td>
				<form:errors path="taskName" class="error" /><br/>
				<form:input path="taskName"/>
			</td>
			</tr>
			
			<tr>
			<td><spring:message code="label.desc" />:</td>			
			<td>
				<form:errors path="taskDesc" class="error" /><br/>
				<form:input path="taskDesc"/>
			</td>
			</tr>
			
				<tr>
			<td><spring:message code="label.startDate" />:</td>			
			<td>
				<form:errors path="startDate" class="error" /><br/>
				<form:input path="startDate" readonly="readonly"/>
			</td>
			</tr>
			
			<tr>
			<td><spring:message code="label.dueDate" />:</td>			
			<td>
			<form:errors path="dueDate" class="error" /><br/>
				<form:input path="dueDate" readonly="readonly"/>
			</td>
			</tr>
			
			<tr>
			<td><spring:message code="label.employees" />: </td>
			<td>
			<form:errors path="empId" class="error" /><br/>
				<form:select path="empId" name="empId" id="empId" multiple="multiple">
				 <form:option value="0"> --SELECT--</form:option>
					<c:forEach items="${empList}" var="emp">
						<form:option value="${emp.getMid()}" label="${emp.getName()} - ${emp.getMid()}" />
					</c:forEach>				
				</form:select>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Task"></td>
				<td><input type="button" value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/home.do'"></td>
			</tr>
		</table>
		</form:form>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		var projId = document.getElementById("projId").value;
		if(projId > 0){
			loadProjEmp(projId);
		}
	});
	
	$( function() {
	    $("#startDate").datepicker({
	    		dateFormat: 'dd-mm-yy',
	    		minDate: 0,
	           onSelect: function(date) {
	             $("#dueDate").datepicker('option', 'minDate', date);
	           }
	         });

	         $("#dueDate").datepicker({
	        	 dateFormat: 'dd-mm-yy'
	         });
	} );
	
	</script>
</body>
</html>