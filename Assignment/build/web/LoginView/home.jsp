<%-- 
    Document   : home
    Created on : 8 thg 3, 2024, 14:35:01
    Author     : vdhung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>
        <link rel="stylesheet" href="css/HomeStyle.css">
    </head>
    <body style="background-image: url('images/sanhAlpha.jpg');background-size: cover;   background-repeat: no-repeat;  background-position: center;background-attachment: fixed; ">
        <div id="nav-bar">
            <input id="nav-toggle" type="checkbox"/>
            <div id="nav-header"><a id="nav-title" href="https://daihoc.fpt.edu.vn/" target="_blank"><i class="fab fa-codepen"></i> FPT University</a>
                <label for="nav-toggle"><span id="nav-toggle-burger"></span></label>
                <hr/>
            </div>
            <div id="nav-content">
                <div class="nav-button"><i class="fas fa-home"></i><span onclick="window.location.href = 'home'">Home</span></div>
                <hr/>
                <div class="nav-button"><i class="fas fa-calendar-alt"></i>
                    <c:if test="${sessionScope.account.role.id eq 2}">
                        <span onclick="window.location.href = 'lecturer/schedule?id=${sessionScope.account.id}'">Schedule</span>
                    </c:if>
                    <c:if test="${sessionScope.account.role.id eq 1}">
                        <span onclick="window.location.href = 'student/schedule?id=${sessionScope.account.id}'">Schedule</span>
                    </c:if>
                </div>
                <c:if test="${sessionScope.account.role.id eq 2}">
                    <hr/>
                    <div class="nav-button"><i class="fas fa-pen"></i> <span onclick="window.location.href = 'lecturer/givescore'">Give Score</span> </div>
                </c:if>

                <c:if test="${sessionScope.account.role.id eq 1}">
                    <hr/>
                    <div class="nav-button"><i class="fas fa-graduation-cap"></i><span onclick="window.location.href = 'student/score'">View Score</span></div>
                        </c:if>
                <div id="nav-content-highlight"></div>
            </div>
            <input id="nav-footer-toggle" type="checkbox"/>
            <div id="nav-footer">
                <div id="nav-footer-heading">
                    <div id="nav-footer-avatar"><img src="images/fpt.jpg"/></div>
                    <div id="nav-footer-titlebox"><a id="nav-footer-title">${sessionScope.account.displayname}</a>
                        <span id="nav-footer-subtitle">
                            <c:if test="${sessionScope.account.role.id eq 2}">Lecturer</c:if>
                            <c:if test="${sessionScope.account.role.id eq 1}">Student</c:if>
                        </span>
                    </div>
                    <label for="nav-footer-toggle"><i class="fas fa-caret-up"></i></label>
                </div>
                <div id="nav-footer-content">
                    <button><i class="fas fa-door-open"><a href="logout"> Logout</a></i></button>
                </div>
            </div>
        </div>
        <div class="text-center">
            <h2 class="pb-4 pl-3 mb-3">
                <span>FPT</span><br>
                <span>EDUCATION</span><br>
                <button onclick="window.location.href = 'https://international.fpt.edu.vn/'"><b>LEARN MORE</b></button>
            </h2>
            <br>
        </div>
    </body>
</html>
