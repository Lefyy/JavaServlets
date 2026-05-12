<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/products">JavaShop</a>
        <div class="d-flex align-items-center gap-2">
            <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-primary">РљРѕСЂР·РёРЅР°</a>
            <c:if test="${currentCustomer != null}">
                <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline-secondary">РџСЂРѕС„РёР»СЊ</a>
            </c:if>
            <c:if test="${currentCustomer != null && currentCustomer.staff}">
                <a href="${pageContext.request.contextPath}/admin" class="btn btn-outline-dark">РџР°РЅРµР»СЊ Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°</a>
            </c:if>
            <c:choose>
                <c:when test="${currentCustomer == null}">
                    <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-link">Р’РѕР№С‚Рё</a>
                    <a href="${pageContext.request.contextPath}/auth/signup" class="btn btn-primary">Р РµРіРёСЃС‚СЂР°С†РёСЏ</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-link">Р’С‹Р№С‚Рё</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<c:if test="${flashMessage != null}">
    <div class="container mt-3">
        <div class="alert alert-info mb-0">${flashMessage}</div>
    </div>
</c:if>
