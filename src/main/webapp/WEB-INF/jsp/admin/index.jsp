<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="РђРґРјРёРЅ-РїР°РЅРµР»СЊ"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3>РђРґРјРёРЅ-РїР°РЅРµР»СЊ</h3>
<p>Р’С‹Р±РµСЂРёС‚Рµ СЂР°Р·РґРµР» РґР»СЏ СѓРїСЂР°РІР»РµРЅРёСЏ:</p>
<div class="admin-button-grid">
    <a href="${pageContext.request.contextPath}/admin/customers" class="admin-button" style="background-color:#198754;"><i class="fas fa-users me-2"></i> РџРѕРєСѓРїР°С‚РµР»Рё</a>
    <a href="${pageContext.request.contextPath}/admin/orders" class="admin-button" style="background-color:#0d6efd;"><i class="fas fa-shopping-cart me-2"></i> Р—Р°РєР°Р·С‹</a>
    <a href="${pageContext.request.contextPath}/admin/products" class="admin-button" style="background-color:#dc3545;"><i class="fas fa-box-open me-2"></i> РџСЂРѕРґСѓРєС‚С‹</a>
    <a href="${pageContext.request.contextPath}/admin/statistics" class="admin-button" style="background-color:#20c997;"><i class="fas fa-chart-line me-2"></i> РЎС‚Р°С‚РёСЃС‚РёРєР°</a>
</div>
<div class="mt-4">
    <p><strong>Р’СЃРµРіРѕ Р·Р°РєР°Р·РѕРІ:</strong> ${stats.totalOrders}</p>
    <p><strong>РћР±С‰Р°СЏ РІС‹СЂСѓС‡РєР°:</strong> ${stats.totalRevenue}</p>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
