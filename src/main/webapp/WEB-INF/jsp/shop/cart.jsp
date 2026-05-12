<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Корзина"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h2>Корзина</h2>
<div class="card">
    <div class="card-body">
        <c:forEach items="${cartItems}" var="item">
            <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                <div>
                    <strong>${item.product.name}</strong><br>
                    <small>${item.lineTotal} ?</small>
                </div>
                <div class="d-flex gap-2">
                    <form method="post" action="${pageContext.request.contextPath}/cart/update" class="d-flex gap-2">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <input class="form-control form-control-sm" style="width:80px;" type="number" min="0" max="${item.product.quantity}" step="1" name="quantity" value="${item.qty}" required>
                        <button class="btn btn-outline-secondary btn-sm" type="submit">Обновить</button>
                    </form>
                    <form method="post" action="${pageContext.request.contextPath}/cart/remove">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <button class="btn btn-outline-danger btn-sm" type="submit">Удалить</button>
                    </form>
                </div>
            </div>
        </c:forEach>
        <div class="mt-3 d-flex justify-content-between">
            <strong>Итого: ${cartTotal} ?</strong>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/checkout">Оформить заказ</a>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>

