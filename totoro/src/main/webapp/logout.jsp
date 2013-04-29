<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags/sso"     			prefix="sso" %>
<html>
<head>
	<meta name="decorator" content="cleanuserless"/>
	<title>Parameters</title>
</head>
<body>
	<div id="content">
	
	<img align="absmiddle" src="${pageContext.request.contextPath }/r/images/loading_progress.gif"/> &nbsp; Logging you out of the system
	
	
	</div>
	<div id="post-content-js">
	<script>
		function callFinalLogout() { parent.window.location.href='${pageContext.request.contextPath}/g/logoutfinal.do'; }
		setTimeout('callFinalLogout()', 5000);
	</script>	
	</div>

</body>
		
