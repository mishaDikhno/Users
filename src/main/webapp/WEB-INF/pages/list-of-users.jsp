<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--<c:url var="stylesheet" value="/css/user.css" />--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>List of users</title>
    <style type="text/css">
        .border {
            border-collapse: collapse;
            border: 2px solid black;
        }
        .struct {
            border-collapse: collapse;
            border: 1px solid black;
            text-align: center;
        }
        div {
            border: 4px;
        }
        .search {
            float: left;
        }
        .table {
            position: absolute;
            left: 300px;
        }
        .adding {
            position: absolute;
            float: left;
            top: 140px;
        }
    </style>
</head>
<body>

<div class="search">
    <h1>Search</h1>

    <form action="${pageContext.request.contextPath}/user/search" method="get">
        <table>
            <tr>
                <td><label for="name">Name</label></td>
                <td><input type="text" name="name" id="name"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="search"> <a
                        href="${pageContext.request.contextPath}/user/remove.html">${reset}</a></td>
            </tr>
        </table>
    </form>
    <c:if test="${empty users}">
        <font color="red">Name is typed incorrectly, or it does not exist!</font>
    </c:if>
</div>

<div class="adding">
    <p><h2>Create a new User!<br/></h2>
    <a href="${pageContext.request.contextPath}/user/add.html">Add new user</a><br/></p>
</div>

<div class="table" align="center">
    <h1>List of users</h1>
    <c:if test="${not empty users}">
        <table class="border">
            <thead>
            <tr>
                <th class="struct" width="50px">id</th>
                <th class="struct" width="150px">name</th>
                <th class="struct" width="50px">age</th>
                <th class="struct" width="50px">isAdmin</th>
                <th class="struct" width="250px">createDate</th>
                <th class="struct" width="100px">action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr id="links">
                    <td class="struct"><a href="${pageContext.request.contextPath}/user/select/${user.id}.html"></a>${user.id}</td>
                    <td class="struct">${user.name}</td>
                    <td class="struct">${user.age}</td>
                    <td class="struct">${user.isAdmin}</td>
                    <td class="struct">${user.createDate}</td>
                    <td class="struct">
                        <a href="${pageContext.request.contextPath}/user/edit/${user.id}.html">Edit</a><br/>
                        <a href="${pageContext.request.contextPath}/user/delete/${user.id}.html">Delete</a><br/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${number > 1}">

            <div>
                <table>
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/user/first">${first}</a></td>
                        <td><a href="${pageContext.request.contextPath}/user/previous">${previous}</a></td>
                        <c:forEach var="i" begin="${startPage}" end="${current}">
                            <td><a href="${pageContext.request.contextPath}/user/list.html?page=${i-1}">${i}</a></td>
                        </c:forEach>
                        <td>${current+1}</td>
                        <c:forEach var="i" begin="${current+2}" end="${endPage}">
                            <td><a href="${pageContext.request.contextPath}/user/list.html?page=${i-1}">${i}</a></td>
                        </c:forEach>
                        <td><a href="${pageContext.request.contextPath}/user/next">${next}</a></td>
                        <td><a href="${pageContext.request.contextPath}/user/last">${last}</a></td>
                    </tr>
                </table>
            </div>
        </c:if>
        <br/>
        <font color="green" size="16">${message}</font>
    </c:if>
</div>
</body>
</html>
