<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Заказ принят"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-8">
        <h3>Спасибо, заказ #${order.id} принят</h3>
        <p>Статус: ${order.statusId}</p>
        <p>Дата: ${order.createdAt}</p>
        <h5 class="mt-4">Состав заказа</h5>
        <table class="table table-sm">
            <thead>
            <tr>
                <th>Товар ID</th>
                <th>Цена</th>
                <th>Кол-во</th>
                <th>Сумма</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td>${item.productId}</td>
                    <td>${item.priceAtPurchase}</td>
                    <td>${item.quantity}</td>
                    <td>${item.priceAtPurchase * item.quantity}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/products">Вернуться в магазин</a>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
