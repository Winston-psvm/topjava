<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 20.06.2021
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<a href="index.html">Home</a>
<hr>
<h2>${param.action=='create'? 'Create meal' : 'Edit meal'}</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
    </dl>
    <dl>
        <dt>Description</dt>
        <dd><input type="text" value="${meal.description}" name="description" size="20" required></dd>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Back</button>
</form>
</body>
</html>
