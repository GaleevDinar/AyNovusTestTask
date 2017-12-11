<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>

<body>
    <div>
        ${requestScope.info}
    </div>
<div>
    <div>
        <form method="post" action="/sign-in">
            <h1>Вход на сайт</h1>
            <div>
                <input placeholder="Имя" required="" name="login" type="text">
            </div>
            <div>
                <input placeholder="Пароль" required="" name="password" type="password">
            </div>
            <div>
                <input value="Войти" type="submit">
            </div>
        </form>
    </div>
    <div>
        <form method="get" action="/sign-up">
            <input value="Регистрация" type="submit">
        </form>
    </div>
</div>
</body>

</html>