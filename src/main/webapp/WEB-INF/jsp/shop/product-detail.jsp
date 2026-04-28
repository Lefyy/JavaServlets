<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${product.name}"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h2 class="mb-3">${product.name}</h2>
                <p class="mb-1">Цена: <strong>${product.price} ₽</strong></p>
                <p class="mb-1">В наличии: ${product.quantity}</p>
                <p class="text-muted">Категория: ${categoryName} <small>(ID: ${product.categoryId})</small></p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h5>Добавить в корзину</h5>
                <form method="post" action="${pageContext.request.contextPath}/cart/add">
                    <input type="hidden" name="productId" value="${product.id}">
                    <div class="mb-3">
                        <label class="form-label">Количество</label>
                        <input class="form-control" type="number" name="quantity" min="1" value="1">
                    </div>
                    <button class="btn btn-primary">Добавить</button>
                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/products">Назад к каталогу</a>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
