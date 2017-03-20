<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hyunchoi
  Date: 3/20/17
  Time: 2:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table border=1>
    <tr><th>Name</th>
        <th>Class Name</th>
        <th>Group Name</th>

    </tr>
    <c:forEach items="${listOfGroups}" var="row">
        <tr>
            <td>${row.studentId}</td>
            <td>${row.classId}</td>
            <td>${row.groupId}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
