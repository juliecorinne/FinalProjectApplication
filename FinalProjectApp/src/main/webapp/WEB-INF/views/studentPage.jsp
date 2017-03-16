<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hyunchoi
  Date: 3/16/17
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>studentpage</title>
</head>
<body>
${message5}
<form action="classJoined" method="get"  class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
    <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
        Join a Class </h3>
    <hr>
    <select class="form-control" onchange="this.form.submit()" name="classToJoin">
        <option disabled selected value> -- select a class-- </option>
        <c:forEach var="myvar" items="${theList}">
            <option value = ${myvar.classId}>${myvar.name}  ${myvar.classId}</option>
        </c:forEach>
    </select>

</form>

</body>
</html>
