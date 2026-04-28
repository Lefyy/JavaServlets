<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html><body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Statistics</h2>
<p>Total orders: ${stats.totalOrders}</p>
<p>Total revenue: ${stats.totalRevenue}</p>
<p>Top product: ${stats.topProduct}</p>
<p>Top customer: ${stats.topCustomer}</p>
</body></html>
