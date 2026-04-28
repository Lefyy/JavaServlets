<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Продукты"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3><i class="fas fa-box-open"></i> Продукты</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>
<div class="card p-3 mb-3">
    <h5 class="mb-3">Добавить товар</h5>
    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
        <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="create">
        <div class="col-md-2"><input class="form-control" name="name" placeholder="Название" required></div>
        <div class="col-md-2"><input class="form-control" name="price" placeholder="Цена" required></div>
        <div class="col-md-2"><input class="form-control" name="quantity" placeholder="Кол-во" required></div>
        <div class="col-md-2"><input class="form-control" name="categoryId" placeholder="Категория ID" required></div>
        <div class="col-md-3"><input class="form-control" name="imageUrl" placeholder="Image URL"></div>
        <div class="col-md-1"><button class="btn btn-primary">Создать</button></div>
    </form>
</div>
<div class="table-responsive">
    <table class="table table-sm table-hover align-middle">
        <thead><tr><th>ID</th><th>Название</th><th>Цена</th><th>Кол-во</th><th>Категория</th><th>Image URL</th><th>Обновить</th><th>Удалить</th></tr></thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.categoryId}</td>
                <td class="text-truncate" style="max-width:180px;">${product.imageUrl}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-1">
                        <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${product.id}">
                        <div class="col-md-2"><input class="form-control form-control-sm" name="name" value="${product.name}" required></div>
                        <div class="col-md-2"><input class="form-control form-control-sm" name="price" value="${product.price}" required></div>
                        <div class="col-md-2"><input class="form-control form-control-sm" name="quantity" value="${product.quantity}" required></div>
                        <div class="col-md-2"><input class="form-control form-control-sm" name="categoryId" value="${product.categoryId}" required></div>
                        <div class="col-md-3"><input class="form-control form-control-sm" name="imageUrl" value="${product.imageUrl}"></div>
                        <div class="col-md-1"><button class="btn btn-sm btn-outline-primary">OK</button></div>
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin">
                        <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${product.id}">
                        <button class="btn btn-sm btn-outline-danger">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
