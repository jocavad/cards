<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:message code="ba.BankApprovalRecord"/></title>
    </head>
  <link rel="stylesheet" href="<c:url value="/js/jquery-1.12.1-ui.css" />">
  <script src="<c:url value="/js/jquery-3.4.1.js" />"></script>
  <script src="<c:url value="/js/jquery-1.12.1-ui.js" />"></script>
  <script>
  $( function() {
    $( "#approvalDate" ).datepicker({dateFormat: 'dd/mm/yy'});
  } );
  </script>
    <body>
        <f:form id="f1" action="modify" method="post" modelAttribute="BaRec">
            <f:hidden path="approvalId" size="5"/> <f:errors path="approvalId"/><br>
            <table border="0">
            <tr><td><s:message code="ba.RequestId"/></td><td><f:input path="requests" size="50"/></td><td><img id="reqPopup" src="<c:url value="/img/magnifier.png"/>" width="15" height="15" style="cursor: pointer;" /></td><td><f:errors path="requests"/></td></tr>
            <tr><td><s:message code="ba.ApprovalDate"/> (dd/MM/yyyy)</td><td><f:input path="approvalDate" size="50"/></td><td><f:errors path="approvalDate"/></td></tr>
            </table>
            <c:set var="id" value="${BaRec.approvalId}"/>
            <c:if test="${empty id}">
            <input id="b1" type="submit" value="<s:message code="Create"/>" name="crt">
            </c:if>
            <c:if test="${not empty id}">
            <input id="b2" type="submit" value="<s:message code="Modify"/>" name="mod">
            <input id="b3" type="submit" value="<s:message code="Delete"/>" name="del"/>
            </c:if>
        </f:form>
            <input id="b4" type="submit" value="<s:message code="Back"/>" name="bck" onclick="location.href='<c:url value="../../bankApproval" />'"/>
        <jsp:include page="/requests/list">
            <jsp:param name="ltype" value="0"/>
        </jsp:include>
    </body>
</html>