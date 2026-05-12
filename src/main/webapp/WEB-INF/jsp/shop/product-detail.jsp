<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${product.name}"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h2 class="mb-3">${product.name}</h2>
                <p class="mb-1">Price: <strong>${product.price} ?</strong></p>
                <p class="mb-1">In stock: ${product.quantity}</p>
                <p class="text-muted">Category: ${categoryName} <small>(ID: ${product.categoryId})</small></p>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h5>Add to cart</h5>
                <form method="post" action="${pageContext.request.contextPath}/cart/add">
                    <input type="hidden" name="productId" value="${product.id}">
                    <div class="mb-3">
                        <label class="form-label">Quantity</label>
                        <input class="form-control" type="number" name="quantity" min="1" max="${product.quantity}" step="1" value="1" required>
                    </div>
                    <button class="btn btn-primary" <c:if test="${product.quantity == 0}">disabled</c:if>>Add</button>
                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/products">Back</a>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
