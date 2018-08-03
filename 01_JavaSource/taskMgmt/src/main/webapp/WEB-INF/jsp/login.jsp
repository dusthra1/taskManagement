<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form:form action="validateLogin.do" class="LoginForm" commandName="loginform" method="POST">
	
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
			<td><label>Password<span class="mandatory">*</span></label></td>
			<td><input type="password" name="password" value=""
				id="password" />
			</td>
		</tr>
		<tr>
			<td><label>Language</label>
			</td>
			<td>
				<select name="locale" id="locale">
				 <option value="en">EN</option>
				  <option value="es">ES</option>
				   <option value="fr">FR</option>
									
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="btnLayer">
					<input type="submit" name="Login" value="Login">
				</div></td>
		</tr>
	</table>
</form:form>
