<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Покупатели"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3><i class="fas fa-users"></i> Покупатели</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>

<div class="card p-3 mb-3">
    <form method="get" action="${pageContext.request.contextPath}/admin/customers" class="row g-2 align-items-end">
        <div class="col-md-5">
            <label class="form-label mb-1">Поиск по ID или имени</label>
            <input class="form-control" name="q" value="${q}" maxlength="120" placeholder="Например: 42 или Иван">
        </div>
        <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Найти</button></div>
        <div class="col-md-2"><a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/admin/customers">Сброс</a></div>
    </form>
</div>

<div class="card p-3 mb-3">
    <h5 class="mb-3">Создать покупателя</h5>
    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
        <input type="hidden" name="entity" value="customer">
        <input type="hidden" name="action" value="create">
        <input type="hidden" name="q" value="${q}">
        <input type="hidden" name="page" value="${currentPage}">
        <div class="col-md-3"><input class="form-control" name="name" maxlength="120" placeholder="Имя" required></div>
        <div class="col-md-3"><input class="form-control" name="email" type="email" maxlength="120" placeholder="Email" required></div>
        <div class="col-md-3"><input class="form-control" name="password" type="password" minlength="4" maxlength="120" placeholder="Пароль" required></div>
        <div class="col-md-2 form-check ms-2 mt-2"><input class="form-check-input" type="checkbox" name="isStaff" value="true" id="isStaffCreate"><label class="form-check-label" for="isStaffCreate">Администратор</label></div>
        <div class="col-md-12"><button class="btn btn-primary">Создать</button></div>
    </form>
</div>

<div class="table-responsive">
    <table class="table table-sm table-hover align-middle">
        <thead><tr><th>ID</th><th>Имя</th><th>Email</th><th>Роль</th><th>Обновить</th><th>Удалить</th></tr></thead>
        <tbody>
        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>${customer.id}</td><td>${customer.name}</td><td>${customer.email}</td>
                <td><c:choose><c:when test="${customer.staff}">Админ</c:when><c:otherwise>Пользователь</c:otherwise></c:choose></td>
                <td><button class="btn btn-sm btn-outline-primary" type="button" data-bs-toggle="collapse" data-bs-target="#edit-customer-${customer.id}">Редактировать</button></td>
                <td><form method="post" action="${pageContext.request.contextPath}/admin"><input type="hidden" name="entity" value="customer"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${customer.id}"><input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}"><button class="btn btn-sm btn-outline-danger">Удалить</button></form></td>
            </tr>
            <tr>
                <td colspan="6" class="py-0 border-0">
                    <div class="collapse mt-2" id="edit-customer-${customer.id}">
                        <div class="card card-body">
                            <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
                                <input type="hidden" name="entity" value="customer"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${customer.id}"><input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
                                <div class="col-md-3"><input class="form-control form-control-sm" name="name" value="${customer.name}" maxlength="120" required></div>
                                <div class="col-md-3"><input class="form-control form-control-sm" name="email" type="email" value="${customer.email}" maxlength="120" required></div>
                                <div class="col-md-3"><input class="form-control form-control-sm" name="password" type="password" minlength="4" maxlength="120" placeholder="Новый пароль"></div>
                                <div class="col-md-2"><select class="form-select form-select-sm" name="isStaff"><option value="true" <c:if test="${customer.staff}">selected</c:if>>Админ</option><option value="false" <c:if test="${!customer.staff}">selected</c:if>>Пользователь</option></select></div>
                                <div class="col-md-1"><button class="btn btn-sm btn-outline-primary">Сохранить</button></div>
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
<nav aria-label="Страницы покупателей"><ul class="pagination justify-content-center mt-3">
<li class="page-item ${hasPrevious ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${currentPage - 1}&q=${q}">&lt;</a></li>
<c:forEach begin="${currentPage - 1 < 1 ? 1 : currentPage - 1}" end="${currentPage + 1 > totalPages ? totalPages : currentPage + 1}" var="i"><li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${i}&q=${q}">${i}</a></li></c:forEach>
<li class="page-item ${hasNext ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/customers?page=${currentPage + 1}&q=${q}">&gt;</a></li>
</ul></nav>
</c:if>

<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
