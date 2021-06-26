<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meals, this is fucking meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>
<table border="3" cellpadding="20" >
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="meal">
    <tr style="background-color:${meal.excess ? 'red' : 'green'}">
        <td>${meal.dateTime.toLocalDate()}</td> <!-- FIXME Where is time? -->
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="meals?action=update&id=${meal.id}">Update</a> </td>
        <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
        </c:forEach>
</table>
</body>
</html>
