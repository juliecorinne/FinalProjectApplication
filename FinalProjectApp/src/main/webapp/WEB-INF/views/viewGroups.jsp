<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hyunchoi
  Date: 3/17/17
  Time: 9:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Group</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>

<div class="container" align="center">
    <div class="jumbotron" style="background:url(http://edu.stemjobs.com/wp-content/uploads/2015/01/86487819.jpg);
                                      background-position: 0% 25%;
                                      background-size: cover;
                                      background-repeat: no-repeat;
                                      text-shadow: black 0.3em 0.3em 0.3em;
                                      ;">

        <form style="max-width: 100%;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                              overflow: auto;">
            <div style="color:#FFFFFF">${message}</div>

<br>

    <div style="color:#FFFFFF"><h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
    View Student Groups</h3></div>
    <hr>

    <table border=1>
        <c:forEach items="${theGroup}" begin="0" var="row" varStatus="loopCounter">
            <tr style="color:#FFFFFF">
                <td><c:out value="Group ${loopCounter.count}: "></c:out></td>

                <c:forEach items="${row}" var="cell">
                    <td>${cell.firstName}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>





<br>



<div style="color:#FFFFFF">REMAINING STUDENT</div>
<table border=1>
    <c:forEach var="myvar" items="${leftover}">  <%----creating a loop that displays list--%>
        <tr style="color:#FFFFFF">
            <td> ${myvar.firstName}  ${myvar.lastName} </td>

        </tr>
    </c:forEach>
</table>
        </form>

<form action="manageGroup" method="post"  class="form-signin" style="max-width: 100%;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
    <p style="color:#FFFFFF">Add Student: </p>
    <hr>

    <select name="selectName">
        <option disabled selected value> -- select a remaining student -- </option>
        <c:forEach var="myvar" items="${leftover}" varStatus="loopCounter">
            <option value = ${loopCounter.count-1}>${myvar.firstName}  ${myvar.lastName}</option>
        </c:forEach>
    </select>

    <p style="color:#FFFFFF">To Group:</p>
    <select name="selectGroup">
        <option disabled selected value> -- select a group -- </option>
        <c:forEach var="myvar" items="${groups}" varStatus="loopCounter">
            <option value = ${loopCounter.count-1}>Group ${loopCounter.count}</option>
        </c:forEach>
    </select>

    <button type="submit" value="Submit">ADD STUDENT!!</button>


</form>
    </div>
</div>
</body>
</html>
