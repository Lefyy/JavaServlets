<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Статусы заказов"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3>Статусы заказов</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>
<div class="card p-3 mb-3">
    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-2">
        <input type="hidden" name="entity" value="status"><input type="hidden" name="action" value="create">
        <div class="col-md-4"><input class="form-control" name="name" placeholder="Название статуса" required></div>
        <div class="col-md-2"><button class="btn btn-primary">Создать</button></div>
    </form>
</div>
<table class="table table-sm table-hover">
    <thead><tr><th>ID</th><th>Название</th><th>Обновить</th><th>Удалить</th></tr></thead>
    <tbody>
    <c:forEach items="${statuses}" var="status">
        <tr>
            <td>${status.id}</td>
            <td>${status.name}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin" class="d-flex gap-2">
                    <input type="hidden" name="entity" value="status"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${status.id}">
                    <input class="form-control form-control-sm" name="name" value="${status.name}" required>
                    <button class="btn btn-sm btn-outline-primary">Сохранить</button>
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin">
                    <input type="hidden" name="entity" value="status"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${status.id}">
                    <button class="btn btn-sm btn-outline-danger">Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
