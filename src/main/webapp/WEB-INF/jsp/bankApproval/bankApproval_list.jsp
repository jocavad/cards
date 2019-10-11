<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="ba.BankApprovalTitle"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <jsp:include page="/header"/>
<script src="<c:url value="/js/jquery-3.4.1.js" />"></script>
<div id="tab">
             <c:if test="${baPage>1}">
             <input id="e5" name="p" value="<s:message code="Previous"/>" type="button" onclick="location.href='<c:url value="list"><c:param name="baPage" value="${baPage-1}"/></c:url>'"/>
             </c:if>
             <c:if test="${baLastPage==0}">
             <input id="e4" name="n" value="<s:message code="Next"/>" type="button" onclick="location.href='<c:url value="list"><c:param name="baPage" value="${baPage+1}"/></c:url>'"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="ba.ApprovalId"/></th>
                    <th><s:message code="ba.RequestId"/></th>
                    <th><s:message code="ba.ApprovalDate"/></th>
                    <th><s:message code="Edit"/></th>
              </tr>
              <c:forEach items="${baList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.approvalId}"/></td>
                    <td><c:out value="${i.requests.requestId}"/></td>
                    <td><c:out value="${i.approvalDate}"/></td>
                    <td><input id="e1" value="<s:message code="Edit"/>" type="button" onclick="location.href='record/${i.approvalId}'"/></td>
                </tr>
            </c:forEach>
          </table>
        <input id="e4" name="bck" value="<s:message code="Back"/>" type="button" onclick="location.href='../'"/>
        <input id="e3" name="crt" value="<s:message code="Create"/>" type="button" onclick="location.href='record/new'"/>
        </div> 
    </body>
</html>
