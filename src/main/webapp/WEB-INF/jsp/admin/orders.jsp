<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Заказы"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3><i class="fas fa-shopping-cart"></i> Заказы</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>

<div class="card p-3 mb-3">
    <form method="get" action="${pageContext.request.contextPath}/admin/orders" class="row g-2 align-items-end">
        <div class="col-md-4">
            <label class="form-label mb-1">Поиск по ID заказа</label>
            <input class="form-control" name="q" value="${q}" maxlength="120" placeholder="e.g. 501">
        </div>
        <div class="col-md-3">
            <label class="form-label mb-1">Сортировка по дате</label>
            <select class="form-select" name="sortDate">
                <option value="desc" <c:if test="${sortDate == 'desc'}">selected</c:if>>Сначала новые</option>
                <option value="asc" <c:if test="${sortDate == 'asc'}">selected</c:if>>Сначала старые</option>
            </select>
        </div>
        <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Применить</button></div>
        <div class="col-md-2"><a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/admin/orders">Сброс</a></div>
    </form>
</div>

<div class="table-responsive">
    <table class="table table-sm table-hover align-middle">
        <thead><tr><th>ID</th><th>Покупатель</th><th>Статус</th><th>Создан</th><th>Состав</th><th>Обновить</th><th>Удалить</th></tr></thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${customerNames[order.customerId]} <small class="text-muted">(ID: ${order.customerId})</small></td>
                <td>${statusNames[order.statusId]} <small class="text-muted">(ID: ${order.statusId})</small></td>
                <td><fmt:formatDate value="${orderСозданAtDates[order.id]}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><button class="btn btn-sm btn-outline-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#items-order-${order.id}" aria-expanded="false" aria-controls="items-order-${order.id}">Показать товары</button></td>
                <td><button class="btn btn-sm btn-outline-primary" type="button" data-bs-toggle="collapse" data-bs-target="#edit-order-${order.id}" aria-expanded="false" aria-controls="edit-order-${order.id}">Редактировать</button></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin">
                        <input type="hidden" name="entity" value="order"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${order.id}">
                        <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}"><input type="hidden" name="sortDate" value="${sortDate}">
                        <button class="btn btn-sm btn-outline-danger">Удалить</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="7" class="py-0 border-0">
                    <div class="collapse mt-2" id="items-order-${order.id}">
                        <div class="card card-body">
                            <c:choose>
                                <c:when test="${empty orderСоставByOrderId[order.id]}"><span class="text-muted">Позиции не найдены</span></c:when>
                                <c:otherwise>
                                    <ul class="mb-0 ps-3">
                                        <c:forEach items="${orderСоставByOrderId[order.id]}" var="item">
                                            <li>${productNames[item.productId]} - ${item.quantity} шт. - ${item.priceAtPurchase}</li>
                                        </c:forEach>
                                    </ul>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="7" class="py-0 border-0">
                    <div class="collapse mt-2" id="edit-order-${order.id}">
                        <div class="card card-body">
                            <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
                                <input type="hidden" name="entity" value="order"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${order.id}">
                                <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}"><input type="hidden" name="sortDate" value="${sortDate}">
                                <div class="col-md-4">
                                    <select class="form-select form-select-sm" name="statusId" required>
                                        <c:forEach items="${statuses}" var="status"><option value="${status.id}" <c:if test="${status.id == order.statusId}">selected</c:if>>${status.id}: ${status.name}</option></c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2"><button class="btn btn-sm btn-outline-primary">Сохранить</button></div>
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
    <nav aria-label="Страницы заказов">
        <ul class="pagination justify-content-center mt-3">
            <li class="page-item ${hasPrevious ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/orders?page=${currentPage - 1}&q=${q}&sortDate=${sortDate}">&lt;</a></li>
            <c:forEach begin="1" end="${totalPages}" var="i"><li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/admin/orders?page=${i}&q=${q}&sortDate=${sortDate}">${i}</a></li></c:forEach>
            <li class="page-item ${hasNext ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/orders?page=${currentPage + 1}&q=${q}&sortDate=${sortDate}">&gt;</a></li>
        </ul>
    </nav>
</c:if>

<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>

