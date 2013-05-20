<%@ include file="/WEB-INF/jsp/d.jsp" %>
<html>
<head>
<title>JVM Properties</title>
<meta name="decorator" content="clean-bootstrap" />
<meta name="where" content="support.logging" />
</head>
<body>
	

	<div id="content">
	
	
<%@page import="java.net.URLEncoder"%><jsp:useBean id="currentDate" class="java.util.Date" scope="request" />
<span id="moduleContentIdentifier" class="support_logging"/>


<div class="formContainer">
<c:if test="${currentLoggers != null}">
	<div class="section">
	<form action="update">
	<table class="table table-striped">
	<thead>
		<tr>
			<th>Category</th>
			<th>Current Level</th>
			<th>New Level</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input name="n" size="30"/></td>
			<td>
				<select name="l">
					<option value="ALL">ALL</option>
					<option value="TRACE">TRACE</option>
					<option value="DEBUG">DEBUG</option>
					<option value="INFO">INFO</option>
					<option value="WARN">WARN</option>
					<option value="ERROR">ERROR</option>
					<option value="FATAL">FATAL</option>
					<option value="OFF">OFF</option>
				</select>
			</td>
			<td>
				<input type="submit" value="Add"/>
			</td>
		</tr>
		<c:forEach items="${currentLoggers}" var="result">
			<tr>
				<td>${result.name}</td>
				<td>${result.level}</td>
				<td style="white-space:nowrap;">
					<c:if test="${result.level != 'ALL' }">
					<a href="update?n=${result.name }&l=ALL&r=${random }">ALL</a>
					</c:if>,
					<c:if test="${result.level != 'TRACE' }">
					<a href="update?n=${result.name }&l=TRACE&r=${random }">TRACE</a>
					</c:if>,
					<c:if test="${result.level != 'DEBUG' }">
					<a href="update?n=${result.name }&l=DEBUG&r=${random }">DEBUG</a>
					</c:if>,
					<c:if test="${result.level != 'INFO' }">
					<a href="update?n=${result.name }&l=INFO&r=${random }">INFO</a>
					</c:if>,
					<c:if test="${result.level != 'WARN' }">
					<a href="update?n=${result.name }&l=WARN&r=${random }">WARN</a>
					</c:if>,
					<c:if test="${result.level != 'ERROR' }">
					<a href="update?n=${result.name }&l=ERROR&r=${random }">ERROR</a>
					</c:if>,
					<c:if test="${result.level != 'FATAL' }">
					<a href="update?n=${result.name }&l=FATAL&r=${random }">FATAL</a>
					</c:if>
					<c:if test="${result.level != 'OFF' }">
					<a href="update?n=${result.name }&l=OFF&r=${random }">OFF</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	</div>	
</c:if>
</div>



</div>
</body>
</html>