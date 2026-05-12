<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="РџСЂРѕС„РёР»СЊ"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row">
    <div class="col-md-4">
        <h4>РџСЂРѕС„РёР»СЊ</h4>
        <form method="post" action="${pageContext.request.contextPath}/profile" class="card p-3">
            <div class="mb-2">
                <label class="form-label">РРјСЏ</label>
                <input class="form-control" name="name" value="${currentCustomer.name}">
            </div>
            <div class="mb-2">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" value="${currentCustomer.email}">
            </div>
            <div class="mb-3">
                <label class="form-label">РќРѕРІС‹Р№ РїР°СЂРѕР»СЊ</label>
                <input class="form-control" name="password" type="password">
            </div>
            <button class="btn btn-primary">РЎРѕС…СЂР°РЅРёС‚СЊ</button>
        </form>
    </div>
    <div class="col-md-8">
        <h4>РњРѕРё Р·Р°РєР°Р·С‹</h4>
        <div class="table-responsive">
            <table class="table table-hover table-sm">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>РЎС‚Р°С‚СѓСЃ</th>
                    <th>Р”Р°С‚Р°</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>#${order.id}</td>
                        <td>${statusNames[order.statusId]} <small class="text-muted">(ID: ${order.statusId})</small></td>
                        <td><fmt:formatDate value="${orderCreatedAtDates[order.id]}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td><a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/orders/success?id=${order.id}">РћС‚РєСЂС‹С‚СЊ</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
