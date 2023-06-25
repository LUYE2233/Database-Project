<%--
  Created by IntelliJ IDEA.
  User: luye
  Date: 2023/6/20
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.userName}同学你好</title>
    <link rel="stylesheet" href="../css/toughGlassInner.css">
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
        <div class="form" id="form">
            <h2>登陆成功，欢迎Dr.${sessionScope.user.userName}</h2>
            <div class="inputBox"><input type="submit" id="refresh" value="refresh"></div>
        </div>
        </div>
    </div>
</section>
</body>
</html>
