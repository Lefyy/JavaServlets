<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Каталог"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-3">
        <h5>Категории</h5>
        <ul class="list-group">
            <li class="list-group-item ${empty currentCategory ? 'active' : ''}">
                <a href="${pageContext.request.contextPath}/products${empty currentSort ? '' : '?sort='.concat(currentSort)}">Все</a>
            </li>
            <c:forEach items="${categories}" var="category">
                <li class="list-group-item ${currentCategory == category.id.toString() ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/products?category=${category.id}${empty currentSort ? '' : '&sort='.concat(currentSort)}">${category.name}</a>
                </li>
            </c:forEach>
        </ul>
        <h5 class="mt-3">Сортировка</h5>
        <div class="d-flex justify-content-end mb-3">
            <a class="btn btn-sm btn-outline-secondary me-2"
               href="${pageContext.request.contextPath}/products?sort=price_asc${empty currentCategory ? '' : '&category='.concat(currentCategory)}">Цена ↑</a>
            <a class="btn btn-sm btn-outline-secondary me-2"
               href="${pageContext.request.contextPath}/products?sort=price_desc${empty currentCategory ? '' : '&category='.concat(currentCategory)}">Цена ↓</a>
            <a class="btn btn-sm btn-outline-secondary"
               href="${pageContext.request.contextPath}/products?sort=popularity${empty currentCategory ? '' : '&category='.concat(currentCategory)}">Популярные</a>
        </div>
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
        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:choose>
                        <c:when test="${hasPrevious}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/products?page=1${empty currentCategory ? '' : '&category='.concat(currentCategory)}${empty currentSort ? '' : '&sort='.concat(currentSort)}">&laquo;&laquo;</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/products?page=${currentPage - 1}${empty currentCategory ? '' : '&category='.concat(currentCategory)}${empty currentSort ? '' : '&sort='.concat(currentSort)}">&lt;</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled"><span class="page-link">&laquo;&laquo;</span></li>
                            <li class="page-item disabled"><span class="page-link">&lt;</span></li>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="${currentPage - 1 < 1 ? 1 : currentPage - 1}" end="${currentPage + 1 > totalPages ? totalPages : currentPage + 1}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <li class="page-item active"><span class="page-link">${i}</span></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link" href="${pageContext.request.contextPath}/products?page=${i}${empty currentCategory ? '' : '&category='.concat(currentCategory)}${empty currentSort ? '' : '&sort='.concat(currentSort)}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${hasNext}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/products?page=${currentPage + 1}${empty currentCategory ? '' : '&category='.concat(currentCategory)}${empty currentSort ? '' : '&sort='.concat(currentSort)}">&gt;</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/products?page=${totalPages}${empty currentCategory ? '' : '&category='.concat(currentCategory)}${empty currentSort ? '' : '&sort='.concat(currentSort)}">&raquo;&raquo;</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled"><span class="page-link">&gt;</span></li>
                            <li class="page-item disabled"><span class="page-link">&raquo;&raquo;</span></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </c:if>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
