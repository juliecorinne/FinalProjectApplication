<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: kamel
  Date: 1/12/2017
  Time: 3:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Group App</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>


</head>

<div class="container">
    <div class="jumbotron" style="background:url(http://edu.stemjobs.com/wp-content/uploads/2015/01/86487819.jpg);
                                      background-position: 0% 25%;
                                      background-size: cover;
                                      background-repeat: no-repeat;
                                      color: white;
                                      text-shadow: black 0.3em 0.3em 0.3em;
                                      height: 100vh;">

        <div class = "container">
            <div class="wrapper" style="margin-top: 20px;
                                     margin-bottom: 20px;">


                <form action="addStudent" method="post" name="welcome" class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
                    <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
                        ${message}</h3>


                    <hr style="border:solid;">

                    <br>

                    <input type="text" class="form-control" name="FirstName" placeholder="First Name" required="" autofocus="" style="position: relative;
                                  font-size: 16px;
                                  height: auto;
                                  padding: 10px;
                                  margin-bottom: 10px;
                                  border-bottom-left-radius: 0;
                                  border-bottom-right-radius: 0;"/>
                    <input type="text" class="form-control" name="LastName" placeholder="Last Name" required=""
                           style="position: relative;
                            font-size: 16px;
                            height: auto;
                            padding: 10px;
                            margin-bottom: 10px;
                            border-top-left-radius: 0;
                            border-top-right-radius: 0; "
                    />
                    <input type="email" class="form-control" name="email" placeholder="Email" required="" autofocus="" style="position: relative;
                                  font-size: 16px;
                                  height: auto;
                                  padding: 10px;
                                  margin-bottom: 10px;
                                  border-bottom-left-radius: 0;
                                  border-bottom-right-radius: 0;"/>
                    <input type="text" class="form-control" name="userName" placeholder="User Name" required=""
                           style="position: relative;
                            font-size: 16px;
                            height: auto;
                            padding: 10px;
                            margin-bottom: 10px;
                            border-top-left-radius: 0;
                            border-top-right-radius: 0; "
                    />
                    <input type="password" class="form-control" name="password" placeholder="Password" required=""
                           style="position: relative;
                            font-size: 16px;
                            height: auto;
                            padding: 10px;
                            margin-bottom: 10px;
                            border-top-left-radius: 0;
                            border-top-right-radius: 0; "
                    />

                    <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Sumbit" type="Submit" style="margin-bottom:2px">Submit</button>

                    <br>
                    <br>

                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
