<%--
  Created by IntelliJ IDEA.
  User: luye
  Date: 2023/6/24
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>重置密码</title>
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
            <div class="form" id="form"><h2>Reset</h2>
                <form action="Reset" method="post">
                    <div class="inputBox"><input type="text" placeholder="UserID" name="userID" id="userID"></div>
                    <div class="inputBox"><input type="password" placeholder="Old Password or Reset Words"
                                                 name="oldPassword" id="oldPassword"></div>
                    <div class="inputBox"><input type="password" placeholder="New Password" name="password"
                                                 id="password"></div>
                    <div class="inputBox"><input type="password" placeholder="Password Again" name="password2"
                                                 id="password2"></div>
                    <div class="inputBox"><input type="submit" value="Reset" id="submit" disabled></div>
                    <p class="forget">Already have an account ?<a style="color: mediumpurple;" href="Login">Login</a>
                    </p>
                    <span id="tips">${requestScope.tips}</span>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
<script src="https://code.jquery.com/jquery-3.0.0.min.js" type="text/javascript"></script>
<script>
    const pw = $("#password");
    $("#form").on({
        input: function () {
            if ($("#userID").val().length === 10) {
                if (pw.val() === $("#password2").val()) {
                    if (pw.val().length >= 6) {
                        $("#submit").prop("disabled", false);
                        $("#tips").empty();
                    }else {
                        $("#submit").prop("disabled", true);
                        $("#tips").empty().append("<p>new password too short</p>");
                    }
                } else {
                    $("#submit").prop("disabled", true);
                    $("#tips").empty().append("<p>two new password isn't equal</p>");
                }
            } else {
                $("#submit").prop("disabled", true);
                $("#tips").empty().append("<p>userID incorrect</p>");
            }
        }
    })
</script>
</html>
