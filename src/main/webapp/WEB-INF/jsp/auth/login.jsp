<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Вход</h2>
<form method="post" action="${pageContext.request.contextPath}/auth/login">
    <label>Email <input name="email" type="email" required></label><br>
    <label>Пароль <input name="password" type="password" required></label><br>
    <button type="submit">Войти</button>
</form>
</body>
</html>
