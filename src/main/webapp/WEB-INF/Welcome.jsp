<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TEST</title>
    <link rel="stylesheet" href="css/toughGlassInner.css">
    <script>
        tinymce.init({
            selector : "#mainArt",
            inline : true
        })
    </script>
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
        <!-- 导航栏 -->
        <div class="row">
            <div class="col-md-12">
                <ul class="header">
                    <li><a href="#">Description</a></li>
                    <li><a href="#">News</a></li>
                    <li><a href="#">Labs</a></li>
                    <li><a href="#">Teachers</a></li>
                    <li class="right"><a href="#">Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <!--   sidebar     -->
            <div class="col-md-2 sidebar">
                <div class="sidebar-group">
                    <h2>云技术管理</h2>
                    <ul>
                        <li><a href="#">云服务器</a></li>
                        <li><a href="#">云数据库</a></li>
                        <li><a href="#">负载均衡</a></li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="col-md-10 art form" >
                <div id="mainArt">这里是测试文本</div>
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
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</html>
