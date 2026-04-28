<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Статистика"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3><i class="fas fa-chart-pie"></i> Статистика</h3>
    <a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary">&larr; Назад к админ-панели</a>
</div>
<div class="row g-3">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Всего заказов</h5>
                <p class="h4">${stats.totalOrders}</p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Суммарная выручка</h5>
                <p class="h4">${stats.totalRevenue} ₽</p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Топ продукт</h5>
                <p class="h5">${stats.topProduct}</p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Топ покупатель</h5>
                <p class="h5">${stats.topCustomer}</p>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
