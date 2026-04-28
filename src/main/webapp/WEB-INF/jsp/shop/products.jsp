<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Каталог"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-3">
        <h5>Фильтры</h5>
        <form method="get" action="${pageContext.request.contextPath}/products" class="card p-3">
            <div class="mb-2">
                <input class="form-control" name="search" placeholder="Поиск по названию">
            </div>
            <div class="mb-2">
                <select class="form-select" name="categoryId">
                    <option value="">Все категории</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="btn btn-primary">Применить</button>
        </form>
    </div>
    <div class="col-md-9">
        <div class="row">
            <c:forEach items="${products}" var="product">
                <div class="col-md-4 mb-3">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><a href="${pageContext.request.contextPath}/products/${product.id}">${product.name}</a></h5>
                            <p class="card-text mb-1">${product.price} ₽</p>
                            <small class="text-muted">Остаток: ${product.quantity}</small>
                        </div>
                        <div class="card-footer bg-white">
                            <form method="post" action="${pageContext.request.contextPath}/cart/add" class="d-flex gap-2">
                                <input type="hidden" name="productId" value="${product.id}">
                                <input class="form-control" type="number" name="quantity" min="1" value="1">
                                <button class="btn btn-outline-primary">В корзину</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
