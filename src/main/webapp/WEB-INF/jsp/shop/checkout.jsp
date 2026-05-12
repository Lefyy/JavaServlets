<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="РћС„РѕСЂРјР»РµРЅРёРµ Р·Р°РєР°Р·Р°"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3>РћС„РѕСЂРјР»РµРЅРёРµ Р·Р°РєР°Р·Р°</h3>
<p class="text-muted">РџСЂРѕРІРµСЂСЊС‚Рµ СЃРѕСЃС‚Р°РІ Р·Р°РєР°Р·Р° Рё РїРѕРґС‚РІРµСЂРґРёС‚Рµ РѕРїР»Р°С‚Сѓ.</p>
<div class="card mb-3">
    <div class="card-body">
        <ul class="list-group list-group-flush">
            <c:forEach items="${cartItems}" var="item">
                <li class="list-group-item d-flex justify-content-between">
                    <span>${item.product.name} x ${item.qty}</span>
                    <strong>${item.lineTotal} в‚Ѕ</strong>
                </li>
            </c:forEach>
        </ul>
        <div class="d-flex justify-content-between mt-3">
            <h5 class="mb-0">РС‚РѕРіРѕ:</h5>
            <h5 class="mb-0">${cartTotal} в‚Ѕ</h5>
        </div>
    </div>
</div>
<form method="post" action="${pageContext.request.contextPath}/checkout">
    <button class="btn btn-success" type="submit">РџРѕРґС‚РІРµСЂРґРёС‚СЊ Р·Р°РєР°Р·</button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/cart">Р’РµСЂРЅСѓС‚СЊСЃСЏ РІ РєРѕСЂР·РёРЅСѓ</a>
</form>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
