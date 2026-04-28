<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Корзина</h2>
<c:forEach items="${cartItems}" var="item">
    <div style="margin-bottom: 8px;">
        <strong>${item.product.name}</strong> x
        <form method="post" action="${pageContext.request.contextPath}/cart/update" style="display:inline;">
            <input type="hidden" name="productId" value="${item.product.id}">
            <input type="number" min="0" name="quantity" value="${item.qty}">
            <button type="submit">Обновить</button>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/cart/remove" style="display:inline;">
            <input type="hidden" name="productId" value="${item.product.id}">
            <button type="submit">Удалить</button>
        </form>
        = ${item.lineTotal}
    </div>
</c:forEach>
<p><strong>Итого: ${cartTotal}</strong></p>
<a href="${pageContext.request.contextPath}/checkout">Оформить заказ</a>
</body>
</html>
