<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Add user page!</title>
</head>
<body>
<h1>Add new User!</h1>

<p>Create a new user!!!</p>
<font color="red">${message}</font>
<form action="${pageContext.request.contextPath}/user/add" method="post">
    <table>
        <tr>
            <td><label for="name">Name</label></td>
            <td><input type="text" name="name" id="name"></td>
        </tr>
        <tr>
            <td><label for="age">Age</label></td>
            <td><input type="text" name="age" id="age"></td>
        </tr>
        <tr>

            <td><label><input type="radio" name="isAdmin" value="true">Admin</label></td>
            <td><label><input type="radio" name="isAdmin" value="false" checked>User</label></td>

        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Add"></td>
        </tr>
    </table>
</form>
<br/>

<p><a href="${pageContext.request.contextPath}/">Home page!</a></p>
</body>
</html>
