<!--
    index.jsp - Just a redirect page.
-->

<%@ include file="/WEB-INF/jsp/include_taglib.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<c:redirect url="welcome.htm"/>