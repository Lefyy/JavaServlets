<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Регистрация</h2>
<form method="post" action="${pageContext.request.contextPath}/auth/signup">
    <label>Имя <input name="name" required></label><br>
    <label>Email <input name="email" type="email" required></label><br>
    <label>Пароль <input name="password" type="password" required></label><br>
    <button type="submit">Создать аккаунт</button>
</form>
</body>
</html>
