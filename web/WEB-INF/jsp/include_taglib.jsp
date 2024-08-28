<%-- 
    Document   : taglib-include.jsp
    Created on : 16-Feb-2011, 17:23:23
    Author     : escralp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%-- Include some tag libs. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%-- Bundle names tied to resource files. --%>
<fmt:setBundle basename="hdt.properties.messages" var="msg_bundle"/>
<fmt:setBundle basename="hdt.properties.urls" var="url_bundle"/>