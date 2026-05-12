<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Вход"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-5">
        <h3>Вход</h3>
        <form method="post" action="${pageContext.request.contextPath}/auth/login" class="card p-3">
            <div class="mb-3"><label class="form-label">Email</label><input class="form-control" name="email" type="email" maxlength="120" required></div>
            <div class="mb-3"><label class="form-label">Пароль</label><input class="form-control" name="password" type="password" minlength="4" maxlength="120" required></div>
            <div>
                <button class="btn btn-primary" type="submit">Войти</button>
                <a href="${pageContext.request.contextPath}/auth/signup" class="btn btn-link">Регистрация</a>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>

