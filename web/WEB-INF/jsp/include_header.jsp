<%-- 
    Document   : include_header.jsp
    Created on : 01-Jul-2011, 10:12:05
    Author     : escralp
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<div id="header">
    <div id="logo">
        <a title="Ericsson" href="<fmt:message key="welcome-url-ericsson" bundle="${url_bundle}"/>"><span class="hide-element">Ericsson</span></a>
        <h1><fmt:message key="welcome.heading" bundle="${msg_bundle}"/></h1>
    </div>
    <div id="header-elements">
        <div id="login-area">
            <div id="login-area-container">
                <ul>
                    <li class="person"><fmt:message key="welcome.loggedin_as" bundle="${msg_bundle}"/> <span class="login-user">demo</span> (<a target="_top" href="#">Logout</a>)</li>
                    <li class="time"><fmt:message key="welcome.session_expires" bundle="${msg_bundle}"/> <span class="login-expiry">00:00:00</span></li>
                </ul>
            </div>
        </div>

        <div id="quicklinks" >
            <c:set var="linkCounter" value="${0}" />
            <c:forEach var="qLItem" begin="0" items="${quickLinks}">
                <c:choose>
                    <c:when test="${linkCounter mod 4 == 0}">
                        <ul>
                        <li><a class="first" href="<c:out value="${qLItem.description}"/>" target="_blank"><c:out value="${qLItem.name}"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<c:out value="${qLItem.description}"/>" target="_blank"><c:out value="${qLItem.name}"/></a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${(linkCounter + 1) mod 4 == 0}">
                        </ul><br/>
                    </c:when>
                </c:choose>
                <c:set var="linkCounter" value="${linkCounter + 1}" />
            </c:forEach>
        </div>
    </div>
</div>
