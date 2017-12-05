<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
    <h2>Click to activate account</h2>
 
    <form:form method="post" action="${flowExecutionUrl}">
        <input type="submit" name="_eventId_continue" value="continue" />
        <input type="submit" name="_eventId_cancel" value="cancel" />
    </form:form>
</body>
</html>