<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<script>
    $(function() {
    $('#reqDialog').dialog({
      modal:true,
      autoOpen: false,
      width: 600,
      maxWidth: 600,
      close:{
          function(){
             $(this).remove();
          }
      }
    });
 

    $('#reqPopup').click(function() {
     $("#reqDialog").dialog("open");
    });
  });
  
  function reqFunction(v) {
     $('#requests').val(v);
     $("#reqDialog").dialog("close");
  }
</script>

<div id="reqDialog" title="<s:message code="req.RequestsTitle"/>">
             <c:if test="${reqPage>1}">
             <input id="e5" name="p" value="<s:message code="Previous"/>" type="button" onclick="$('#reqDialog').load('<c:url value="/requests/list" /> #reqDialog',{reqPage:'${reqPage-1}', ltype:'${ltype}'} );"/>
             </c:if>
             <c:if test="${reqLastPage==0}">
             <input id="e4" name="n" value="<s:message code="Next"/>" type="button" onclick="$('#reqDialog').load('<c:url value="/requests/list" /> #reqDialog',{reqPage:'${reqPage+1}', ltype:'${ltype}'} );"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="req.RequestId"/></th>
                    <th><s:message code="req.ClientId"/></th>
                    <th><s:message code="req.EmployeeId"/></th>
                    <th><s:message code="req.AccountNumber"/></th>
                    <th><s:message code="req.RequestDate"/></th>
                    <th></th>
              </tr>
            <c:forEach items="${reqList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.requestId}"/></td>
                    <td><c:out value="${i.clients.clientId}"/></td>
                    <td><c:out value="${i.employees.employeeId}"/></td>
                    <td><c:out value="${i.accountNumber}"/></td>
                    <td><c:out value="${i.requestDate}"/></td>
                    <td><input id="e1" value="<s:message code="Select"/>" type="button" onClick="javascript:reqFunction(${i.requestId})"/></td>
                </tr>
            </c:forEach>
          </table>
</div>