<!<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: kamel
  Date: 1/12/2017
  Time: 3:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Group App</title>
    ${message1}

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>

<!-- Nav tabs -->
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
        <li role="presentation"><a href="createClass" aria-controls="profile" role="tab" data-toggle="tab">Create Class</a></li>
        <li role="presentation"><a href="viewGroup" aria-controls="messages" role="tab" data-toggle="tab">View Groups</a></li>
    </ul>
    <ul class="nav navbar-right navbar-nav">
        <li role="presentation"><a href="/" aria-controls="messages" role="tab" data-toggle="tab">Log Out</a></li>
    </ul>
</div>


<div class="container">
    <div class="jumbotron" style="background:url(http://edu.stemjobs.com/wp-content/uploads/2015/01/86487819.jpg);
                                      background-position: 0% 25%;
                                      background-size: cover;
                                      background-repeat: no-repeat;
                                      color: white;
                                      text-shadow: black 0.3em 0.3em 0.3em;
                                      height: 100vh;">




            <form action="classCreated" method="post" name="register" class="form-signin" style="max-width: 420px;
                              padding: 10px 10px 15px;
                              margin: 0 auto;
                              background-color: #eee;
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
                <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
                    Create Class</h3>


                <hr style="border:solid;">

                <br>

                <input type="text" class="form-control" name="Classname" placeholder="Class Name" required="" autofocus="" style="position: relative;
                                  font-size: 16px;
                                  height: auto;
                                  padding: 10px;
                                  margin-bottom: 10px;
                                  border-bottom-left-radius: 0;
                                  border-bottom-right-radius: 0;"/>
                <input type="text" class="form-control" name="schoolName" placeholder="School Name" required=""
                       style="position: relative;
                            font-size: 16px;
                            height: auto;
                            padding: 10px;
                            margin-bottom: 10px;
                            border-top-left-radius: 0;
                            border-top-right-radius: 0; "
                />
                <input type="text" class="form-control" name="classID" placeholder="Class ID" required=""
                       style="position: relative;
                            font-size: 16px;
                            height: auto;
                            padding: 10px;
                            margin-bottom: 10px;
                            border-top-left-radius: 0;
                            border-top-right-radius: 0; "
                />
                <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" type="Submit">Register</button>

                <div class="text-center" style="padding-bottom: 10px;"><a href="/"><h3><b>Go Back</b></h3></a></div>
            </form>


            <form action="classSelected" method="get"  class="form-signin" style="max-width: 420px;
                    height: 150px;
                              padding: 10px 10px 0px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
                <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 0px;margin-top: 0px; padding-top: 15px; padding-bottom: 0px;">
                    View Students In Class</h3>
                <hr>

                <select class="form-control" onchange="this.form.submit()" name="selectClass" style="padding-top: 0px;">
                    <option disabled selected value> -- select a class-- </option>
                    <c:forEach var="myvar" items="${theList}">
                        <option value = ${myvar.classId}>${myvar.name}  ${myvar.classId}</option>
                    </c:forEach>
                </select>
                <table border=1>
                    <c:forEach var="myvar" items="${studentList}">  <%----creating a loop that displays list--%>
                        <tr>
                            <td> ${myvar.firstName}   ${myvar.lastName}</td>

                        </tr>
                    </c:forEach>
                </table>

            </form>

    </div>
</div>
</body>
</html>