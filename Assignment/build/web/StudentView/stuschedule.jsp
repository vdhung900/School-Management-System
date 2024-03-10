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
        <title>Lecturer Schedule</title>
        <link rel="stylesheet" href="../css/theme.css">
    </head>
    <body>
        <div class="header">
            <div>FPT University Academic Portal</div>
            <div>
                <a href="../home">Home</a> |
                <a href="#">View Schedule</a>|
                <a href="#">List Student</a>
            </div>
        </div>
        <div class="container">
            <form action="schedule" method="GET">
                <input type="hidden" name="id" value="${param.id}"/>
                Period: <input type="date" value="${requestScope.from}" name="from"/> - <input value="${requestScope.to}" type="date" name="to"/> 
                <input type="submit" value="Show"/>
            </form>
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
                            <span>YEAR</span>
                            <select name="year">
                                <option value="">2021</option>
                                <option value="">2022</option>
                                <option value="">2023</option>
                                <option value="" selected="">2024</option>
                                <option value="">2025</option>
                            </select>
                            </br>
                            <c:set var="currentweek" value="${requestScope.currentweek}"/>
                            <form id="weekForm" action="studentschedule" method="GET">
                                <input type="hidden" id="from" name="from">
                                <input type="hidden" id="to" name="to">
                                WEEK <select name="selectedWeek" id="selectedWeek" onchange="sendWeekData()">
                                    <c:forEach items="${requestScope.formattedWeeks}" var="week">
                                        <option <c:if test="${currentweek eq week}"> selected="" </c:if>>${week}</option>
                                    </c:forEach>
                                </select>
                                </br>
                                <input type="submit" style="display:none;">
                            </form>
                        <th>MON</th>
                        <th>TUE</th>
                        <th>WED</th>
                        <th>THU</th>
                        <th>FRI</th>
                        <th>SAT</th>
                        <th>SUN</th>
                    </tr>
                    <tr>
                        <c:forEach items="${requestScope.dates}" var="d">
                            <td>${d}</td>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.slots}" var="slot">
                        <tr>
                            <td>${slot.name}</td>
                            <c:forEach items="${requestScope.dates}" var="d">
                                <td>
                                    <c:forEach items="${requestScope.lessons}" var="les">
                                        <c:if test="${d eq les.date and les.slot.id eq slot.id}">
                                            <a href="list?id=${les.id}"> 
                                                ${les.group.name}-${les.group.suid.name}
                                            </a>
                                            <br/>At ${les.room.name} <br/>
                                            <%
                                                 LessonDBContext lessDB = new LessonDBContext();
                                                  boolean check = lessDB.checkAttendance(1, 1);
                                            %>
                                            <c:if test="${check}">
                                                Attended
                                            </c:if>
                                            <c:if test="${!check}">
                                                Absent
                                            </c:if>     
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
                    <li>(<font color="green">attended</font>) : hungvdhe 186864 had attended this activity / Võ Đình Hưng đã tham gia hoạt động này</li>
                    <li>(<font color="red">absent</font>) : hungvdhe 188884 had NOT attended this activity / Võ Đình Hưng đã vắng mặt buổi này</li>
                    <li>(-) : no data was given / chưa có dữ liệu</li>
                </ul>
            </div>
        </div>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</html>
