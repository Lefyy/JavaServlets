<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Админ-панель</h2>
<nav>
    <a href="${pageContext.request.contextPath}/admin/customers">Customers</a> |
    <a href="${pageContext.request.contextPath}/admin/products">Products</a> |
    <a href="${pageContext.request.contextPath}/admin/orders">Orders</a> |
    <a href="${pageContext.request.contextPath}/admin/categories">Categories</a> |
    <a href="${pageContext.request.contextPath}/admin/order-statuses">Statuses</a> |
    <a href="${pageContext.request.contextPath}/admin/statistics">Statistics</a>
</nav>
<p>Total orders: ${stats.totalOrders}</p>
<p>Total revenue: ${stats.totalRevenue}</p>
</body>
</html>
