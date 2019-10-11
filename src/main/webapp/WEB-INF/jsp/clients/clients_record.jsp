<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:message code="cli.ClientsRecord"/></title>
    </head>
    <body>
        <f:form id="f1" action="modify" method="post" modelAttribute="CliRec">
            <f:hidden path="clientId" size="5"/> <f:errors path="clientId"/><br>
            <table border="0">
            <tr><td><s:message code="cli.FirstName"/></td><td><f:input path="firstName" size="50"/></td><td><f:errors path="firstName"/></td></tr>
            <tr><td><s:message code="cli.LastName"/></td><td><f:input path="lastName" size="50"/></td><td><f:errors path="lastName"/></td></tr>
            <tr><td><s:message code="cli.Address"/></td><td><f:input path="address" size="50"/></td><td><f:errors path="address"/></td></tr>
            <tr><td><s:message code="cli.Email"/></td><td><f:input path="email" size="50"/></td><td><f:errors path="email"/></td></tr>
            <tr><td><s:message code="cli.PhoneNumber"/></td><td><f:input path="phoneNumber" size="50"/></td><td><f:errors path="phoneNumber"/></td></tr>
            </table>
            <c:set var="id" value="${CliRec.clientId}"/>
            <c:if test="${empty id}">
            <input id="b1" type="submit" value="<s:message code="Create"/>" name="crt">
            </c:if>
            <c:if test="${not empty id}">
            <input id="b2" type="submit" value="<s:message code="Modify"/>" name="mod">
            <input id="b3" type="submit" value="<s:message code="Delete"/>" name="del"/>
            </c:if>
        </f:form>
            <input id="b4" type="submit" value="<s:message code="Back"/>" name="bck" onclick="location.href='<c:url value="../../clients" />'"/>
    </body>
</html>