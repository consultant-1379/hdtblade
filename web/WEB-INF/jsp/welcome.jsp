<%--
    Document   : welcome
    Created on : 19-Oct-2010, 16:32:11
    Author     : escralp
    Version:   : 1.0
    FIXME: The version should come from CVS.
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>

<head>       
    <%@include file="/WEB-INF/jsp/include_css.jsp" %>
    <%@include file="/WEB-INF/jsp/include_scripts.jsp" %>

    <%-- Include the JS utility code. --%>
    <script type="text/javascript" src="resources/js/hdt/welcome_utils.js?ver=${hdt_buildnum}"></script>

    <title><fmt:message key="welcome.title" bundle="${msg_bundle}"/></title>
</head>
<html>    
    <body>
        <div id="doc">

            <%@include file="/WEB-INF/jsp/include_header.jsp" %>

            <div id="main-nav">
                <ul id="main-nav-left">
                    <li><a id="main-nav-1" class="first" href="<fmt:message key="main-nav-1" bundle="${url_bundle}"/>"><fmt:message key="welcome.main_nav_title1" bundle="${msg_bundle}"/></a></li>
                    <li><span id="main-nav-2" class="disabled-nav"><fmt:message key="welcome.main_nav_title2" bundle="${msg_bundle}"/></span></li>
                    <li><span id="main-nav-2" class="disabled-nav"><fmt:message key="welcome.main_nav_title3" bundle="${msg_bundle}"/></span></li>
                </ul>
            </div>

            <form:form action="welcome.htm" commandName="welcomeForm">
                <div id="content-container">
                    <div class="content">
                        <h2><fmt:message key="welcome.content_header1" bundle="${msg_bundle}"/></h2>
                        <div class="warn-note"><fmt:message key="welcome.warn_note" bundle="${msg_bundle}"/></div>
                        <div class="input-def">
                            <h3><fmt:message key="welcome.content_header2" bundle="${msg_bundle}"/></h3>
                            <div class="details-box">
                                <table class="details">
                                    <tr>
                                        <td><fmt:message key="welcome.content_customer_name" bundle="${msg_bundle}"/></td>
                                        <td><form:input path="customerName" /></td>
                                    </tr>
                                    <tr class="last">
                                        <td><fmt:message key="welcome.content_configuration_name" bundle="${msg_bundle}"/></td>
                                        <td><form:input path="confName" /></td>
                                    </tr>
                                </table>
                            </div>

                            <h3><fmt:message key="welcome.content_header3" bundle="${msg_bundle}"/></h3>
                            <div id="radio-prod" class="radio-list last">
                                <div class="button-container">
                                    <form:radiobuttons path="systemId" items="${systemId}" itemLabel="description" itemValue="name" cssClass="radio"/>
                                </div>
                            </div>

                            <div id="network-par-section" class="hide-element">
                                <h3><fmt:message key="welcome.content_header4" bundle="${msg_bundle}"/></h3>
                                <div id="network-selection" class="radio-list last">
                                    <div id="network-sel">
                                    </div>
                                    <div class="cleardiv"></div>
                                </div>
                            </div>
                           
                            <div id="dimensioning-par-section" class="hide-element">
                                <h3><fmt:message key="welcome.content_header5" bundle="${msg_bundle}"/></h3>
                                <div id="parameter-sel" class="radio-list last">
                                    <h4><fmt:message key="welcome.content_header6" bundle="${msg_bundle}"/></h4>
                                    <div id="dimensioning-par-radiobtn">                                                                             
                                    </div>
                                    <div class="cleardiv"></div>
                                    <div id="param-section">                                        
                                    </div>
                                    <div class="cleardiv"></div>
                                </div>
                            </div>
                        </div>
                        <div id="submit-form-container">
                            <button type="submit" id="submit-button"><fmt:message key="welcome.conf_submit" bundle="${msg_bundle}"/></button>
                        </div>
                        <div class="cleardiv"></div>
                    </form:form>
                </div>
                <div id="content-end">&nbsp;</div>
            </div>

            <%@include file="/WEB-INF/jsp/include_footer.jsp" %>
        </div>
        <div id="footer-bottom">&nbsp;</div>

        <%@include file="/WEB-INF/jsp/include_body.jsp" %>
        
    </body>
    <%@include file="/WEB-INF/jsp/page_bottom_tags.jsp" %>
</html>