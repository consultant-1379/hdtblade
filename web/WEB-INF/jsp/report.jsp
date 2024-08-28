<%-- 
    Document   : eniqs
    Created on : 28-Oct-2010, 18:43:40
    Author     : escralp
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>

<head>  
    <%@include file="/WEB-INF/jsp/include_css.jsp" %>
    <%@include file="/WEB-INF/jsp/include_scripts.jsp"%>

    <%-- Include the JS utility code. --%>
    <script type="text/javascript" src="resources/js/hdt/report_utils.js?ver=${hdt_buildnum}"></script>

    <title><fmt:message key="report.title" bundle="${msg_bundle}"/></title>        
</head>
<html>
    <body>
        <div id="doc">

            <%@include file="/WEB-INF/jsp/include_header.jsp" %>

            <div id="main-nav">
                <ul id="main-nav-left">
                    <li><a id="main-nav-1" class="first" href="<fmt:message key="main-nav-1" bundle="${url_bundle}"/>"><fmt:message key="welcome.main_nav_title1" bundle="${msg_bundle}"/></a></li>
                    <li><a id="main-nav-2" href="<fmt:message key="main-nav-2" bundle="${url_bundle}"/>"><fmt:message key="welcome.main_nav_title2" bundle="${msg_bundle}"/></a></li>
                    <li><a id="main-nav-3" href="<fmt:message key="main-nav-3" bundle="${url_bundle}"/>"><fmt:message key="welcome.main_nav_title3" bundle="${msg_bundle}"/></a></li>
                </ul>
            </div>

            <div id="session-reset-warn-dialog" class="hide-element">
                <p>
                    <span class="custom-config"><fmt:message key="dimensioning.warn_dialog_1" bundle="${msg_bundle}"/></span> <fmt:message key="dimensioning.warn_dialog_2" bundle="${msg_bundle}"/>?
                </p>
            </div>

            <div id="overview-container">
                <div class="content">
                    <div id="report-heading">
                        <h2><fmt:message key="report.content_header1" bundle="${msg_bundle}"/></h2>
                        <div id="button-container-top">
                            <button class="email-button" type="submit"><fmt:message key="report.button_xls" bundle="${msg_bundle}"/></button>
                            <%-- <button class="print-button" type="submit"><fmt:message key="report.button_print" bundle="${msg_bundle}"/></button>--%>
                        </div>
                    </div>
                    <div id="report-content">                        
                        <h3><fmt:message key="report.content_subheader1" bundle="${msg_bundle}"/></h3>
                        <table class="report-table">
                            <tbody>
                                <tr>
                                    <td class="item"><fmt:message key="dimensioning.customer_name" bundle="${msg_bundle}"/></td>
                                    <td><c:out value="${customerName}"/></td>
                                </tr>
                                <tr>
                                    <td class="item"><fmt:message key="dimensioning.conf_name" bundle="${msg_bundle}"/></td>
                                    <td><c:out value="${confName}"/></td>
                                </tr>
                            </tbody>
                        </table>

                        <h3><fmt:message key="report.content_subheader2" bundle="${msg_bundle}"/></h3>
                        <table class="report-table">
                            <tr>
                                <td class="item"><fmt:message key="dimensioning.product" bundle="${msg_bundle}"/></td>
                                <td>${systemDesc}</td>
                            </tr>
                            <tr>
                                <td class="item"><fmt:message key="dimensioning.network" bundle="${msg_bundle}"/></td>
                                <td>${networkDesc}</td>
                            </tr>
                            <tr>
                                <%-- Check if parameters or APPs have changed. --%>
                                <c:choose>
                                    <c:when test="${!(noParChanges && noAPPChanges)}">
                                        <td class="item custom-config"><fmt:message key="dimensioning.custom" bundle="${msg_bundle}"/></td>
                                        <td></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="item"><fmt:message key="dimensioning.bundle" bundle="${msg_bundle}"/></td>
                                        <td>${bundleDesc}</td>                                        
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                            </tbody>
                        </table>

                        <h3><fmt:message key="report.content_subheader3" bundle="${msg_bundle}"/></h3>
                        <div class="warn-note"><fmt:message key="welcome.warn_note" bundle="${msg_bundle}"/></div>
                        <div id="results-tbl-container">
                            <div id="condensed-report">                               
                            </div>
                        </div>                        
                        <div id="submit-form-container">
                            <button id="go-top-button" type="button"><fmt:message key="report.button_go_top" bundle="${msg_bundle}"/></button>
                            <button class="email-button" type="submit"><fmt:message key="report.button_xls" bundle="${msg_bundle}"/></button>
                            <%--<button class="print-button" type="submit"><fmt:message key="report.button_print" bundle="${msg_bundle}"/></button>--%>
                        </div>                        
                        <div class="cleardiv"></div>
                    </div>                    
                </div>
                <div id="content-end">&nbsp;</div>                
            </div>
            <%@include file="/WEB-INF/jsp/include_footer.jsp" %>
            <%@include file="/WEB-INF/jsp/include_body.jsp" %>
        </div>
        <div id="footer-bottom">&nbsp;</div>

    </body>
    <%@ include file="/WEB-INF/jsp/page_bottom_tags.jsp" %>
</html>
