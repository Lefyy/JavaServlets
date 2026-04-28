<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>${product.name}</h2>
<p>Цена: ${product.price}</p>
<p>Остаток: ${product.quantity}</p>
<p>Категория ID: ${product.categoryId}</p>
<form method="post" action="${pageContext.request.contextPath}/cart/add">
    <input type="hidden" name="productId" value="${product.id}">
    <input type="number" name="quantity" min="1" value="1">
    <button type="submit">В корзину</button>
</form>
</body>
</html>
