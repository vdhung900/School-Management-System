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
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
                width: 1500px;
            }
        </style>
    </head>
    <body>
        <form action="schedule" method="GET">
            <input type="hidden" name="id" value="${param.id}"/>
            Period: <input type="date" value="${requestScope.from}" name="from"/> - <input value="${requestScope.to}" type="date" name="to"/> 
            <input type="submit" value="Show"/>
        </form>
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
                        WEEK
                        <select name="week">
                            <option value="">01/01 To 07/01</option>
                            <option value="">08/01 To 14/01</option>
                            <option value="">15/01 To 21/01</option>
                            <option value="" selected="">22/01 To 28/01</option>
                            <option value="">29/01 To 04/02</option>
                        </select>
                    </th>
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
                                        ${les.group.name}-${les.group.suid.name} <br/>At ${les.room.name} <br/>
                                        <a href="att?id=${les.id}"> 
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
    </body>
</html>
