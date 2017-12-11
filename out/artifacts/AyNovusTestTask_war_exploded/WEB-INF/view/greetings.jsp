<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Greetings
    </title>
<body>

<div>
    ${requestScope.greeting} ${requestScope.name}!<br>
</div>
<br> ${requestScope.ip}
<br>Последний раз вы у нас были : ${requestScope.dateLastTimeWas}
<br>Сайт просмотрели ${requestScope.viewCount} раз(а)

<div>
    <form method="post" action="/greetings">
        <div>
            <input value="Выйти" type="submit">
        </div>
    </form>
</div>

</body>
</head>
</html>