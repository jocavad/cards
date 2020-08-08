<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<script>
    $(function() {
    $('#cliDialog').dialog({
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
 

    $('#cliPopup').click(function() {
     $("#cliDialog").dialog("open");
    });
  });
  
  function cliFunction(v) {
     $('#clients').val(v);
     $("#cliDialog").dialog("close");
  }
</script>

<div id="cliDialog" title="<s:message code="cli.ClientsTitle"/>">
             <c:if test="${cliPage>1}">
             <input id="e5" name="p" value="<s:message code="Previous"/>" type="button" onclick="$('#cliDialog').load('<c:url value="/webapp/clients/list" /> #cliDialog',{cliPage:'${cliPage-1}', ltype:'${ltype}'} );"/>
             </c:if>
             <c:if test="${cliLastPage==0}">
             <input id="e4" name="n" value="<s:message code="Next"/>" type="button" onclick="$('#cliDialog').load('<c:url value="/webapp/clients/list" /> #cliDialog',{cliPage:'${cliPage+1}', ltype:'${ltype}'} );"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="cli.ClientId"/></th>
                    <th><s:message code="cli.FirstName"/></th>
                    <th><s:message code="cli.LastName"/></th>
                    <th><s:message code="cli.Address"/></th>
                    <th><s:message code="cli.Email"/></th>
                    <th><s:message code="cli.PhoneNumber"/></th>
                    <th></th>
              </tr>
            <c:forEach items="${cliList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.clientId}"/></td>
                    <td><c:out value="${i.firstName}"/></td>
                    <td><c:out value="${i.lastName}"/></td>
                    <td><c:out value="${i.address}"/></td>
                    <td><c:out value="${i.email}"/></td>
                    <td><c:out value="${i.phoneNumber}"/></td>
                    <td><input id="e1" value="<s:message code="Select"/>" type="button" onClick="javascript:cliFunction(${i.clientId})"/></td>
                </tr>
            </c:forEach>
          </table>
</div>