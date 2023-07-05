<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: luye
  Date: 2023/6/20
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.userName}老师你好</title>
    <link rel="stylesheet" href="../../css/toughGlassInner.css">
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
                <c:forEach items="${sessionScope.roomList}" var="c">
                    <form action="">
                        <h4>${c.roomName}</h4>
                        <p>${c.room}</p>
                        <input type="submit" value="choose">
                    </form>
                </c:forEach>
            </div>
        </div>
    </div>
</section>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.9.1/jquery.min.js">
</script>
</body>
</html>
