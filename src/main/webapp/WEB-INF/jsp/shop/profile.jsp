<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Профиль"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-4">
        <h4>Профиль</h4>
        <form method="post" action="${pageContext.request.contextPath}/profile" class="card p-3">
            <div class="mb-2">
                <label class="form-label">Имя</label>
                <input class="form-control" name="name" value="${currentCustomer.name}">
            </div>
            <div class="mb-2">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" value="${currentCustomer.email}">
            </div>
            <div class="mb-3">
                <label class="form-label">Новый пароль</label>
                <input class="form-control" name="password" type="password">
            </div>
            <button class="btn btn-primary">Сохранить</button>
        </form>
    </div>
    <div class="col-md-8">
        <h4>Мои заказы</h4>
        <div class="table-responsive">
            <table class="table table-hover table-sm">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Статус</th>
                    <th>Дата</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>#${order.id}</td>
                        <td>${order.statusId}</td>
                        <td>${order.createdAt}</td>
                        <td><a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/orders/success?id=${order.id}">Открыть</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
