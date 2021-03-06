<html>
<head>
	<meta name="decorator" content="clean-bootstrap" />
	<meta name="where" content="home"/>
	<title>Home</title>
</head>
<body>

<div id="content">


<div class="row-fluid">
	<div class="hero-unit span6">
	<h4>Admin Functions</h4>
	<p>Tagline</p>
	<ul class="pull-right nav">
		<li><a href="${ pageContext.request.contextPath }/admin/sourcesystem/list">Source Systems</a>						
		<li><a href="${ pageContext.request.contextPath }/admin/usergroup/list">User Groups</a>						
		<li><a href="${ pageContext.request.contextPath }/admin/responsibility/list">Responsibilities</a>						
	</ul>
	<div style="clear:both"></div>
	</div>


	<div class="hero-unit span6">
	<h4>Working</h4>
	<ul>
		<li>Message Ingest (IronMQ and Spring Integration)</li>
	</ul>
	<h4>TODO</h4>
	<ul>
		<li>Finish - add search highlighting to all search pages</li>
		<li>Setup nice error pages for 404 and 500</li>
		<li>Exception Ingest</li>
		<li>Exemption Processing</li>
		<li>Exemption Notification</li>
		<li>Complete Responsible Person Identification</li>
		<li>Implement Free Text search to find Responsibilities</li>
		<li>Bundling</li>
		<li>Task Creation from Bundles
			<p>(task type based on responsible person?)</p>
			<ul>
				<li>Data Task</li>
				<li>Pass through Exceptions (System Task?)</li>
			</ul>
		</li>
		<li>Stewardship by Source System?</li>
		<li>Task Assignment and notification</li>
		<li>Outlook Tasks? -- is it possible</li>
		<li>Get My Tasks</li>
		<li>Spring Web Flow for Task Handling?</li>
		<li>Task Completion Notification to MDM</li>
		<li>JMX Enable</li>
		
	</ul>
	<div style="clear:both"></div>
	</div>
	
</div>

</div>
</body>

</html>

