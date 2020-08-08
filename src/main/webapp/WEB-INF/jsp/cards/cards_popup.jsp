<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<script>
    $(function() {
    $('#crdDialog').dialog({
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
 

    $('#crdPopup').click(function() {
     $("#crdDialog").dialog("open");
    });
  });
  
  function crdFunction(v) {
     $('#cards').val(v);
     $("#crdDialog").dialog("close");
  }
</script>

<div id="crdDialog" title="<s:message code="crd.CardsTitle"/>">
             <c:if test="${crdPage>1}">
             <input id="e5" name="p" value="<s:message code="Previous"/>" type="button" onclick="$('#crdDialog').load('<c:url value="/webapp/cards/list" /> #crdDialog',{crdPage:'${crdPage-1}', ltype:'${ltype}'} );"/>
             </c:if>
             <c:if test="${crdLastPage==0}">
             <input id="e4" name="n" value="<s:message code="Next"/>" type="button" onclick="$('#crdDialog').load('<c:url value="/webapp/cards/list" /> #crdDialog',{crdPage:'${crdPage+1}', ltype:'${ltype}'} );"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="crd.CardId"/></th>
                    <th><s:message code="crd.RequestId"/></th>
                    <th><s:message code="crd.Pin"/></th>
                    <th><s:message code="crd.IssueDate"/></th>
                    <th></th>
              </tr>
            <c:forEach items="${crdList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.cardId}"/></td>
                    <td><c:out value="${i.requests.requestId}"/></td>
                    <td><c:out value="${i.pin}"/></td>
                    <fmt:parseDate value="${i.issueDate}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" var="fmtDate" />
                    <td><c:out value="${fmtDate}"/></td>
                    <td><input id="e1" value="<s:message code="Select"/>" type="button" onClick="javascript:crdFunction(${i.cardId})"/></td>
                </tr>
            </c:forEach>
          </table>
</div>