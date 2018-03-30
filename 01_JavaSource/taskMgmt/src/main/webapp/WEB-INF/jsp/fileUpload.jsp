<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
        <form:form action="doUpload.do" name ="uploadFile" modelAttribute="mFileModel" method="post" enctype="multipart/form-data">
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
    
    
  <%--   <table border="1" width="80%">
    <tr>
    <th>SL#</th>
    <th>Name</th>
    <th>Type</th>
    <th>Delete</th>
    </tr>
    
    <tr>
    <td>${fileModel.id}</td>
    <td><a href="downloadFile.do?fileId=${fileModel.id}"> ${fileModel.fileName}</a></td>
    <td>${fileModel.contentType}</td>
    </tr>
    </table> --%>
    
</body>
</html>