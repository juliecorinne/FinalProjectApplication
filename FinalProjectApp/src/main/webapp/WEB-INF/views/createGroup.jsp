<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hyunchoi
  Date: 3/15/17
  Time: 9:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Class</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>

<form action="classSelected" method="get"  class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
    <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
        View Students In Class</h3>
    <hr>
    <select class="form-control" onchange="this.form.submit()" name="selectClass">
        <option disabled selected value> -- select a class-- </option>
        <c:forEach var="myvar" items="${theList}">
            <option value = ${myvar.classId}>${myvar.name}  ${myvar.classId}</option>
        </c:forEach>
    </select>
    <table border=1>
        <c:forEach var="myvar" items="${studentList}">  <%----creating a loop that displays list--%>
            <tr>
                <td> ${myvar.firstName}   ${myvar.lastName}</td>

                <td><a href="delete?id=${myvar.userName}"> Delete </a></td> <%----this is the delete button----%>
            </tr>
        </c:forEach>
    </table>

</form>
<form action="groupCreated" method="post"  class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
    Type in number of students per group <input name="groupNum" type="text"/>

    What criteria would you like to group your students with??? <br>

    <select class="form-control" name="selectCriteria" onchange="this.form.submit()" >
        <option disabled selected value> -- select a criteria-- </option>
        <option value="oppenness">Oppenness</option>
        <option value="emotionalRange">Emotional Range</option>
        <option value="agreeableness">Aggreeableness</option>
        <option value="introExtro">Introverted/Extroverted</option>
        <option value="conscientiousness">Conscientiousness</option>
    </select>



</form>
</body>
</html>
