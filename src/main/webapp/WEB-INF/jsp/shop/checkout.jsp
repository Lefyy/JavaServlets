<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Оформление заказа</h2>
<p>Проверьте товары и подтвердите оформление.</p>
<ul>
    <c:forEach items="${cartItems}" var="item">
        <li>${item.product.name} x ${item.qty} = ${item.lineTotal}</li>
    </c:forEach>
</ul>
<p><strong>Итого: ${cartTotal}</strong></p>
<form method="post" action="${pageContext.request.contextPath}/checkout">
    <button type="submit">Подтвердить заказ</button>
</form>
</body>
</html>
