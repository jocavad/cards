<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<script>
    $(function() {
    $('#baDialog').dialog({
      modal:true,
      autoOpen: false,
      width: 400,
      maxWidth: 400,
      close:{
          function(){
             $(this).remove();
          }
      }
    });
 

    $('#baPopup').click(function() {
     $("#baDialog").dialog("open");
    });
  });
  
  function baFunction(v) {
     $('#bankApproval').val(v);
     $("#baDialog").dialog("close");
  }
</script>

<div id="baDialog" title="<s:message code="ba.BankApprovalTitle"/>">
             <c:if test="${baPage>1}">
             <input id="e5" name="p" value="<s:message code="Previous"/>" type="button" onclick="$('#baDialog').load('<c:url value="/bankApproval/list" /> #baDialog',{baPage:'${baPage-1}', ltype:'${ltype}'} );"/>
             </c:if>
             <c:if test="${baLastPage==0}">
             <input id="e4" name="n" value="<s:message code="Next"/>" type="button" onclick="$('#baDialog').load('<c:url value="/bankApproval/list" /> #baDialog',{baPage:'${baPage+1}', ltype:'${ltype}'} );"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="ba.ApprovalId"/></th>
                    <th><s:message code="ba.RequestId"/></th>
                    <th><s:message code="ba.ApprovalDate"/></th>
                    <th></th>
              </tr>
            <c:forEach items="${baList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.approvalId}"/></td>
                    <td><c:out value="${i.requests.requestId}"/></td>
                    <td><c:out value="${i.approvalDate}"/></td>
                    <td><input id="e1" value="<s:message code="Select"/>" type="button" onClick="javascript:baFunction(${i.approvalId})"/></td>
                </tr>
            </c:forEach>
          </table>
</div>