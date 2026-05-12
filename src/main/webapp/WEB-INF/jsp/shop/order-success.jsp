<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Р—Р°РєР°Р· РїСЂРёРЅСЏС‚"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-8">
        <h3>РЎРїР°СЃРёР±Рѕ, Р·Р°РєР°Р· #${order.id} РїСЂРёРЅСЏС‚</h3>
        <p>РЎС‚Р°С‚СѓСЃ: ${statusNames[order.statusId]} <small class="text-muted">(ID: ${order.statusId})</small></p>
        <p>Р”Р°С‚Р°: <fmt:formatDate value="${orderCreatedAtDate}" pattern="yyyy-MM-dd HH:mm"/></p>
        <h5 class="mt-4">РЎРѕСЃС‚Р°РІ Р·Р°РєР°Р·Р°</h5>
        <table class="table table-sm">
            <thead>
            <tr>
                <th>РўРѕРІР°СЂ</th>
                <th>Р¦РµРЅР°</th>
                <th>РљРѕР»-РІРѕ</th>
                <th>РЎСѓРјРјР°</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td>${productNames[item.productId]} <small class="text-muted">(ID: ${item.productId})</small></td>
                    <td>${item.priceAtPurchase}</td>
                    <td>${item.quantity}</td>
                    <td>${item.priceAtPurchase * item.quantity}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/products">Р’РµСЂРЅСѓС‚СЊСЃСЏ РІ РјР°РіР°Р·РёРЅ</a>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
