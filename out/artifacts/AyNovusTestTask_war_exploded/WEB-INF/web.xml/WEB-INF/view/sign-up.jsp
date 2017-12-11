<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div>
    ${requestScope.info}
</div>
<div>
    ${requestScope.info2}
</div>
<form method="post" action="/sign-up">
    <label><input type="text" name="login"></label> Login <br>
    <label><input type="password" name="password"></label> Password <br>
    <label><input type="password" name="repeatPassword"></label> Password <br>
    <label><input type="submit" value="Зарегестрироваться"></label> <br>
</form>
</body>
</html>
