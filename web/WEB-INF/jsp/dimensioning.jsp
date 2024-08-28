<%-- 
    Document   : dimensioning.jsp
    Created on : 17-Nov-2010, 17:52:48
    Author     : escralp
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>

<head>
    <%@include file="/WEB-INF/jsp/include_css.jsp" %>
    <%@include file="/WEB-INF/jsp/include_scripts.jsp" %>

     <%-- Include the JS utility code. --%>
    <script type="text/javascript" src="resources/js/hdt/dimensioning_utils.js?ver=${hdt_buildnum}"></script>

    <title><fmt:message key="dimensioning.title" bundle="${msg_bundle}"/></title>
    
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
                
            <form:form action="dimensioning.htm" commandName="dimensioningForm">
                <div id="overview-container">
                    <div class="content">
                        <h2><fmt:message key="dimensioning.content_header1" bundle="${msg_bundle}"/></h2>                        
                        <div id="summary-content">
                            <table class="summary-tbl">
                                <tbody>
                                    <tr>
                                        <td class="tbl-heading"><fmt:message key="dimensioning.customer_name" bundle="${msg_bundle}"/></td>
                                        <td><c:out value="${customerName}"/></td>
                                        <td class="tbl-heading"><fmt:message key="dimensioning.conf_name" bundle="${msg_bundle}"/></td>
                                        <td><c:out value="${confName}"/></td>
                                        <td id="bundle-heading" class="tbl-heading"><fmt:message key="dimensioning.bundle" bundle="${msg_bundle}"/></td>
                                        <td id="bundle-desc"><c:out value="${bundleDesc}"/></td>
                                    </tr>
                                    <tr>
                                        <td class="slim-row">&nbsp;</td>
                                        <td class="slim-row">&nbsp;</td>
                                        <td class="slim-row">&nbsp;</td>
                                        <td class="slim-row">&nbsp;</td>
                                        <td class="slim-row">&nbsp;</td>
                                        <td class="slim-row">&nbsp;</td>
                                    <tr>
                                        <td class="tbl-heading"><fmt:message key="dimensioning.product" bundle="${msg_bundle}"/></td>
                                        <td>${systemDesc}</td>
                                        <td class="tbl-heading"><fmt:message key="dimensioning.network" bundle="${msg_bundle}"/></td>
                                        <td>${networkDesc}</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                </tbody>
                            </table>
                            <%--
                            <div id="change-settings">
                                <a class="arrow-link" href="<fmt:message key="main-nav-1" bundle="${url_bundle}"/>"><fmt:message key="dimensioning.content_change" bundle="${msg_bundle}"/></a>
                            </div>
                            --%>
                            <div class="cleardiv"></div>
                        </div>
                    </div>
                    <div id="content-end">&nbsp;</div>
                </div>

                <div id="content-container">
                    <div class="content">
                        <h2><fmt:message key="dimensioning.content_header2" bundle="${msg_bundle}"/></h2>
                        <div class="warn-note"><fmt:message key="welcome.warn_note" bundle="${msg_bundle}"/></div>
                        <table class="result">
                            <thead>
                            <th class="first-col"><fmt:message key="dimensioning.content_role" bundle="${msg_bundle}"/></th>
                            <th><fmt:message key="dimensioning.content_rec_hw" bundle="${msg_bundle}"/></th>
                            </thead>

                            <%-- This is the root for adding in all the result rows. --%>
                            <tbody class="result-rows"/>                           
                        </table>

                        <div id="submit-form-container">
                            <button id="go-top-button" type="button"><fmt:message key="dimensioning.conf_go_top" bundle="${msg_bundle}"/></button>
                            <button type="submit" id="submit-button"><fmt:message key="dimensioning.conf_submit" bundle="${msg_bundle}"/></button>
                        </div>
                        <div class="cleardiv"></div>
                    </div>
                    <div id="content-end">&nbsp;</div>
                </div>
            </form:form>

            <%@include file="/WEB-INF/jsp/include_footer.jsp" %>
            
        </div>
        <div id="footer-bottom">&nbsp;</div>
        
        <%@include file="/WEB-INF/jsp/include_body.jsp" %>
    </body>    
    <%@ include file="/WEB-INF/jsp/page_bottom_tags.jsp" %>
</html>