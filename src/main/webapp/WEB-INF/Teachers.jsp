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
    <title>Teachers</title>
    <link rel="stylesheet" href="<%=basePath%>"/>
</head>
<body style="overflow: scroll">
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
                    <li><a href="Introductions">Description</a></li>
                    <li><a href="News">News</a></li>
                    <li><a href="Labs">Labs</a></li>
                    <li><a href="Teachers">Teachers</a></li>
                    <li class="right"><a href="LogOff">Log Out</a></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <%--<!--   sidebar     -->--%>
            <div class="col-md-2 sidebar">
                <div class="sidebar-group">
                    <h2>Teachers List</h2>
                    <ul>
                        <c:forEach items="${sessionScope.teachersList}" var="c">
                            <li><a id="${c.teacherID}" class="teachers" href="Teachers?teacherID=${c.teacherID}">${c.teacherName}</a></li>
                        </c:forEach>
                    </ul>
                    <c:if test="${sessionScope.user.groupID < 2}">
                        <h2>Manage</h2>
                        <ul>
                            <li><a id="add" href="#">Add</a></li>
                            <li><a id="change" href="#">Change</a></li>
                        </ul>
                    </c:if>
                </div>
            </div>
            <%-- main --%>
            <div class="col-md-10 art">
                <div id="readOnly">
                    <h1>${sessionScope.mainTeacher.teacherName}</h1>
                    ${sessionScope.mainTeacher.teacherText}
                </div>

                <form style="display: none" id="manageForm" method="post" action="Teachers">
                    <label for="mainID"><input readonly style="display: none" name="mainID" id="mainID"
                                               value="${sessionScope.mainTeacher.teacherID}">
                    </label>
                    <label for="ifDelete"><input readonly style="display: none" name="ifDelete" id="ifDelete"
                                                         value="0">
                    </label>
                    <div class="form"><div class="inputBox">
                    <label for="mainName">
                         <input id="mainName" type="text" placeholder="Teacher's Name" name="mainName" style="color: black" value="${sessionScope.mainTeacher.teacherName}" required>
                    </label></div>
                    <label for="mainText"></label><textarea id="mainText" name="mainText"
                                                           class="form_textarea">${sessionScope.mainTeacher.teacherText}</textarea>

                        <div class="inputBox row">
                            <input type="submit" id="submit" name="submit" value="Submit">
                            <input type="button" id="undo" value="Undo" style="background: gray">
                            <input class="right" type="submit" id="delete" value="delete" style="background: red">
                        </div>
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
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.tiny.cloud/1/nsufo92uklykf0xlw5giofxczqyj9s4tnorxx3n74uo45xqq/tinymce/5/tinymce.min.js"
        referrerpolicy="origin"></script>
<%--<script src="webjars/tinymce/5.10.6/tinymce.min.js"></script>--%>
<script>
    $("#change").on({
        click: function () {
            tinymce.init({
                selector: "#mainText",
                language: "zh_CN",
                plugins: "image",
                statusbar: false,
                resize:true,
                height:900
            });
            $("#readOnly").css('display', 'none');
            $("#manageForm").css('display', '');
        }
    });
    $("#add").on({
        click: function () {
            tinymce.init({
                selector: "#mainText",
                language: "zh_CN",
                plugins: "image",
                statusbar: false,
                resize:true,
                height:900
            })
            $("#readOnly").css('display', 'none');
            $("#manageForm").css('display', '');
            $("#mainID").val(-1);
            $("#mainName").val(null);
            $("#mainText").val(null);
        }
    });
    $("#undo").on({
        click: function () {
            $("#readOnly").css('display', '');
            $("#manageForm").css('display', 'none');
        }
    });
    $("#delete").on({
        click: function () {
            $("#ifDelete").val(1);
        }
    });
</script>
</html>
