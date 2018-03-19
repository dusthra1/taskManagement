<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<title>Spring MVC - Hibernate File Upload to Database</title>
</head>
<body>
    <div align="center">
        <h1>File Upload to Database</h1>
        <c:out value="${message}"/> <br/>
        <form:form action="doUpload.do" name = "uploadFile" method="post" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>Pick file #1:</td>
                    <td><input type="file" name="fileUpload" size="50" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Upload" /></td>
                </tr>
            </table>
       </form:form>
    </div>
    
    <a href="downloadFile.do?fileId=103">Download</a>
</body>
</html>