<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Заказы"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<h3><i class="fas fa-shopping-cart"></i> Заказы</h3>
<a href="${pageContext.request.contextPath}/admin" class="btn btn-sm btn-outline-secondary mb-3">&larr; Назад к админ-панели</a>
<div class="table-responsive">
    <table class="table table-sm table-hover align-middle">
        <thead><tr><th>ID</th><th>Покупатель</th><th>Статус</th><th>Создан</th><th>Обновить</th><th>Удалить</th></tr></thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${customerNames[order.customerId]} <small class="text-muted">(ID: ${order.customerId})</small></td>
                <td>${statusNames[order.statusId]} <small class="text-muted">(ID: ${order.statusId})</small></td>
                <td>${order.createdAt}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-1">
                        <input type="hidden" name="entity" value="order"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${order.id}">
                        <div class="col-md-4">
                            <select class="form-select form-select-sm" name="statusId">
                                <c:forEach items="${statuses}" var="status">
                                    <option value="${status.id}" <c:if test="${status.id == order.statusId}">selected</c:if>>${status.id}: ${status.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2"><button class="btn btn-sm btn-outline-primary">Сохранить</button></div>
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin">
                        <input type="hidden" name="entity" value="order"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${order.id}">
                        <button class="btn btn-sm btn-outline-danger">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
