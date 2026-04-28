<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Профиль</h2>
<form method="post" action="${pageContext.request.contextPath}/profile">
    <label>Имя <input name="name" value="${currentCustomer.name}"></label><br>
    <label>Email <input name="email" value="${currentCustomer.email}"></label><br>
    <label>Новый пароль <input name="password" type="password"></label><br>
    <button type="submit">Сохранить</button>
</form>

<h3>Мои заказы</h3>
<c:forEach items="${orders}" var="order">
    <div style="margin: 8px 0; border: 1px solid #ddd; padding: 8px;">
        <div>Заказ #${order.id}, статус ${order.statusId}, дата ${order.createdAt}</div>
        <a href="${pageContext.request.contextPath}/orders/success?id=${order.id}">Открыть</a>
    </div>
</c:forEach>
</body>
</html>
