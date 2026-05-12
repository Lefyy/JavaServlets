<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Категории"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3>Категории</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>

<div class="card p-3 mb-3">
    <form method="get" action="${pageContext.request.contextPath}/admin/categories" class="row g-2 align-items-end">
        <div class="col-md-5"><label class="form-label mb-1">Поиск по ID или названию</label><input class="form-control" name="q" value="${q}" maxlength="120"></div>
        <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Найти</button></div>
        <div class="col-md-2"><a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/admin/categories">Сброс</a></div>
    </form>
</div>

<div class="card p-3 mb-3">
    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
        <input type="hidden" name="entity" value="category"><input type="hidden" name="action" value="create">
        <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
        <div class="col-md-4"><input class="form-control" name="name" maxlength="120" required></div>
        <div class="col-md-2"><button class="btn btn-primary">Создать</button></div>
    </form>
</div>
<table class="table table-sm table-hover">
    <thead><tr><th>ID</th><th>Название</th><th>Обновить</th><th>Удалить</th></tr></thead>
    <tbody>
    <c:forEach items="${categories}" var="category">
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td><button class="btn btn-sm btn-outline-primary" type="button" data-bs-toggle="collapse" data-bs-target="#edit-category-${category.id}" aria-expanded="false" aria-controls="edit-category-${category.id}">Редактировать</button></td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin">
                    <input type="hidden" name="entity" value="category"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${category.id}">
                    <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
                    <button class="btn btn-sm btn-outline-danger">Удалить</button>
                </form>
            </td>
        </tr>
        <tr>
            <td colspan="4" class="py-0 border-0">
                <div class="collapse mt-2" id="edit-category-${category.id}">
                    <div class="card card-body">
                        <form method="post" action="${pageContext.request.contextPath}/admin" class="d-flex gap-2">
                            <input type="hidden" name="entity" value="category"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${category.id}">
                            <input type="hidden" name="q" value="${q}"><input type="hidden" name="page" value="${currentPage}">
                            <input class="form-control form-control-sm" name="name" maxlength="120" value="${category.name}" required>
                            <button class="btn btn-sm btn-outline-primary">Сохранить</button>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${totalPages > 1}">
    <nav aria-label="Страницы категорий"><ul class="pagination justify-content-center mt-3"><li class="page-item ${hasPrevious ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/categories?page=${currentPage - 1}&q=${q}">&lt;</a></li><c:forEach begin="1" end="${totalPages}" var="i"><li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/admin/categories?page=${i}&q=${q}">${i}</a></li></c:forEach><li class="page-item ${hasNext ? '' : 'disabled'}"><a class="page-link" href="${pageContext.request.contextPath}/admin/categories?page=${currentPage + 1}&q=${q}">&gt;</a></li></ul></nav>
</c:if>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>

