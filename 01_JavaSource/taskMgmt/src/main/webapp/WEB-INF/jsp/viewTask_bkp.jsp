<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<title>View Task</title>
<link rel="stylesheet" type="text/css" href="css/task.css">
</head>
<script type="text/javascript" src="javascript/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function() {
    $('#projId').change(function() {
    	
    	var val = this.value;  
    	$('#showContent').empty();
    	
    	if(val==0)
    	document.getElementById("showContent").innerHTML = $("#allProj").html();
    	
    	if(val>0)    	
    		document.getElementById("showContent").innerHTML = $("#proj_"+val).html();
    	
    	
    	
    });
});
</script>
<body>
	<h3 align="center">View Tasks</h3>
		
	<table>
			<tr>
			<th>Project: </th>
			<td>
				<select name="projId" id="projId">
				 	<option value="-1"> --SELECT--</option>
			 		<option value="0">ALL Projects</option>
					<c:forEach items="${allProjList}" var="proj">
						<option value="${proj.id}">${proj.name}</option>
					</c:forEach>				
				</select>
				</td>
			</tr>
	</table>

	
	<div id="showContent"></div>
	
	<a href="home.do" style="align:center">Back Home</a>
	
	<c:forEach items="${allProjList}" var="proj">	
	<div id="proj_${proj.id}" style="display: none">
		<table>
			<tr>
				<td><b>Project:</b> ${proj.name}</td>
			</tr>
			<tr><td></td></tr>
			<c:forEach items="${proj.tasksList}" var="task">
					<tr><td>Task: ${task.taskName}</td></tr>
					<tr><td>Task Description: ${task.description}</td></tr>
					<tr><td>Start Date: ${task.startDate}</td></tr>
					<tr><td>Due Date: ${task.dueDate}</td></tr>
					<tr><td>Employees </td></tr>
					<tr>
						<td>
							<table border="1">
								<tr><td>MID</td><td>Employee Name</td></tr>
								<c:forEach items="${task.employeesList}" var="emp">
									<tr><td>${emp.mid}</td><td>${emp.name }</td></tr>
								</c:forEach>					
							</table>
						</td>
					</tr>		
			</c:forEach>	
			<tr><td></td></tr>	
		</table>
	</div>	
	</c:forEach>
	
	<div id="allProj" style="display: none">	
	<c:forEach items="${allProjList}" var="proj">	
		<table width="40%">
			<tr>
				<td><b>Project:</b> ${proj.name}</td>
			</tr>
			<tr><td></td></tr>
			<c:forEach items="${proj.tasksList}" var="task">
					<tr><td>Task: ${task.taskName}</td></tr>
					<tr><td>Task Description: ${task.description}</td></tr>
					<tr><td>Start Date: ${task.startDate}</td></tr>
					<tr><td>Due Date: ${task.dueDate}</td></tr>
					<tr><td><b>Employees</b> </td></tr>
					<tr>
						<td>
							<table border="1">
								<tr><td>MID</td><td>Employee Name</td></tr>
								<c:forEach items="${task.employeesList}" var="emp">
									<tr><td>${emp.mid}</td><td>${emp.name }</td></tr>
								</c:forEach>					
							</table>
						</td>
					</tr>		
			</c:forEach>	
			<tr><td></td></tr>	
		</table>
	</c:forEach>	
	</div>		
	
	