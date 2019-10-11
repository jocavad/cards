<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<script>
    $(function() {
    $('#empDialog').dialog({
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
 

    $('#empPopup').click(function() {
     $("#empDialog").dialog("open");
    });
  });
  
  function empFunction(v) {
     $('#employees').val(v);
     $("#empDialog").dialog("close");
  }
</script>

<div id="empDialog" title="<s:message code="emp.EmployeesTitle"/>">
             <c:if test="${empPage>1}">
             <input id="e5" name="p" value="<s:message code="Previous"/>" type="button" onclick="$('#empDialog').load('<c:url value="/employees/list" /> #empDialog',{empPage:'${empPage-1}', ltype:'${ltype}'} );"/>
            </c:if>
             <c:if test="${empLastPage==0}">
             <input id="e4" name="n" value="<s:message code="Next"/>" type="button" onclick="$('#empDialog').load('<c:url value="/employees/list" /> #empDialog',{empPage:'${empPage+1}', ltype:'${ltype}'} );"/>
             </c:if>
        <table border="1">
              <tr>
                    <th><s:message code="emp.EmployeeId"/></th>
                    <th><s:message code="emp.FirstName"/></th>
                    <th><s:message code="emp.LastName"/></th>
                    <th><s:message code="emp.Address"/></th>
                    <th><s:message code="emp.PhoneNumber"/></th>
                    <th></th>
              </tr>
            <c:forEach items="${empList}" var="i" end="4">
                <tr>
                    <td><c:out value="${i.employeeId}"/></td>
                    <td><c:out value="${i.firstName}"/></td>
                    <td><c:out value="${i.lastName}"/></td>
                    <td><c:out value="${i.address}"/></td>
                    <td><c:out value="${i.phoneNumber}"/></td>
                    <td><input id="e1" value="<s:message code="Select"/>" type="button" onClick="javascript:empFunction(${i.employeeId})"/></td>
                </tr>
            </c:forEach>
          </table>
</div>