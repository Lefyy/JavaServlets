<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html><body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Customers</h2>
<form method="post" action="${pageContext.request.contextPath}/admin">
    <input type="hidden" name="entity" value="customer">
    <input type="hidden" name="action" value="create">
    <input name="name" placeholder="name" required>
    <input name="email" placeholder="email" required>
    <input name="password" placeholder="password" required>
    <label>staff <input type="checkbox" name="isStaff" value="true"></label>
    <button>Create</button>
</form>
<c:forEach items="${customers}" var="customer">
    <div>#${customer.id} ${customer.name} ${customer.email} staff=${customer.staff}
        <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
            <input type="hidden" name="entity" value="customer"><input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="${customer.id}"><button>Delete</button>
        </form>
    </div>
</c:forEach>
</body></html>
