<%-- 
    Document   : change_parameters_form
    Created on : 16-May-2011, 12:19:28
    Author     : escralp
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>

<h2><fmt:message key="alt.heading1" bundle="${msg_bundle}"/> <c:out value="${currentRole.description}"/></h2>
<div id="alt-content-list">
    <ul id="alt-<c:out value="${currentRole.name}"/>" class="alternatives-list">
        <c:forEach var="appAlternative" items="${appAlternativesList}" varStatus="idx">
            <li>           
                <c:choose>
                    <c:when test="${(idx.index == selectedIdx) && (appAlternative.hwStatus != 'HW_STATUS_DEPRECATED')}">
                        <c:choose>
                            <c:when test="${appAlternative.hwStatus == 'HW_STATUS_EOL'}">
                                <div id="${idx.index}" class="alt-content selected eol">
                            </c:when>
                            <c:otherwise>
                                <div id="${idx.index}" class="alt-content selected">
                            </c:otherwise>
                        </c:choose>                    
                        <div class="alt-h1"><c:out value="${appAlternative.name}"/><span class='ui-icon ui-icon-check check-status'></span></div>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${appAlternative.hwStatus == 'HW_STATUS_DEPRECATED'}">
                                <div id="${idx.index}" class="alt-content deprecated">
                            </c:when>
                            <c:when test="${appAlternative.hwStatus == 'HW_STATUS_EOL'}">
                                <div id="${idx.index}" class="alt-content eol">
                            </c:when>
                            <c:otherwise>
                                <div id="${idx.index}" class="alt-content">
                            </c:otherwise>                                
                        </c:choose>
                        <div class="alt-h1"><c:out value="${appAlternative.name}"/></div>
                    </c:otherwise>
                </c:choose>
                <div class="alt-h2"><c:out value="${appAlternative.description}"/></div>
                <c:choose>
                    <c:when test="${appAlternative.hwStatus == 'HW_STATUS_EOL'}">
                        <div class="alt-h2 eol"><fmt:message key="alt.eol" bundle="${msg_bundle}"/></div>
                    </c:when>
                    <c:when test="${appAlternative.hwStatus == 'HW_STATUS_DEPRECATED'}">
                        <div class="alt-h2 deprecated"><fmt:message key="alt.deprecated" bundle="${msg_bundle}"/></div>
                    </c:when>
                    <c:otherwise>
                        <div class="alt-h2"><fmt:message key="alt.rev" bundle="${msg_bundle}"/><c:out value="${appAlternative.hwRevision}"/></div>
                    </c:otherwise>
                </c:choose>
                </div>	
            </li> 
        </c:forEach>
    </ul>
</div>
