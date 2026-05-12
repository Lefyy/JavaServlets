<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="РЎС‚Р°С‚РёСЃС‚РёРєР°"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3><i class="fas fa-chart-pie"></i> РЎС‚Р°С‚РёСЃС‚РёРєР°</h3>
    <a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary">&larr; РќР°Р·Р°Рґ Рє Р°РґРјРёРЅ-РїР°РЅРµР»Рё</a>
</div>
<form method="get" action="${pageContext.request.contextPath}/admin/statistics" class="card shadow-sm mb-4">
    <div class="card-body">
        <div class="row g-3 align-items-end">
            <div class="col-md-3">
                <label class="form-label" for="from">РћС‚</label>
                <input type="date" class="form-control" id="from" name="from" value="${fromDate}">
            </div>
            <div class="col-md-3">
                <label class="form-label" for="to">Р”Рѕ</label>
                <input type="date" class="form-control" id="to" name="to" value="${toDate}">
            </div>
            <div class="col-md-3">
                <label class="form-label" for="period">Р‘С‹СЃС‚СЂС‹Р№ РїРµСЂРёРѕРґ</label>
                <select class="form-select" id="period" name="period">
                    <option value="">Р’С‹Р±РµСЂРёС‚Рµ РїРµСЂРёРѕРґ</option>
                    <option value="day">Р—Р° РґРµРЅСЊ</option>
                    <option value="week">Р—Р° РЅРµРґРµР»СЋ</option>
                    <option value="month">Р—Р° РјРµСЃСЏС†</option>
                    <option value="year">Р—Р° РіРѕРґ</option>
                </select>
            </div>
            <div class="col-md-3 d-flex gap-2">
                <button type="submit" class="btn btn-primary">РџСЂРёРјРµРЅРёС‚СЊ</button>
                <a href="${pageContext.request.contextPath}/admin/statistics" class="btn btn-outline-secondary">РЎР±СЂРѕСЃРёС‚СЊ</a>
            </div>
        </div>
    </div>
</form>

<div class="row g-3">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Р’СЃРµРіРѕ Р·Р°РєР°Р·РѕРІ</h5>
                <p class="h4">${stats.totalOrders}</p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">РЎСѓРјРјР°СЂРЅР°СЏ РІС‹СЂСѓС‡РєР°</h5>
                <p class="h4">${stats.totalRevenue} в‚Ѕ</p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">РўРѕРї 5 С‚РѕРІР°СЂРѕРІ</h5>
                <ol class="mb-0 ps-3">
                    <c:forEach items="${stats.topProducts}" var="product">
                        <li>${product.label} вЂ” ${product.value} С€С‚.</li>
                    </c:forEach>
                    <c:if test="${empty stats.topProducts}">
                        <li>РќРµС‚ РґР°РЅРЅС‹С…</li>
                    </c:if>
                </ol>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">РўРѕРї 5 РїРѕРєСѓРїР°С‚РµР»РµР№</h5>
                <ol class="mb-0 ps-3">
                    <c:forEach items="${stats.topCustomers}" var="customer">
                        <li>${customer.label} вЂ” ${customer.value} Р·Р°РєР°Р·(РѕРІ)</li>
                    </c:forEach>
                    <c:if test="${empty stats.topCustomers}">
                        <li>РќРµС‚ РґР°РЅРЅС‹С…</li>
                    </c:if>
                </ol>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
