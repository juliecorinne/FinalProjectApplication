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

<h2>Classes You Are Currently Enrolled In</h2>
<c:forEach items="${courseList}"  var="row" varStatus="loopCounter">
    <tr>
            <td>${row.classId}</td>
    </tr>
</c:forEach>


<form action="classJoined" method="post"  class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
    <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
        Join a Class </h3>
    <h1><a href="viewStudentGroup">LOOK AT UR GROUPS</a></h1>
    <hr>
    <select class="form-control"  name="classToJoin">
        <option disabled selected value> -- select a class-- </option>
        <c:forEach var="myvar" items="${theList}">
            <option value = ${myvar.classId}>${myvar.name}  ${myvar.classId}</option>
        </c:forEach>
    </select>

    <button type="submit" value="Submit">JOIN CLASS</button>

</form>
<form action="viewStudentGroup">
<select class="form-control"  name="groupToSee">
    <option disabled selected value> -- view groups by class-- </option>
    <c:forEach var="myvar" items="${courseList}">
        <option value = ${myvar.classId}>${myvar.classId}</option>
    </c:forEach>
</select>
    <button type="submit" value="Submit">VIEW GROUPS</button>
</form>

</body>
</html>
