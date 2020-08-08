<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Main Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <jsp:include page="/webapp/header"/>
            <input id="e1" value="<s:message code="cli.ClientsTitle"/>" type="button" onclick="location.href='<c:url value="/webapp/clients"/>'"/>
            <input id="e2" value="<s:message code="req.RequestsTitle"/>" type="button" onclick="location.href='<c:url value="/webapp/requests"/>'"/>
            <input id="e3" value="<s:message code="emp.EmployeesTitle"/>" type="button" onclick="location.href='<c:url value="/webapp/employees"/>'"/>
            <input id="e4" value="<s:message code="ba.BankApprovalTitle"/>" type="button" onclick="location.href='<c:url value="/webapp/bankApproval"/>'"/>
            <input id="e5" value="<s:message code="crd.CardsTitle"/>" type="button" onclick="location.href='<c:url value="/webapp/cards"/>'"/>
    </body>
</html>