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
    <title>TestPage</title>


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

<div class="container">
    <div class="jumbotron" style="background:url(http://edu.stemjobs.com/wp-content/uploads/2015/01/86487819.jpg);
                                      background-position: 0% 25%;
                                      background-size: cover;
                                      background-repeat: no-repeat;
                                      text-shadow: black 0.3em 0.3em 0.3em;
                                      height: 100vh;"
                            >
            <div class="wrapper" style="margin-top: -15px;
                                     margin-bottom: 20px;">

<form action="testTaken" method="post" name="register" class="form-signin" style="max-width: 100%;
                               padding-top: 10px;
                               padding-bottom: 5px;
                               padding-left: 15px;
                               padding-right: 15px;
                              /*padding: 30px 38px 66px;*/
                              margin-top: 0;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
<p style="color: #FFFFFF">To help us better place you into a group, please fill out the questions below.</p>
    <h4 style="color:white">${message}</h4>
<hr>
    <div style="color:#FFFFFF">
    What are three words you would use to describe yourself and why?<br>
    <textarea name="studentAnswer" cols="117" rows="2" placeholder="Please use no less than 20 words!" style="color: black"></textarea>
        <hr>
    How would someone else describe you after meeting you for the first time?<br>
    <textarea name="studentAnswer" cols="117" rows="2" placeholder="Please use no less than 20 words!" style="color: black"></textarea>
        <hr>
    Tell us about a time you were met with a challenge. How did you approach the challenge. What was the outcome?<br>
    <textarea name="studentAnswer" cols="117" rows="2" placeholder="Please use no less than 20 words!" style="color: black"></textarea>
        <hr>
    Describe your ideal work environment.<br>
    <textarea name="studentAnswer" cols="117" rows="2" placeholder="Please use no less than 20 words!" style="color: black"></textarea>
        <hr>
    How do you deal with pressure or stressful situations?<br>
    <textarea name="studentAnswer" cols="117" rows="2" placeholder="Please use no less than 20 words!" style="color: black"></textarea>
        <hr>
    <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Submit" type="Submit">Submit test</button>
    </div>

</form>
    </div>
    </div>
</div>





</body>
</html>
