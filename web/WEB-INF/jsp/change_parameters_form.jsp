<%-- 
    Document   : change_parameters_form
    Created on : 16-May-2011, 12:19:28
    Author     : escralp
--%>

<%@include file="/WEB-INF/jsp/include_taglib.jsp" %>
<h2><fmt:message key="param.heading1" bundle="${msg_bundle}"/> <c:out value="${currentRole.description}"/></h2>
<table id="<c:out value="${currentRole.name}"/>-form" class="dimensioning-parameters-tbl">
<thead>
    <th class="par-desc"><fmt:message key="param.table.heading1" bundle="${msg_bundle}"/></th>
    <th><fmt:message key="param.table.heading2" bundle="${msg_bundle}"/></th>
    <th><fmt:message key="param.table.heading3" bundle="${msg_bundle}"/></th>
</thead>
<tbody>
    <%-- FIXME: Check if parameter list is empty. --%>
    <c:forEach var="parameter" items="${parameterList}">
        <c:choose>
            <c:when test="${parameter.visibility == 1 || parameter.visibility == 2}">
                <%-- Determine the parameter value. --%>
                <c:choose>
                    <c:when test="${parameter.valueSet == true}">
                        <c:set var="pVal" value="${parameter.value}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="pVal" value="${parameter.defaultValue}"/>
                    </c:otherwise>
                </c:choose>
                <%-- Set the idName initially to the parameter name, so we can use it down below. --%>
                <c:set var="idName" value="${parameter.name}"/>
                <tr>
                    <td class="par-desc">
                        <c:out value="${parameter.description}"/>
                    </td>                    
                    <td>
                        <div class="cell">
                            <c:choose>
                                <c:when test="${parameter.parameterType == 'Double'
                                                || parameter.parameterType == 'Integer'
                                                || parameter.parameterType == 'String'}">
                                        <%-- 
                                                ID has to be unique for the input tag below to allow validation code to work.
                                                But we need the parameter name also, so stick it into the payload section.
                                        --%>                                        
                                        <input id="<c:out value="${idName}"/>" class="input-el parameterName:<c:out value="${parameter.name}"/> default:<c:out value="${parameter.defaultValue}"/>" type="text" class="value-input-left" value="<c:out value="${pVal}"/>"/>
                                        <script type="text/javascript">
                                            <%-- Live Validation JavaScript code, automatically generated. Works off a unique <input> tag ID. --%>
                                            var <c:out value="${idName}"/> = new LiveValidation('<c:out value="${idName}"/>', {validMessage: " ", wait: 1000});
                                            <c:out value="${idName}"/>.add(Validate.Presence, {failureMessage: "Value field must not be empty."});
                                            <c:out value="${idName}"/>.add(Validate.Numericality, {minimum: <c:out value="${parameter.minValue}"/>, maximum: <c:out value="${parameter.maxValue}"/>,
                                                tooLowMessage: "Minimum value is: <c:out value="${parameter.minValue}"/>.",
                                                tooHighMessage: "Maximum value is: <c:out value="${parameter.maxValue}"/>.",
                                                notANumberMessage: "Must be a number."});                                           
                                        </script>
                                        <c:set var="fieldType" value="text"/>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>                                        
                                        <c:when test="${pVal == true}">
                                            <%--
                                                 No validation here but stick the parameter name in the payload anyway
                                                 because the JS posting this to the server works off the payload section.
                                            --%>
                                            <input id="<c:out value="${idName}"/>" class="input-el parameterName:<c:out value="${parameter.name}"/>  default:<c:out value="${parameter.defaultValue}"/>" type="checkbox" checked="checked"/>
                                        </c:when>
                                        <c:otherwise>
                                            <%--
                                                 No validation here but stick the parameter name in the payload anyway
                                                 because the JS posting this to the server works off the payload section.
                                            --%>
                                            <input id="<c:out value="${idName}"/>" class="input-el parameterName:<c:out value="${parameter.name}"/>  default:<c:out value="${parameter.defaultValue}"/>" type="checkbox"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:set var="fieldType" value="checkbox"/>
                                </c:otherwise>
                            </c:choose>
                        </div>                        
                    </td>                    
                    <td>
                        <a id="<c:out value="${idName}"/>" class="arrow-link reset-link type:<c:out value="${fieldType}"/> default:<c:out value="${parameter.defaultValue}"/>" href="javascript:void(0);"><fmt:message key="param.table.reset_link" bundle="${msg_bundle}"/> (<b><c:out value="${parameter.defaultValue}"/></b>)</a>
                    </td>
                </tr>
            </c:when>
        </c:choose>
    </c:forEach>
</tbody>
</table>