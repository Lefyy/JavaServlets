<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html><body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Products</h2>
<form method="post" action="${pageContext.request.contextPath}/admin">
    <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="create">
    <input name="name" placeholder="name" required>
    <input name="price" placeholder="price" required>
    <input name="quantity" placeholder="qty" required>
    <input name="categoryId" placeholder="category id" required>
    <input name="imageUrl" placeholder="image url">
    <button>Create</button>
</form>
<c:forEach items="${products}" var="product">
    <div>#${product.id} ${product.name} ${product.price} qty=${product.quantity}
        <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
            <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="${product.id}"><button>Delete</button>
        </form>
    </div>
</c:forEach>
</body></html>
