<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Статистика"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3><i class="fas fa-chart-pie"></i> Статистика</h3>
    <a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary">&larr; Назад к админ-панели</a>
</div>
<form method="get" action="${pageContext.request.contextPath}/admin/statistics" class="card shadow-sm mb-4">
    <div class="card-body">
        <div class="row g-3 align-items-end">
            <div class="col-md-3">
                <label class="form-label" for="from">От</label>
                <input type="date" class="form-control" id="from" name="from" value="${fromDate}">
            </div>
            <div class="col-md-3">
                <label class="form-label" for="to">До</label>
                <input type="date" class="form-control" id="to" name="to" value="${toDate}">
            </div>
            <div class="col-md-3">
                <label class="form-label" for="period">Быстрый период</label>
                <select class="form-select" id="period" name="period">
                    <option value="">Выберите период</option>
                    <option value="day">За день</option>
                    <option value="week">За неделю</option>
                    <option value="month">За месяц</option>
                    <option value="year">За год</option>
                </select>
            </div>
            <div class="col-md-3 d-flex gap-2">
                <button type="submit" class="btn btn-primary">Применить</button>
                <a href="${pageContext.request.contextPath}/admin/statistics" class="btn btn-outline-secondary">Сбросить</a>
            </div>
        </div>
    </div>
</form>

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
                <h5 class="card-title">Топ 5 товаров</h5>
                <ol class="mb-0 ps-3">
                    <c:forEach items="${stats.topProducts}" var="product">
                        <li>${product.label} — ${product.value} шт.</li>
                    </c:forEach>
                    <c:if test="${empty stats.topProducts}">
                        <li>Нет данных</li>
                    </c:if>
                </ol>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Топ 5 покупателей</h5>
                <ol class="mb-0 ps-3">
                    <c:forEach items="${stats.topCustomers}" var="customer">
                        <li>${customer.label} — ${customer.value} заказ(ов)</li>
                    </c:forEach>
                    <c:if test="${empty stats.topCustomers}">
                        <li>Нет данных</li>
                    </c:if>
                </ol>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
