<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html><body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<h2>Order statuses</h2>
<form method="post" action="${pageContext.request.contextPath}/admin">
    <input type="hidden" name="entity" value="status"><input type="hidden" name="action" value="create">
    <input name="name" placeholder="name"><button>Create</button>
</form>
<c:forEach items="${statuses}" var="status">
    <div>#${status.id} ${status.name}
        <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
            <input type="hidden" name="entity" value="status"><input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="${status.id}"><button>Delete</button>
        </form>
    </div>
</c:forEach>
</body></html>
