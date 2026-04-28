<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Админ-панель"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3>Админ-панель</h3>
<p>Выберите раздел для управления:</p>
<div class="admin-button-grid">
    <a href="${pageContext.request.contextPath}/admin/customers" class="admin-button" style="background-color:#198754;"><i class="fas fa-users me-2"></i> Покупатели</a>
    <a href="${pageContext.request.contextPath}/admin/orders" class="admin-button" style="background-color:#0d6efd;"><i class="fas fa-shopping-cart me-2"></i> Заказы</a>
    <a href="${pageContext.request.contextPath}/admin/products" class="admin-button" style="background-color:#dc3545;"><i class="fas fa-box-open me-2"></i> Продукты</a>
    <a href="${pageContext.request.contextPath}/admin/statistics" class="admin-button" style="background-color:#20c997;"><i class="fas fa-chart-line me-2"></i> Статистика</a>
</div>
<div class="mt-4">
    <p><strong>Всего заказов:</strong> ${stats.totalOrders}</p>
    <p><strong>Общая выручка:</strong> ${stats.totalRevenue}</p>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
