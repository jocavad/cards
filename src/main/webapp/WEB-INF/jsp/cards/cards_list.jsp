<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="crd.CardsTitle"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <jsp:include page="/header"/>
<script src="<c:url value="/js/jquery-3.4.1.js" />"></script>
<div id="tab">
             <c:if test="${crdPage>1}">
             <input id="e5" name="p" value="<s:message code="BankApprovalTitle"/>" type="button" onclick="location.href='<c:url value="list"><c:param name="crdPage" value="${crdPage-1}"/></c:url>'"/>
             </c:if>
             <c:if test="${crdLastPage==0}">
             <input id="e4" name="n" value="<s:message code="BankApprovalTitle"/>" type="button" onclick="location.href='<c:url value="list"><c:param name="crdPage" value="${crdPage+1}"/></c:url>'"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="crd.CardId"/></th>
                    <th><s:message code="crd.BankApprovalId"/></th>
                    <th><s:message code="crd.Pin"/></th>
                    <th><s:message code="crd.IssueDate"/></th>
                    <th><s:message code="Edit"/></th>
              </tr>
            <c:forEach items="${crdList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.cardId}"/></td>
                    <td><c:out value="${i.bankApproval.approvalId}"/></td>
                    <td><c:out value="${i.pin}"/></td>
                    <td><c:out value="${i.issueDate}"/></td>
                    <td><input id="e1" value="<s:message code="Edit"/>" type="button" onclick="location.href='record/${i.cardId}'"/></td>
                </tr>
            </c:forEach>
          </table>
        <input id="e4" name="bck" value="<s:message code="Back"/>" type="button" onclick="location.href='../'"/>
        <input id="e3" name="crt" value="<s:message code="Create"/>" type="button" onclick="location.href='record/new'"/>
        </div> 
    </body>
</html>
