<%-- 
    Document   : schedule
    Created on : 4 thg 3, 2024, 23:14:38
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                            ${les.group.name}-${les.group.suid.name} <br/>
                                            At ${les.room.name} <br/>
                                            <a href="takeattendance?id=${les.id}"> 
                                                <c:if test="${les.attended}">
                                                    Edit
                                                </c:if>
                                                <c:if test="${!les.attended}">
                                                    Take
                                                </c:if>     
                                            </a>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                        </tr>  
                    </c:forEach>
                </tbody>
            </table>   
        </div>
        <br><br>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</html>
