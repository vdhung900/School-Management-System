<%-- 
    Document   : login
    Created on : 4 thg 3, 2024, 22:02:58
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/LoginStyle.css">
    </head>
    <body class="align">

        <div class="grid align__item">

            <div class="register">

                <svg xmlns="http://www.w3.org/2000/svg" class="site__logo" width="56" height="84" viewBox="77.7 214.9 274.7 412"><defs><linearGradient id="a" x1="0%" y1="0%" y2="0%"><stop offset="0%" stop-color="#8ceabb"/><stop offset="100%" stop-color="#378f7b"/></linearGradient></defs><path fill="url(#a)" d="M215 214.9c-83.6 123.5-137.3 200.8-137.3 275.9 0 75.2 61.4 136.1 137.3 136.1s137.3-60.9 137.3-136.1c0-75.1-53.7-152.4-137.3-275.9z"/></svg>

                <h2>Login</h2>

                <form action="login" method="POST" class="form">

                    <div class="form__field">
                        <input type="email" placeholder="info@fpt.edu.vn" name="username">
                    </div>

                    <div class="form__field">
                        <input type="password" placeholder="••••••••••••" name="password">
                    </div>

                    <div class="form__field">
                        <input type="submit" value="Login">
                    </div>

                </form>

                <p>Already have an accout? <a href="#">Log in</a></p>

            </div>

        </div>

    </body>
    <!--    <body>
            <form action="login" method="POST">
                Username: <input type="type" name="username">
                Password: <input type="type" name="password">
                <input type="submit" value="Login"/>
            </form>
        </body>-->
</html>
