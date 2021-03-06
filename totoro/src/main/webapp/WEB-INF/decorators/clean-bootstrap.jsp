<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ include file="/WEB-INF/jsp/d.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Totoro - <decorator:title /></title>
<link href="https://www.railinc.com/railinc-theme/images/favicon.ico" rel="Shortcut Icon" />
	

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/r/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/r/css/jquery-ui-1.8.12.custom.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/r/css/standard.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/r/css/app.css" />


	<script src="${pageContext.request.contextPath }/r/js/jquery-1.7.1.min.js" type='text/javascript'></script>
	<script src="${pageContext.request.contextPath }/r/js/jquery-ui-1.8.17.custom.min.js" type='text/javascript'></script>
	<script src="${pageContext.request.contextPath }/r/js/jquery.hoverIntent.minified.js" type='text/javascript'></script>
	<script src="${pageContext.request.contextPath }/r/js/jquery.tablesorter.min.js" type='text/javascript'></script>
	<script src="${pageContext.request.contextPath }/r/js/jquery-ui-1.8.17.custom.min.js" type='text/javascript'></script>
	<script src="${pageContext.request.contextPath }/r/js/jquery.mustache-0.7.2.js" type='text/javascript'></script>
	<script src="${pageContext.request.contextPath }/r/js/mustache-0.7.2.js" type='text/javascript'></script>

<decorator:head />
</head>
<body>
	<!-- Header Begin -->
	<div id="rail-header">
		<div id="util-nav-wrapper">
			<ul id="util-nav">
				<c:if test="${not empty loggedUser }">		
					<li>
						<span id="rail-app-user">${loggedUser.userId } :</span> 
						<span id="rail-app-user-company">${loggedUser.employer }</span>
					</li>
					<li><a href="/rportal/web/csc">Launch Pad</a></li>
					<li><a href="/contactus">Contact Us</a></li>
					<li class="lastLK"><a href="${pageContext.request.contextPath }/logout">Sign Out</a></li>
				</c:if>
			</ul>

		</div>
		<a href="/" id="railinc-logo"><img
			src="${pageContext.request.contextPath }/r/images/logo.png"
			alt="Go to www.railinc.com" width="150" height="26" border="0" />
		</a>
		<c:url value="/" var="homeUrl"/>
		<div id="rail-app-name"><a href="${homeUrl }">Totoro</a></div>
		<div class="clear"></div>

	</div>

	<c:set var="where"><decorator:getProperty property="meta.where" /></c:set>
	<div id="rail-app-container">
		<div class="navbar">
			<div class="navbar-inner">
<!-- 				<a class="brand" href="#"><decorator:title/></a> -->
				<ul class="nav">
					<li class="${where.startsWith('home') ? 'active' : '' }"><a
						href="${pageContext.request.contextPath }/">Home</a></li>
					<li class="dropdown ${where.contains('admin') ? 'active' : '' }"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> Admin <b class="caret"></b> </a>
						<ul class="dropdown-menu">
							<li class="${where.matches('admin.sourcesystem.*') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/admin/sourcesystem/list">Source Systems</a>						
							<li class="${where.matches('admin.usergroup.*') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/admin/usergroup/list">User Groups</a>						
							<li class="${where.matches('admin.responsibility.*') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/admin/responsibility/list">Responsibilities</a>						
						</ul>
					</li>
					<li class="dropdown ${where.matches('support.*') ? 'active' : '' }"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> Support <b class="caret"></b> </a>
						<ul class="dropdown-menu">
							<li class="${where.matches('support.sendmessage.*') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support/message/send">Send a Message</a>						
							<li class="${where.matches('support.messagelist.*') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support/message">Messages</a>
							
							<li class="${where.matches('support.home') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support">Support Home</a>
							<li class="${where.matches('support.properties') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support/properties">JVM Properties</a>
							<li class="${where.matches('support.snoop') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support/snoop">Request Snoop</a>

							<li class="${where.matches('support.logging') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support/logging">Logging</a>
							<li class="${where.matches('support.jmx') ? 'active' : '' }"><a href="${ pageContext.request.contextPath }/support/jmx">JMX Console</a>
						</ul>
					</li>
				</ul>
			</div>
		</div>

		<div id="content-container">

			<decorator:getProperty property="div.breadcrumbs" />

		<spring:hasBindErrors name="flash">
			<div class="alert alert-block alert-info">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <form:errors path="flash"/>
			</div>
		</spring:hasBindErrors>
			


			<decorator:getProperty property="div.content" default="" />

			<div class="clear"></div>

		</div>
	</div>


	<!-- App Area End -->
	<div class="clear"></div>
	<!-- Footer Begin -->


	<div id="rail-footer" class="navbar navbar-fixed-bottom" style="">
		<ul class="nav" style="display:none;" id="footer-nav">
			<li><a href="/legal" class="first">legal notices</a>
			</li>
			<li><a href="/privacy">privacy rights</a>
			</li>
			<li><a href="/terms">terms of service</a>
			</li>
			<li><a href="/contactus">contact us</a>
			</li>
		</ul>
	</div>

	<decorator:getProperty property="div.post-content-js"/>
	<script src="${pageContext.request.contextPath }/r/bootstrap/js/bootstrap.min.js" type='text/javascript'></script>
	<script>
	String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); }
	$(function() {
		$(".btn-search").addClass("btn-inverse");
		$(".btn-add-new").addClass("btn-success");
		$("#rail-footer").hover(function() {
			  $(this).find('#footer-nav').stop(true, true).slideToggle();
			}, function() {
				  $(this).find('#footer-nav').stop(true, true).slideToggle();
			});
	});
	function highlightSearchResult(term) {
		if (term === null || term.length == 0) {
			return;
		}
		$(".highlight-search-result").each(function(idx,element) {
			var el = $(this);
			var t = el.text();
			var pattern = new RegExp("("+term+")", "ig");
			t = t.replace(pattern,"<span class='label label-info'>$1</span>");
			el.html(t);
		});
		
	}
	</script>	
</body>
</html>

