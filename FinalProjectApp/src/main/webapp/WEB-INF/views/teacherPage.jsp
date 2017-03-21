<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hyunchoi
  Date: 3/14/17
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher Login</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <script type="text/javascript" src="https://embed.modernapp.co/chat?code=0af636fdc942f80def1de3c92d2637d8"></script>
</head>
<body>
<div>

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

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="container">
                <div class="jumbotron" style="background:url(http://edu.stemjobs.com/wp-content/uploads/2015/01/86487819.jpg);
                                      background-position: 0% 25%;
                                      background-size: cover;
                                      background-repeat: no-repeat;
                                      color: white;
                                      text-shadow: black 0.3em 0.3em 0.3em;
                                      height: 100vh;">

                    <div class = "container">
                        <div class="wrapper" style="margin-top: 80px;
                                     margin-bottom: 20px;">
                            <form action="valid" method="post" name="welcome" class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
                                <h3 class="form-signin-heading" style="text-align:center;
                                                          margin-bottom: 30px;">
                                   Hello! Welcome to your home page. To create a group, click on the "Create Class" tab and view the students in a class.
                                </h3>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        <div role="tabpanel" class="tab-pane" id="groups">



        </div>
        <%--<div role="tabpanel" class="tab-pane" id="messages">...</div>--%>
        <%--<div role="tabpanel" class="tab-pane" id="settings">...</div>--%>
    </div>

</div>
<script>
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        e.target // newly activated tab
        e.relatedTarget // previous active tab
    })
</script>
</body>
</html>
