<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Оформление заказа"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3>Оформление заказа</h3>
<p class="text-muted">Проверьте состав заказа и подтвердите оплату.</p>
<div class="card mb-3">
    <div class="card-body">
        <ul class="list-group list-group-flush">
            <c:forEach items="${cartItems}" var="item">
                <li class="list-group-item d-flex justify-content-between">
                    <span>${item.product.name} x ${item.qty}</span>
                    <strong>${item.lineTotal} руб.</strong>
                </li>
            </c:forEach>
        </ul>
        <div class="d-flex justify-content-between mt-3">
            <h5 class="mb-0">Итого:</h5>
            <h5 class="mb-0">${cartTotal} руб.</h5>
        </div>
    </div>
</div>
<form method="post" action="${pageContext.request.contextPath}/checkout">
    <button class="btn btn-success" type="submit">Подтвердить заказ</button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/cart">Вернуться в корзину</a>
</form>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>