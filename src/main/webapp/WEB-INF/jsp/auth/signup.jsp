<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Регистрация"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-5">
        <h3>Регистрация</h3>
        <form method="post" action="${pageContext.request.contextPath}/auth/signup" class="card p-3">
            <div class="mb-3">
                <label class="form-label">Имя</label>
                <input class="form-control" name="name" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" type="email" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Пароль</label>
                <input class="form-control" name="password" type="password" required>
            </div>
            <button class="btn btn-primary" type="submit">Создать аккаунт</button>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
