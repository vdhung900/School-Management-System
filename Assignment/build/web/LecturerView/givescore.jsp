<%-- 
    Document   : givescore
    Created on : 18 thg 3, 2024, 15:15:31
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Give Score</title>
        <link rel="stylesheet" href="../css/theme.css">
        <style>
            input[type="submit"] {
                width: 130px;
                height: 40px;
                border-radius: 10px;
                background-color: chartreuse;
                margin-top: 10px;
                margin-bottom: 20px;
                margin-left: 1200px;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <div>FPT University Academic Portal</div>
            <div>
                <a href="../home">Home</a> |
                <a href="#">Give Score</a>
            </div>
        </div>
        <div class="container">
            <c:forEach items="${requestScope.groups}" var="g">
                <a style="font-size: large;" href="givescore?suid=${g.suid.id}&gid=${g.id}">${g.suid.name}-${g.name}</a><br>
            </c:forEach>
            <form action="givescore" method="POST">
                <input type="hidden" name="suid" value="${param.suid}"/>
                <input type="hidden" name="gid" value="${param.gid}"/>
                <table >
                    <thead>
                        <c:if test="${!(requestScope.points eq null)}">
                        <td>No</td>
                        <td>Student Name</td>
                        <td>Member</td>
                    </c:if>
                    <c:forEach items="${requestScope.points}" var="point">
                        <td>${point.name}</td>
                    </c:forEach>
                    </thead>
                    <c:forEach items="${requestScope.students}" var="student" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${student.name}</td>
                            <td>${student.member}</td>
                            <c:forEach items="${requestScope.points}" var="p">
                                <td>
                                    <c:set value="" var="scorevalue"></c:set>
                                    <c:forEach items="${student.scores}" var="score">
                                        <c:if test="${score.point.id eq p.id}">
                                            <c:set value="${score.value}" var="scorevalue"></c:set>
                                        </c:if>
                                    </c:forEach>
                                    <input style="width: 50px" type="text" name="score_student${student.id}_point${p.id}" value="${scorevalue}">
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
                <input type="submit" value="Save"/>
            </form>

        </div>
        <br><br>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</html>
