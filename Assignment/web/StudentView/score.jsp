<%-- 
    Document   : score
    Created on : 11 thg 3, 2024, 16:41:25
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Score Report</title>
        <link rel="stylesheet" href="../css/theme.css">
    </head>
    <body>
        <div class="header">
            <div>FPT University Academic Portal</div>
            <div>
                <a href="../home">Home</a> |
                <a href="#">Score</a>
            </div>
        </div>
        <div class="container">
            <table border="1px">
                <tr>
                    <th>Grade category</th>
                    <th>Grade item</th>
                    <th>Weight</th>
                    <th>Value</th>
                </tr>
                <c:forEach items="${requestScope.scores}" var="sc">
                    <tr>
                        <td>${sc.point.category.name}</td>
                        <td>${sc.point.name}</td>
                        <td>${sc.point.weight}</td>
                        <td>${sc.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
    </body>
</html>
