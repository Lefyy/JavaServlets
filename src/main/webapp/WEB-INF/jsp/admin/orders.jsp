<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html><body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Orders</h2>
<c:forEach items="${orders}" var="order">
    <div>#${order.id} customer=${order.customerId} status=${order.statusId}
        <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
            <input type="hidden" name="entity" value="order"><input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="${order.id}"><button>Delete</button>
        </form>
    </div>
</c:forEach>
</body></html>
