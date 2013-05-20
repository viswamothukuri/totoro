<%@ include file="/WEB-INF/jsp/d.jsp" %>
<html>
<head>
	<title>JMX Console</title>
	<meta name="decorator" content="clean-bootstrap" />
	<meta name="where" content="support.jmx" />
	
	<style>
	#jmx {font-size:16px;}
	#jmx table {width:100%;margin:0px;}
	#jmx td { vertical-align:top; text-align:left;}
	#jmx ol { margin:0px; padding:0px }
	#jmx li {margin:0px:padding:0px;}
	#jmx h2,h3 {margin-top:15px;padding:0px;}
	#jmx .noticeBox { width:98%; margin:0px; margin:auto;}
	#jmx th { border:none; }
	#jmx .opdescription {font-size:0.8em;color:#999999;}
	</style>
	
	<script src="/totoro/r/js/jquery.editinplace.min.js"></script>
</head>
<body>
	

	<div id="content">

<%
javax.management.MBeanServer server = (javax.management.MBeanServer) request.getAttribute("server");
javax.management.ObjectName name = (javax.management.ObjectName) request.getAttribute("name");
String gname = name.toString();//.replaceAll("\"", "");
pageContext.setAttribute("gname",gname);
%>
<div id="jmx">
<div class="noticeBox noticeOk">
${hostname }:${address }
</div>
	<c:url value="/support/jmx" var="currentDomainUrl">
		<c:param name="n" value="${name.domain }:*"/>
	</c:url>

<h2>Domains (<a href="${currentDomainUrl }"><%= name.getDomain() %></a>)</h2>
<c:forEach items="${domains }" var="d" varStatus="s">
	<c:url value="/support/jmx" var="url">
		<c:param name="n" value="${d }:*"/>
	</c:url>
	<c:if test="${ s.index gt 0 }"> | </c:if>
	<a href="${url}">${d }</a>
</c:forEach>

<c:if test="${not empty names }">
<h3>Objects</h3>
<c:forEach items="${names }" var="n">
	<li>
		<c:url value="/support/jmx" var="url">
			<c:param name="n" value="${n }"/>
		</c:url>
		<a href="${url}">${n }</a>
	</li>
</c:forEach>
</c:if>

<c:if test="${not empty mbean }">
<h3>Attributes</h3>
<table  class="table table-striped">
	<thead>
		<tr>
			<th>Property Name</th>
			<th>Description</th>
			<th>Type</th>
			<th>Value</th>
		</tr>
	</thead>
	<c:forEach items="${mbean.attributes}" var="attr">
	<c:set var="attr" scope="request" value="${attr.name }"/>
	<c:if test="${ attr.readable }">
		<tr>
			<td style="font-weight:bold;">${ attr.name }</td>
			<td>
			<div class="opdescription">${attr.description }</div>
			</td>
			<td>
				<c:choose>
					<c:when test="${attr.type == '[Ljava.lang.String;' }">
						string[]			
					</c:when>
					<c:otherwise>
						${attr.type }
					</c:otherwise>
				</c:choose>
			</td> 
			<% try {request.setAttribute("val", server.getAttribute(name, (String) request.getAttribute("attr"))) ;} catch (Exception e) {	request.setAttribute("val", e.getMessage());}%>
			<td>
				<c:choose>
					<c:when test="${attr.type == '[Ljava.lang.String;' }">
						<c:choose>
						<c:when test="${fn:length(val) gt 0 }">
						<ol>
							<c:forEach items="${val }" var="v">
								<li>${v }</li>
							</c:forEach>
						</ol>
						</c:when>
						<c:otherwise>(null)</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="${attr.type == 'java.lang.String' or attr.type == 'int' or attr.type == 'long' or attr.type == 'boolean' }">
						<span id="${attr.name }">${val }</span>
						<c:if test="${attr.writable }">
							<img src="${ pageContext.request.contextPath}/r/images/edit.png" width="10"/>
							<c:url value="/support/jmx" var="url"/>
							<script>
								$("#${attr.name }").editInPlace({ url: '${url}', params: 'n=${gname}', saving_text : "Saving...", show_buttons: true });
							</script>
						</c:if>
					</c:when>
					<c:otherwise>
						${val }
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:if>
	</c:forEach>
</table>


<h3>Operations</h3>
<table class="table table-striped">
	<tr>
		<th>Operation((param)*):return type
		<div class="opdescription">description</div>
		</th>
	</tr>
	<c:if test="${empty operations }">
		<tr>
			<td colspan="2">none</td>
		</tr>
	</c:if>
	<c:forEach items="${operations}" var="op" varStatus="opstatus">
		<c:url value="/support/jmx/invoke" var="url"/>
		<tr id="tr_${opstatus.index }">
			<td colspan="2">
				<form id="form_${opstatus.index }" action="${url}" method="POST">
					<input type="hidden" name="n" value='${gname }'/>
					<input type="hidden" name="op" value="${op.name }"/>
					<input type="submit" class="btn btn-success" value="${op.name }"/>
					(
					
					<input type="hidden" value="${opstatus.index }" name="opindex"/>
					<c:forEach items="${op.signature }" var="p" varStatus="s">
						<c:if test="${s.index gt 0 }">,</c:if>
						<input type="text" size="6" name="p" placeholder="${p.type }"/>:${p.name}
						<input type="hidden" name="sig" value="${p.type }"/>
					</c:forEach>
					) : ${op.returnType }
					<div class="opdescription">${op.description }</div>
				</form>
				<script>
				    $("#form_${opstatus.index }").submit(function(e){
				    	e.preventDefault();
				        data = $("#form_${opstatus.index }").serialize();
				        url = $("#form_${opstatus.index }").attr("action");
				        $.post(url, data,function(data,status,xhr) {
				        	$("#tr_${opstatus.index }").after("<tr class='noticeBox noticeOk'><td colspan='2'>" + data + "</td></tr>").next("tr").delay(15000).fadeOut(15000).delay(100,function(){$(this).remove();});
						} ); 
				    });
				</script>	
			</td>
		</tr>
	</c:forEach>
</table>
</c:if>


</div>

</div>
</body>
</html>