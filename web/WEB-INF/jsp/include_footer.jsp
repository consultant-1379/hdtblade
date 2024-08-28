<%-- 
    Document   : include_footer
    Created on : 01-Jul-2011, 10:21:24
    Author     : escralp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<div id="footer">
    <div class="footer-content">
        <ul>
            <li class="first"><fmt:message key="about.hdtversion" bundle="${msg_bundle}"/> <c:out value="${hdt_ver}"/>,
            <fmt:message key="about.build" bundle="${msg_bundle}"/> <c:out value="${hdt_buildnum}"/></li>
            <li><fmt:message key="about.dbversion" bundle="${msg_bundle}"/> <c:out value="${hdt_db_ver}"/></li>
        </ul>
    </div>
</div>
