<%-- 
    Document   : schedule
    Created on : 9 thg 3, 2024, 00:01:45
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dal.LessonDBContext" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Schedule</title>
        <link rel="stylesheet" href="../css/theme.css">
    </head>
    <body>
        <div class="header">
            <div>FPT University Academic Portal</div>
            <div>
                <a href="../home">Home</a> |
                <a href="#">View Schedule</a>
            </div>
        </div>
        <div class="container">
            <p>
                <b>Note:</b>
                These activities do not include extra-curriculum activities, such as club activities ...
            </p>
            <p>
                <b>Chú thích:</b>
                Các hoạt động trong bảng dưới không bao gồm hoạt động ngoại khóa, ví dụ như hoạt động câu lạc bộ ...
            </p>
            <p>
                Các phòng bắt đầu bằng AL thuộc tòa nhà Alpha. VD: AL...</br>
                Các phòng bắt đầu bằng BE thuộc tòa nhà Beta. VD: BE,..</br>
                Các phòng bắt đầu bằng G thuộc tòa nhà Gamma. VD: G201,...</br>
                Các phòng tập bằng đầu bằng R thuộc khu vực sân tập Vovinam.</br>
                Các phòng bắt đầu bằng DE thuộc tòa nhà Delta. VD: DE,..</br>
                Little UK (LUK) thuộc tầng 5 tòa nhà Delta</br>
            </p>
            <table>
                <thead>
                    <tr>
                        <th rowspan="2">
                            <form action="schedule" method="GET">
                                <input type="hidden" value="${requestScope.id}" name="id">
                                <input type="hidden" value="${sessionScope.week}" name="week">
                                YEAR
                                <select name="year" onchange="this.form.submit()">
                                    <c:forEach items="${sessionScope.years}" var="year">
                                        <option value="${year}" ${year == sessionScope.year ? 'selected' : ''}>${year}</option>
                                    </c:forEach>
                                </select>
                            </form>
                            </br>
                            <form action="schedule" method="GET">
                                <input type="hidden" value="${requestScope.id}" name="id">
                                <input type="hidden" value="${sessionScope.year}" name="year">
                                WEEK 
                                <select name="week" onchange="this.form.submit()">
                                    <c:forEach items="${sessionScope.weeks}" var="week" varStatus="status">
                                        <option value="${status.index + 1}" ${status.index + 1 == sessionScope.week ? 'selected' : ''}>${week}</option>
                                    </c:forEach>
                                </select>
                                </br>
                                <th>MON</th>
                                <th>TUE</th>
                                <th>WED</th>
                                <th>THU</th>
                                <th>FRI</th>
                                <th>SAT</th>
                                <th>SUN</th>
                    </tr>
                    <tr>
                        <c:forEach items="${sessionScope.dayOfWeek}" var="d">
                            <th>${d}</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${sessionScope.slots}" var="slot">
                        <tr>
                            <td>${slot.name}</td>
                            <c:forEach items="${sessionScope.dayOfWeek}" var="d">
                                <td>
                                    <c:forEach items="${sessionScope.lessons}" var="les">
                                        <c:if test="${d eq les.date and les.slot.id eq slot.id}">
                                            <a href="list?id=${les.id}"> 
                                                ${les.group.name}-${les.group.suid.name}
                                            </a>
                                            <br/>At ${les.room.name} <br/>
                                            <c:forEach items="${sessionScope.atts}" var="att">
                                                <c:if test="${att.lesson.id eq les.id}">
                                                    <c:if test="${att.present eq null}">
                                                        Not yet
                                                    </c:if>
                                                    <c:if test="${att.present}">
                                                        <div style="color: green">(Attended)</div>
                                                    </c:if>
                                                    <c:if test="${!att.present}">
                                                        <div style="color: red">(Absent)</div>
                                                    </c:if>            
                                                </c:if>
                                            </c:forEach>
                                            <div style="font-size: small">(${les.slot.period})</div>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                        </tr>  
                    </c:forEach>
                </tbody>
            </table>   
            <div class="note">
                <b>More note / Chú thích thêm: </b></br>
                <ul>
                    <li>(<font color="green">attended</font>) : attended this activity / đã tham gia hoạt động này</li>
                    <li>(<font color="red">absent</font>) : NOT attended this activity / đã vắng mặt buổi này</li>
                    <li>(-) : no data was given / chưa có dữ liệu</li>
                </ul>
            </div>
        </div>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</html>
