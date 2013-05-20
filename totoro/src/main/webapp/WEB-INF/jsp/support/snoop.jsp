<%@ include file="/WEB-INF/jsp/d.jsp" %>
<html>
<head>
<title>Snoop</title>
<meta name="decorator" content="clean-bootstrap" />
<meta name="where" content="support.snoop" />
</head>
<body>
	

	<div id="content">
		<div class="container-fluid">
			<div class="row-fluid">
					<ul class="nav nav-list span2 well">
						<li><a href="#basics">Basics</a></li>
						<li><a href="#cookies">Cookies</a></li>
						<li><a href="#headers">Headers</a></li>
					</ul>
					<script>
					$(function(){
						$(".nav-list").delegate("a","click",function(event) {
							$(".nav-list li").removeClass("active");
							$(this).parent().addClass("active");
						});
					});
					</script>
				<div class="span10">
							
									<section id="basics">	
									<h3>Basics</h3>
									<table id="sortableTable" class="table table-striped">
										<thead>
											<tr>
												<th class="col1">Property Name</th>
												<th class="col2">Property Value</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>Local Name</td>
												<td><%=request.getLocalName()%></td>
											</tr>
											<tr>
												<td>Local Port</td>
												<td><%=request.getLocalPort()%></td>
											</tr>
											<tr>
												<td>Remote Address</td>
												<td><%=request.getRemoteAddr()%></td>
											</tr>
											<tr>
												<td>Remote Port</td>
												<td><%=request.getRemotePort()%></td>
											</tr>
											<tr>
												<td>Remote Host</td>
												<td><%=request.getRemoteHost()%></td>
											</tr>
											<tr>
												<td>Remote User</td>
												<td><%=request.getRemoteUser()%></td>
											</tr>
										</tbody>
									</table>
							</section>
							<section id="cookies">
									<h3>Cookies</h3>
									<table id="sortableTable" class="table table-striped">
										<thead>
											<tr>
												<th class="col1">Domain</th>
												<th class="col2">Path</th>
												<th class="col3">Name</th>
												<th class="col4">Value</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="<%=request.getCookies()%>" var="c">
												<tr>
													<td>${c.domain}</td>
													<td>${c.path }</td>
													<td>${c.name }</td>
													<td>${c.value }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
							</section>
							<section id="headers">
									<h3>Headers</h3>
									<table id="sortableTable" class="table table-striped">
										<thead>
											<tr>
												<th class="col1">Property Name</th>
												<th class="col2">Property Value</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${header}" var="n">
												<tr>
													<td>${n.key}</td>
													<td>${n.value}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									</section>
				</div>
			</div>
		</div>
									
</div>
