<%--
  Created by IntelliJ IDEA.
  User: luye
  Date: 2023/6/24
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link rel="stylesheet" href="../css/toughGlass.css">
</head>
<body>
<section>
    <div class="color"></div>
    <div class="color"></div>
    <div class="color"></div>
    <div class="box">
        <div class="square" style="--i:0;"></div>
        <div class="square" style="--i:1;"></div>
        <div class="square" style="--i:2;"></div>
        <div class="square" style="--i:3;"></div>
        <div class="square" style="--i:4;"></div>
        <div class="container">
            <div class="form" id="form"><h2>Login</h2>
                <form action="Filter/Login" method="post">
                    <div class="inputBox"><input type="text" placeholder="UserID" name="userID" id="userID"></div>
                    <div class="inputBox"><input type="password" placeholder="Password" name="password" id="password">
                    </div>
                    <div class="inputBox"><input type="submit" value="Login" id="submit" disabled></div>
                    <p class="forget">Forget Password ?<a style="color: mediumpurple;" href="Reset">Reset</a></p>
                    <p class="forget">Don't have an account ?<a style="color: mediumpurple;" href="SignUp">Sign up</a>
                    </p>
                    <span id="tips" style="color: crimson">${requestScope.tips}</span>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
<script src="https://code.jquery.com/jquery-3.0.0.min.js" type="text/javascript"></script>
<script>
    $("#form").on({
        input: function () {
            if ($("#userID").val().length === 10) {
                if ($("#password").val().length >= 6) {
                    $("#submit").prop("disabled", false);
                    $("#tips").empty();
                } else {
                    $("#submit").prop("disabled", true);
                    $("#tips").empty().append("<p>password too short</p>");
                }
            } else {
                $("#tips").empty().append("<p>userID incorrect</p>");
            }
        }
    })
</script>
</html>
