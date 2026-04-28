<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Каталог</h2>
<form method="get" action="${pageContext.request.contextPath}/products">
    <input name="search" placeholder="Поиск по названию">
    <select name="categoryId">
        <option value="">Все категории</option>
        <c:forEach items="${categories}" var="category">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select>
    <button type="submit">Фильтр</button>
</form>

<c:forEach items="${products}" var="product">
    <div style="border: 1px solid #ddd; margin: 8px 0; padding: 8px;">
        <h3><a href="${pageContext.request.contextPath}/products/${product.id}">${product.name}</a></h3>
        <p>Цена: ${product.price}, Остаток: ${product.quantity}</p>
        <form method="post" action="${pageContext.request.contextPath}/cart/add">
            <input type="hidden" name="productId" value="${product.id}">
            <input type="number" name="quantity" min="1" value="1">
            <button type="submit">В корзину</button>
        </form>
    </div>
</c:forEach>
</body>
</html>
