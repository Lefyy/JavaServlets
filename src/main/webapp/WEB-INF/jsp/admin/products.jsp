<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Products"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3><i class="fas fa-box-open"></i> Products</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Back to admin</a>

<div class="card p-3 mb-3">
    <form method="get" action="${pageContext.request.contextPath}/admin/products" class="row g-2 align-items-end">
        <div class="col-md-5">
            <label class="form-label mb-1">Search by ID or name</label>
            <input class="form-control" name="q" value="${q}" maxlength="120" placeholder="e.g. 10 or monitor">
        </div>
        <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Search</button></div>
        <div class="col-md-2"><a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/admin/products">Reset</a></div>
    </form>
</div>

<div class="card p-3 mb-3">
    <h5 class="mb-3">Create product</h5>
    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
        <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="create">
        <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
        <div class="col-md-2"><input class="form-control" name="name" maxlength="160" required></div>
        <div class="col-md-2"><input class="form-control" type="number" name="price" min="0" step="0.01" required></div>
        <div class="col-md-2"><input class="form-control" type="number" name="quantity" min="0" step="1" required></div>
        <div class="col-md-2"><input class="form-control" type="number" name="categoryId" min="1" step="1" required></div>
        <div class="col-md-3"><input class="form-control" name="imageUrl" maxlength="500"></div>
        <div class="col-md-1"><button class="btn btn-primary">Create</button></div>
    </form>
</div>
<div class="table-responsive">
    <table class="table table-sm table-hover align-middle">
        <thead><tr><th>ID</th><th>Name</th><th>Price</th><th>Qty</th><th>Category</th><th>Image URL</th><th>Update</th><th>Delete</th></tr></thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${categoryNames[product.categoryId]} <small class="text-muted">(ID: ${product.categoryId})</small></td>
                <td class="text-truncate" style="max-width:180px;">${product.imageUrl}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" type="button" data-bs-toggle="collapse" data-bs-target="#edit-product-${product.id}" aria-expanded="false" aria-controls="edit-product-${product.id}">Edit</button>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin">
                        <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${product.id}">
                        <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
                        <button class="btn btn-sm btn-outline-danger">Delete</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="8" class="py-0 border-0">
                    <div class="collapse mt-2" id="edit-product-${product.id}">
                        <div class="card card-body">
                            <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
                                <input type="hidden" name="entity" value="product"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${product.id}">
                                <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
                                <div class="col-md-2"><input class="form-control form-control-sm" name="name" value="${product.name}" maxlength="160" required></div>
                                <div class="col-md-2"><input class="form-control form-control-sm" type="number" name="price" min="0" step="0.01" value="${product.price}" required></div>
                                <div class="col-md-2"><input class="form-control form-control-sm" type="number" name="quantity" min="0" step="1" value="${product.quantity}" required></div>
                                <div class="col-md-2"><input class="form-control form-control-sm" type="number" name="categoryId" min="1" step="1" value="${product.categoryId}" required></div>
                                <div class="col-md-3"><input class="form-control form-control-sm" name="imageUrl" maxlength="500" value="${product.imageUrl}"></div>
                                <div class="col-md-1"><button class="btn btn-sm btn-outline-primary">Save</button></div>
                            </form>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${totalPages > 1}">
    <nav aria-label="Product pages">
        <ul class="pagination justify-content-center mt-3">
            <li class="page-item ${hasPrevious ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/products?page=${currentPage - 1}&q=${q}">&lt;</a></li>
            <c:forEach begin="1" end="${totalPages}" var="i"><li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/admin/products?page=${i}&q=${q}">${i}</a></li></c:forEach>
            <li class="page-item ${hasNext ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/products?page=${currentPage + 1}&q=${q}">&gt;</a></li>
        </ul>
    </nav>
</c:if>

<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
