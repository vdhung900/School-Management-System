<%-- 
    Document   : score
    Created on : 11 thg 3, 2024, 16:41:25
    Author     : vdhung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Score Report</title>
        <link rel="stylesheet" href="../css/theme.css">
        <style>
            select#soflow-color {
                border: 4px solid #AAA;
                font-size: inherit;
                margin-bottom: 20px;
                padding: 5px 75px;
                width: 250px;
                font-weight: bold;
                font-size: large;
            }
        </style>
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
            <form action="score" method="GET">
                <select id="soflow-color" name="suid" onchange="this.form.submit()" style="margin-left: 600px;">
                    <c:forEach items="${requestScope.subjects}" var="sub">
                        <option<c:if test="${param.suid eq sub.id}"> selected="selected"</c:if>
                                                                     value="${sub.id}" style="font-weight: bold;">${sub.name}</option>
                    </c:forEach>
                </select>
            </form>
            <table border="1px">
                <tr>
                    <th>Grade category</th>
                    <th>Grade item</th>
                    <th>Weight</th>
                    <th>Value</th>
                </tr>
                <c:set var="average" value=""></c:set>
                <c:set var="countpoint" value="0"></c:set>
                <c:set var="countscore" value="0"></c:set>
                <c:set var="final_exam_value" value=""></c:set>
                <c:set var="final_exam_resit_value" value=""></c:set>
                <c:forEach items="${requestScope.categories}" var="c">
                    <c:set var="totalweight" value=""></c:set>
                    <c:set var="totalvalue" value=""></c:set>
                        <tr>
                            <td rowspan="${c.points.size()+2}">${c.name}</td>
                        <c:forEach items="${c.points}" var="p">
                        <tr>
                            <c:set var="countpoint" value="${countpoint + 1}"></c:set>
                            <td>${p.name}</td>
                            <td>${p.weight * 100}%</td>
                            <c:set var="totalweight" value="${totalweight + p.weight}"></c:set>
                                <td>
                                <c:forEach items="${requestScope.scores}" var="sc">
                                    <c:if test="${sc.point.id eq p.id}">
                                        ${sc.value}
                                        <c:if test="${c.id eq 7}">
                                            <c:set var="final_exam_value" value="${sc.value}"></c:set>
                                        </c:if>
                                        <c:if test="${c.id eq 8}">
                                            <c:set var="final_exam_resit_value" value="${sc.value}"></c:set>
                                        </c:if>
                                        <c:if test="${sc.value ne null}">
                                            <c:set var="countscore" value="${countscore + 1}"></c:set> 
                                        </c:if>
                                        <c:set var="totalvalue" value="${totalvalue + sc.value}"></c:set>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>  
                    </c:forEach>
                    <tr>
                        <td>Total</td>
                        <td><fmt:formatNumber value="${totalweight * 100}" type="number" pattern="#0.0#" />%</td>
                        <td>
                            <c:if test="${!(totalvalue eq '')}"><fmt:formatNumber value="${totalvalue/c.points.size()}" type="number" pattern="#0.0#" /></c:if>
                        </td>
                        <c:if test="${(c.id eq 8) and (final_exam_resit_value ne null)}">
                            <c:set var="average" value="${average - (final_exam_value*totalweight)}"></c:set>
                        </c:if>
                        <c:set var="totalvalue" value="${totalvalue/c.points.size()}"></c:set>
                        <c:set var="average" value="${average + (totalweight*totalvalue)}"></c:set>
                        </tr>
                        </tr>
                </c:forEach>
                <tr style="font-size: large">
                    <td rowspan="2"><b>COURSE<br>TOTAL</b></td>
                    <td><b>AVERAGE</b></td>
                    <c:if test="${countscore ge (countpoint-1)}">
                        <td colspan="2"  style="text-align: center;"><b><fmt:formatNumber value="${average}" type="number" pattern="#.##" /></b></td>
                            </c:if>
                <tr style="font-size: large">
                    <td><b>STATUS</b></td>
                    <c:if test="${countscore ge (countpoint-1)}">
                        <c:if test="${average >= 4}"><td colspan="2" style="color: green;text-align: center;"><b>PASSED</b></td></c:if>
                        <c:if test="${average < 4}"><td colspan="2" style="color: red;text-align: center;"><b>NOT PASSED</b></td></c:if>
                    </c:if>
                    <c:if test="${!(countscore ge (countpoint-1))}">
                        <td colspan="2" style="color: green;text-align: center;"><b>STUDYING</b></td>
                    </c:if>
                </tr>
                </tr>
            </table>
        </div>
        <div class="footer">
            Powered by FPT University | CMS | library | books24x7
        </div>
    </body>
</body>
</html>
