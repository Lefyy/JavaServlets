<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Заказ успешно оформлен</h2>
<p>Номер заказа: #${order.id}</p>
<ul>
    <c:forEach items="${items}" var="item">
        <li>Товар ID ${item.productId}, количество ${item.quantity}, цена ${item.priceAtPurchase}</li>
    </c:forEach>
</ul>
</body>
</html>
