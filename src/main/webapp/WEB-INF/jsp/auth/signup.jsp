<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Sign up"/>
<jsp:include page="/WEB-INF/jsp/partials/layout-top.jsp"/>
<div class="row justify-content-center">
    <div class="col-md-5">
        <h3>Sign up</h3>
        <form method="post" action="${pageContext.request.contextPath}/auth/signup" class="card p-3">
            <div class="mb-3"><label class="form-label">Name</label><input class="form-control" name="name" maxlength="120" required></div>
            <div class="mb-3"><label class="form-label">Email</label><input class="form-control" name="email" type="email" maxlength="120" required></div>
            <div class="mb-3"><label class="form-label">Password</label><input class="form-control" name="password" type="password" minlength="4" maxlength="120" required></div>
            <button class="btn btn-primary" type="submit">Create account</button>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/partials/layout-bottom.jsp"/>
