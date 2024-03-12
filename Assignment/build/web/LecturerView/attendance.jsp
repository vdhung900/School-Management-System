<%-- 
    Document   : attendance
    Created on : 7 thg 3, 2024, 23:59:49
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take Attendance</title>
        <link rel="stylesheet" href="../css/theme.css">
        <style>
        input[type="submit"] {
            width: 130px;
            height: 40px;
            border-radius: 10px;
            background-color: chartreuse;
            margin-top: 20px;
            margin-bottom: 20px;
            margin-left: 1340px;
        }
    </style>
    </head>
    <body>
        <div class="header">
            <div>FPT University Academic Portal</div>
            <div>
                <a href="../home">Home</a> |
                <a href="schedule?id=${sessionScope.account.id}">Schedule</a> |
                <a href="">Take Attendance</a>
            </div>
        </div>
        <div class="container">
            <form action="takeattendance" method="POST">
                <input type="hidden" name="id" value="${param.id}"/>
                <table border="1px">
                    <tr>
                        <th>Index</th>
                        <th>Code</th>
                        <th>Name</th>
                        <th>Images</th>
                        <th>Status</th>
                        <th>Comment</th>
                        <th>Record time</th>
                    </tr>
                    <c:forEach items="${requestScope.atts}" var="a" varStatus="status">
                        <tr>

                            <td>${status.index+1}</td>
                            <td>${a.student.member}</td>
                            <td>${a.student.name}</td>
                            <td>
                        <center>
                            <img src="../images/ava/${a.student.img}" style="height:150px;width:150px;border-width:0px;">
                        </center> 
                        </td>
                        <td>
                            <input type="radio" 
                                   ${!a.present?"checked=\"checked\"":""}
                                   value="no" name="present${a.student.id}"/> Absent          
                            <input type="radio"
                                   ${a.present?"checked=\"checked\"":""}
                                   value="yes" name="present${a.student.id}" /> Attended
                        </td>
                        <td>
                            <input name="description${a.student.id}" type="text" value="${a.description}"/>
                        </td>
                        <td>${a.time}</td>
                        </tr>   
                    </c:forEach>
                </table>
                <input type="submit" value="Save"/>
            </form>
        </div>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</html>
