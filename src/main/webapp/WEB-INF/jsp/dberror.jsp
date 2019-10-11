<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:message code="errorTitle"/></title>
    </head>
    <body>
        <h1><s:message code="errorHead"/></h1>
        <br/>
            <s:message code="errorMsg"/>
            <br/>
            <input id="b4" type="submit" value="<s:message code="Back"/>" name="bck" onclick="location.href='<c:url value="../../" />'"/>
    </body>
</html>
