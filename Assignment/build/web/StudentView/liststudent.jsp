<%-- 
    Document   : liststudent
    Created on : 9 thg 3, 2024, 21:05:26
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Student</title>
        <link rel="stylesheet" href="../css/theme.css">
    </head>
    <body>
        <div class="header">
            <div>FPT University Academic Portal</div>
            <div>
                <a href="../home">Home</a> |
                <a href="schedule?id=${sessionScope.account.id}">View Schedule</a> |
                <a href="#">List Student</a>
            </div>
        </div>
        <div class="container">
            <table border="1px">
                <tr>
                    <th>Index</th>
                    <th style="text-align: center;">Image</th>
                    <th>Member</th>
                    <th>Name</th>
                </tr>
                <c:forEach items="${requestScope.students}" var="s" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>
                        <center>
                            <img src="../images/ava/S${s.id}.jpg" style="height:150px;width:150px;border-width:0px;">
                        </center>
                        </td>
                        <td>${s.member}</td>
                        <td>${s.name}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</html>
