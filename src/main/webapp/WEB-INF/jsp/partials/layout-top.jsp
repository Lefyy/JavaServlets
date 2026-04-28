<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title><c:out value="${pageTitle != null ? pageTitle : 'JavaShop'}"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .admin-button-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 20px; }
        .admin-button { display: flex; justify-content: center; align-items: center; padding: 40px; font-size: 1.2rem; font-weight: 500; text-decoration: none; color: #fff; border-radius: 15px; transition: all .2s ease; box-shadow: 0 4px 10px rgba(0,0,0,.1); }
        .admin-button:hover { color: #fff; transform: translateY(-5px); box-shadow: 0 6px 15px rgba(0,0,0,.15); }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/partials/header.jsp"/>
<div class="container mt-4">
