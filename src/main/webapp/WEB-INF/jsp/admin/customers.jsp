<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Покупатели"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3><i class="fas fa-users"></i> Покупатели</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>
<div class="card p-3 mb-3">
    <h5 class="mb-3">Создать покупателя</h5>
    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
        <input type="hidden" name="entity" value="customer">
        <input type="hidden" name="action" value="create">
        <div class="col-md-3"><input class="form-control" name="name" placeholder="Имя" required></div>
        <div class="col-md-3"><input class="form-control" name="email" placeholder="Email" required></div>
        <div class="col-md-3"><input class="form-control" name="password" placeholder="Пароль" required></div>
        <div class="col-md-2 form-check ms-2 mt-2"><input class="form-check-input" type="checkbox" name="isStaff" value="true" id="isStaffCreate"><label class="form-check-label" for="isStaffCreate">Администратор</label></div>
        <div class="col-md-12"><button class="btn btn-primary">Создать</button></div>
    </form>
</div>
<div class="table-responsive">
    <table class="table table-sm table-hover align-middle">
        <thead><tr><th>ID</th><th>Имя</th><th>Email</th><th>Администратор</th><th>Обновить</th><th>Удалить</th></tr></thead>
        <tbody>
        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td><c:choose><c:when test="${customer.staff}">Админ</c:when><c:otherwise>Пользователь</c:otherwise></c:choose></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" type="button" data-bs-toggle="collapse" data-bs-target="#edit-customer-${customer.id}" aria-expanded="false" aria-controls="edit-customer-${customer.id}">
                        Редактировать
                    </button>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin">
                        <input type="hidden" name="entity" value="customer"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${customer.id}">
                        <button class="btn btn-sm btn-outline-danger">Удалить</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="6" class="py-0 border-0">
                    <div class="collapse mt-2" id="edit-customer-${customer.id}">
                        <div class="card card-body">
                            <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
                                <input type="hidden" name="entity" value="customer"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${customer.id}">
                                <div class="col-md-3"><input class="form-control form-control-sm" name="name" value="${customer.name}" required></div>
                                <div class="col-md-3"><input class="form-control form-control-sm" name="email" value="${customer.email}" required></div>
                                <div class="col-md-3"><input class="form-control form-control-sm" name="password" placeholder="Новый пароль"></div>
                                <div class="col-md-2">
                                    <select class="form-select form-select-sm" name="isStaff">
                                        <option value="true" <c:if test="${customer.staff}">selected</c:if>>Админ</option>
                                        <option value="false" <c:if test="${!customer.staff}">selected</c:if>>Пользователь</option>
                                    </select>
                                </div>
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
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
