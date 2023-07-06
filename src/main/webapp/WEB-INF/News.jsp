<%--
  Created by IntelliJ IDEA.
  User: luye
  Date: 2023/7/5
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/css/toughGlassInner.css";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TEST</title>
    <link rel="stylesheet" href="<%=basePath%>" />
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
        <%--<!-- 导航栏 -->--%>
        <div class="row">
            <div class="col-md-12">
                <ul class="header">
                    <li><a href="#">Description</a></li>
                    <li><a href="#">News</a></li>
                    <li><a href="#">Labs</a></li>
                    <li><a href="#">Teachers</a></li>
                    <c:if test="${sessionScope.user.groupID < 2}">
                        <li><a href="#">Add</a></li>
                        <li><a href="#">Add</a></li>
                        <li><a href="#">Add</a></li>
                    </c:if>
                    <li class="right"><a href="#">Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <%--<!--   sidebar     -->--%>
            <div class="col-md-2 sidebar">
                <div class="sidebar-group">
                    <h2>News List</h2>
                    <ul>
                        <c:forEach items="${sessionScope.newsList}" var="c">
                            <li><a href="newsID_${c.newsID}">${c.newsName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <%-- main --%>
            <div class="col-md-10 art">
                <form id="mainText" method="post" action="test">
                    <label for="mainArt"></label><textarea id="mainArt" name="mainArt" class="form_textarea">The Test Line</textarea>
                    <div class="form">
                        <div class="inputBox"><input type="submit" id="submit" name="submit"></div>
                    </div>
                </form>
            </div>
        </div>
        <!-- footer -->
        <div class="area row">
            <div class="col-md-12 footer">
                <p>North East University<br>
                    Software Engineering 2023&copy;<br>
                    Project By KUKUKING
                </p>
            </div>
        </div>
    </div>
</section>
</body>
<script src="https://code.jquery.com/jquery-3.0.0.min.js" type="text/javascript"></script>
<script src="https://cdn.tiny.cloud/1/nsufo92uklykf0xlw5giofxczqyj9s4tnorxx3n74uo45xqq/tinymce/5/tinymce.min.js"
        referrerpolicy="origin"></script>
<%--<script src="webjars/tinymce/5.10.6/tinymce.min.js"></script>--%>
<script>
    tinymce.init({
        selector: "#mainArt",
        language: "zh_CN",
        plugins: "image",
        statusbar: false
    });
</script>
</html>
