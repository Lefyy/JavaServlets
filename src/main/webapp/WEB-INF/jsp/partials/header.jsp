<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header style="margin-bottom: 20px;">
    <h1><a href="${pageContext.request.contextPath}/products">JavaShop</a></h1>
    <nav style="display: flex; gap: 12px;">
        <a href="${pageContext.request.contextPath}/products">Каталог</a>
        <a href="${pageContext.request.contextPath}/cart">Корзина</a>
        <a href="${pageContext.request.contextPath}/profile">Профиль</a>
        <c:if test="${currentCustomer != null && currentCustomer.staff}">
            <a href="${pageContext.request.contextPath}/admin">Админка</a>
        </c:if>
        <c:choose>
            <c:when test="${currentCustomer == null}">
                <a href="${pageContext.request.contextPath}/auth/login">Вход</a>
                <a href="${pageContext.request.contextPath}/auth/signup">Регистрация</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/auth/logout">Выход</a>
            </c:otherwise>
        </c:choose>
    </nav>
    <c:if test="${flashMessage != null}">
        <p style="color: #0a7;">${flashMessage}</p>
    </c:if>
</header>
