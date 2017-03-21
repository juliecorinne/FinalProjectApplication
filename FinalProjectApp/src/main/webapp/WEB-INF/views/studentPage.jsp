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
    <script type="text/javascript" src="https://embed.modernapp.co/chat?code=0af636fdc942f80def1de3c92d2637d8"></script>
</head>
<body>



<div class="container" align="center">
    <div class="jumbotron" style="background:url(http://edu.stemjobs.com/wp-content/uploads/2015/01/86487819.jpg);
                                      background-position: 0% 25%;
                                      background-size: cover;
                                      background-repeat: no-repeat;
                                      text-shadow: black 0.3em 0.3em 0.3em;
                                      height: 100vh;">
        <div style="padding-top: 100px;">
<form action="classJoined" method="post"  class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
    <h2 style="color:#FFFFFF">Classes You Are Currently Enrolled In</h2>
    <div style="color:#FFFFFF">
    <c:forEach items="${courseList}"  var="row" varStatus="loopCounter">
        <tr style="color:#FFFFFF">
            <td>|${row.classId}| </td>
        </tr>
    </c:forEach>
    </div>
    <h2 style="color:#FFFFFF"
    class="form-signin-heading" style="text-align:right;
                                                          margin-bottom: 30px;">
        Join a Class </h2>

    <hr>
    <select class="form-control"  name="classToJoin">
        <option disabled selected value> -- select a class -- </option>
        <c:forEach var="myvar" items="${theList}">
            <option value = ${myvar.classId}>${myvar.name}  ${myvar.classId}</option>
        </c:forEach>
    </select>

    <button type="submit" value="Submit">JOIN CLASS</button>
</form>
        </div>
<form action="viewStudentGroup" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">

        <h2 style="color:#FFFFFF">View Groups</h2>
        <hr>
    <select class="form-control"  name="groupToSee">
        <option disabled selected value> -- view groups by class -- </option>
        <c:forEach var="myvar" items="${courseList}">
            <option value = ${myvar.classId}>${myvar.classId}</option>
        </c:forEach>
    </select>
    <button type="submit" value="Submit">VIEW GROUPS</button>
</form>
    </div>
</div>

</body>
</html>
