<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:message code="req.RequestsRecord"/></title>
    </head>
  <link rel="stylesheet" href="<c:url value="/js/jquery-1.12.1-ui.css" />">
  <script src="<c:url value="/js/jquery-3.4.1.js" />"></script>
  <script src="<c:url value="/js/jquery-1.12.1-ui.js" />"></script>
  <script>
  $( function() {
    $( "#requestDate" ).datepicker({dateFormat: 'dd/mm/yy'});
  } );
  </script>
    <body>
        <f:form id="f1" action="modify" method="post" modelAttribute="ReqRec">
            <f:hidden path="requestId" size="5"/> <f:errors path="requestId"/><br>
            <table border="0">
            <tr><td><s:message code="req.ClientId"/></td><td><f:input path="clients" size="50"/></td><td><img id="cliPopup" src="<c:url value="/img/magnifier.png"/>" width="15" height="15" style="cursor: pointer;" /></td><td><f:errors path="clients"/></td></tr>
            <tr><td><s:message code="req.EmployeeId"/></td><td><f:input path="employees" size="50"/></td><td><img id="empPopup" src="<c:url value="/img/magnifier.png"/>" width="15" height="15" style="cursor: pointer;" /></td><td><f:errors path="employees"/></td></tr>
            <tr><td><s:message code="req.AccountNumber"/></td><td><f:input path="accountNumber" size="50"/></td><td><f:errors path="accountNumber"/></td></tr>
            <tr><td><s:message code="req.RequestDate"/> (dd/MM/yyyy)</td><td><f:input path="requestDate" size="50"/></td><td><f:errors path="requestDate"/></td></tr>
            </table>
            <c:set var="id" value="${ReqRec.requestId}"/>
            <c:if test="${empty id}">
            <input id="b1" type="submit" value="<s:message code="Create"/>" name="crt">
            </c:if>
            <c:if test="${not empty id}">
            <input id="b2" type="submit" value="<s:message code="Modify"/>" name="mod">
            <input id="b3" type="submit" value="<s:message code="Delete"/>" name="del"/>
            </c:if>
        </f:form>
            <input id="b4" type="submit" value="<s:message code="Back"/>" name="bck" onclick="location.href='<c:url value="../../requests" />'"/>

        <jsp:include page="/employees/list">
            <jsp:param name="ltype" value="0"/>
        </jsp:include>
        <jsp:include page="/clients/list">
            <jsp:param name="ltype" value="0"/>
        </jsp:include>
    </body>
</html>