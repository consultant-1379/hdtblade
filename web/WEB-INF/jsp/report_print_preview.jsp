<%-- 
    Document   : report_print_preview
    Created on : 10-Mar-2011, 19:47:22
    Author     : escralp

    THIS WAS DEPRECATED ON 11082011.
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>

<head>    
    <link rel="stylesheet" href="resources/css/hdt_print.css" type="text/css"/>

    <title><fmt:message key="report.print_preview.title" bundle="${msg_bundle}"/></title>
</head>
<html>
    <body>
        <div id="doc">
            <div id="header">
                <fmt:message key="about.hdtversion" bundle="${msg_bundle}"/> <c:out value="${hdt_ver}"/>, <fmt:message key="about.dbversion" bundle="${msg_bundle}"/> <c:out value="${hdt_db_ver}"/>, Date: <c:out value="${date_str}"/>
                <span class="button-container">
                    <button type="button" id="print-me-button" onclick="window.print();return false;"><fmt:message key="report.button_print" bundle="${msg_bundle}"/></button>
                    <button type="button" id="close-me-button" onclick="window.close();return false;"><fmt:message key="report.button_close" bundle="${msg_bundle}"/></button>
                </span>
            </div>

            <div id="overview-container">
                <div class="content">
                    <h2 class="large-heading"><fmt:message key="report.content_header1" bundle="${msg_bundle}"/></h2>
                    <div class="warn-note-res">
                        <h3>Notes</h3>
                        <ul>
                            <c:forEach var="note" items="${noteList}">
                                <c:choose>
                                    <c:when test="${note.enabled == true}">
                                        <li><c:out value="${note.noteText}"/></li>
                                    </c:when>                                    
                                </c:choose>
                            </c:forEach>
                        </ul>
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
                                <td class="item"><fmt:message key="dimensioning.bundle" bundle="${msg_bundle}"/></td>
                                <td>${bundleDesc}</td>
                            </tr>
                            </tbody>
                        </table>

                        <h3><fmt:message key="report.content_subheader3" bundle="${msg_bundle}"/></h3>                        
                        <div id="results-tbl-container">
                            <table class="result">
                                <thead>
                                <th class="first-col"><fmt:message key="dimensioning.content_role" bundle="${msg_bundle}"/></th>
                                <th><fmt:message key="dimensioning.content_rec_hw" bundle="${msg_bundle}"/></th>                                
                                </thead>
                                <c:forEach var="role" items="${roleList}">
                                    <tbody class="result-rows">
                                        <c:set var="listOfAlternatives" value="${appNumMap[role.name]}"/>
                                        <tr>
                                            <td class="role-col">
                                                <span class="role-info">
                                                    <c:out value="${role.description}"/>
                                                </span>
                                            </td>

                                            <td class="recommended-col">
                                                <table class="hw-results-tbl">
                                                    <thead class="hw-print-res">
                                                    <th class="result-app-rep"><fmt:message key="dimensioning.res.app_num" bundle="${msg_bundle}"/></th>
                                                    <th class="result-qty-rep"><fmt:message key="dimensioning.res.app_qty" bundle="${msg_bundle}"/></th>
                                                    <th class="result-desc-rep"><fmt:message key="dimensioning.res.app_desc" bundle="${msg_bundle}"/></th>
                                                    <th class="result-eol-rep"><fmt:message key="dimensioning.res.eol" bundle="${msg_bundle}"/></th>
                                                    <th class="result-lod-rep"><fmt:message key="dimensioning.res.lod" bundle="${msg_bundle}"/></th>
                                                    </thead>
                                                    <tbody>
                                                        <c:choose>
                                                            <c:when test="${not empty listOfAlternatives}">
                                                                <c:forEach var="appNum" items="${listOfAlternatives[0]}">
                                                                    <tr>
                                                                        <td><c:out value="${appNum.name}"/></td>
                                                                        <%-- Check if user has overriden. --%>
                                                                        <c:choose>
                                                                            <c:when test="${appNum.userQtySet != true}">
                                                                                <td><c:out value="${appNum.quantity}"/></td>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <td><c:out value="${appNum.userQuantity}"/></td>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                        <td><c:out value="${appNum.description}"/></td>
                                                                        <td><c:out value="${appNum.endOfServiceLife}"/></td>
                                                                        <td><c:out value="${appNum.lastOrderDate}"/></td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <tr>
                                                                    <td><fmt:message key="dimensioning.res.hw_no_result" bundle="${msg_bundle}"/></td>
                                                                    <td><fmt:message key="dimensioning.res.hw_no_result" bundle="${msg_bundle}"/></td>
                                                                    <td><fmt:message key="dimensioning.res.hw_no_result" bundle="${msg_bundle}"/></td>
                                                                    <td><fmt:message key="dimensioning.res.hw_no_result" bundle="${msg_bundle}"/></td>
                                                                    <td><fmt:message key="dimensioning.res.hw_no_result" bundle="${msg_bundle}"/></td>
                                                                </tr>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </tbody>                                                    
                                                </table>
                                            </td>                                           
                                        </tr>
                                    </tbody>
                                </c:forEach>
                                <tbody>
                                    <tr>
                                        <td class="end-row">&nbsp;</td>
                                        <td class="end-row">&nbsp;</td>                                       
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>                
            </div>

            <div id="footer">
                <div class="footer-content">
                </div>
            </div>
        </div>
    </body>
</html>