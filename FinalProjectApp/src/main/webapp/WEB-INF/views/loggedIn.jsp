<%--
  Created by IntelliJ IDEA.
  User: hyunchoi
  Date: 3/14/17
  Time: 3:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher</title>
    <!-- teacher -->
</head>
<body>
THIS IS STUDNET

<br>

TAKE TEST HERE


<form action="testTaken" method="post" name="register" class="form-signin" style="max-width: 420px;
                              padding: 30px 38px 66px;
                              margin: 0 auto;
                              background-color: #eee;
                              border: 3px dotted rgba(0,0,0,0.1);
                              background-color:rgba(0, 0, 0, 0.6);
                                ">
        <textarea name="studentAnswer" cols="40" rows="5" placeholder="questionanswer"></textarea>
        <textarea name="studentAnswer" cols="40" rows="5" placeholder="questionanswer"></textarea>
        <textarea name="studentAnswer" cols="40" rows="5" placeholder="questionanswer"></textarea>
        <textarea name="studentAnswer" cols="40" rows="5" placeholder="questionanswer"></textarea>
        <textarea name="studentAnswer" cols="40" rows="5" placeholder="questionanswer"></textarea>
    <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Submit" type="Submit">Submit test</button>


</form>

${message}


<br>


${message2}



</body>
</html>
