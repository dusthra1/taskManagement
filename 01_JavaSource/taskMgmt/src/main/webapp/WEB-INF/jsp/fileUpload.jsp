<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<sec:csrfMetaTags/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Spring MVC - Hibernate File Upload to Database</title>

 <link type="text/css" rel="stylesheet" href="javascript/jsgrid/jsgrid.min.css" />
 <link type="text/css" rel="stylesheet" href="javascript/jsgrid/jsgrid-theme.min.css" />
	<style>
        .error {
            color: red;
        }
    </style>
    
</head>
<body>
    <div align="left">
        <h1>File Upload to Database</h1>
        <c:out value="${message}"/> <br/>
        <form:form action="fileUpload.do" name ="uploadFile" modelAttribute="mFileModel" method="post" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>Select your file:</td>
                    <td><form:input type="file" path="file" id="file"/><br>
                     <form:errors path="file" cssClass="error"/>
                  </td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Upload" /></td>
                </tr>
            </table>
       </form:form>
    </div>
    
    <div id="jsGrid"></div>

 	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="javascript/jsgrid/jsgrid.min.js"></script>
  	<script type="text/javascript" src="javascript/viewFiles.js"></script>

    
</body>
</html>