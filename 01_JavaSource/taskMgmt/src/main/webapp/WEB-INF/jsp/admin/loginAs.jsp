<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login User</title>
</head>
<body>

<form:form action="workas.do" class="LoginForm" commandName="loginform" method="POST">
<div id="genericError" class="errorMsg">
		<c:if test="${not empty errorMessage}">
		<fmt:message key="${errorMessage}" />
		</c:if>
		<c:if test="${not empty userNote  && 0!=userNote}">
			<fmt:message key="${userNote}" />
	    </c:if>
	</div>
<table width="70%" align="center">
		
		<tr>
			<td><label>User Name<span class="mandatory">*</span>
			</label>
			</td>
			<td><input type="text" name="userID" value="" id="userName" /><br />
			</td>
		</tr>
		
		<tr>
			<td colspan="2">
				<div class="btnLayer">
					<input type="submit" name="Go" value="Go">
				</div>
			</td>
		</tr>
</table>

</form:form>

</body>
</html>