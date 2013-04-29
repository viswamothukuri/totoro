<%@ include file="/WEB-INF/jsp/d.jsp" %>
<html>
<head>
<title>Responsibilities</title>
<meta name="decorator" content="clean-bootstrap" />
<meta name="where" content="admin.responsibility.list" />
</head>
<body>

	<div id="content">
		<form:form commandName="responsibilitysearch" cssClass="form-inline" method="POST">
				<form:label path="query">Search</form:label>
				<form:input path="query"/>
				
			    <form:label path="criteria.sourceSystem">Source System</form:label>
			    <form:select path="criteria.sourceSystem">
			    	<form:option value="" label="- Any -"/>
			    	<form:options items="${sourceSystems }" itemValue="identifier" itemLabel="name"/>
			    </form:select>
			    <form:label path="criteria.personType">Type</form:label>
			    <form:select path="criteria.personType">
			    	<form:option value="" label="- Any -"/>
					<form:options items="${personTypes }"/>
			    </form:select>
			    
			    <input type="submit" class="btn" value="Search"/>
			    <input type="button" class="btn" value="Reset" onclick="$('#q').attr('value','');this.form.submit();"/>
		  		<a class="btn btn-success" href="${pageContext.request.contextPath }/a/admin/responsibility/new"><i class="icon-plus"></i> <fmt:message key="responsibility.list.actions.addnew"/></a>
		
	
		<c:if test="${empty responsibilitysearch.results }">
			<div class="alert alert-info">
				<fmt:message key="responsibility.list.noresults">
					<fmt:param value="${param.q }"/>
				</fmt:message>
			</div>
		</c:if>

		<c:if test="${not empty responsibilitysearch.results }">
		
		<c:if test="${responsibilitysearch.paging.pageCount gt -1 }">
		<div class="pagination">
			<ul>
			    <li class="${responsibilitysearch.paging.page eq 0 ? 'disabled' : ''}"><a href="#">&laquo;</a></li>
				<c:forEach items="${responsibilitysearch.paging.pages}" var="pg">
				<li class="${responsibilitysearch.paging.page eq pg ? 'active' : ''}"><a href="#">${pg + 1}</a></li>
				</c:forEach>
				<li class="${responsibilitysearch.paging.page eq (responsibilitysearch.paging.pageCount - 1) ? 'disabled' : ''}"><a href="#">&raquo;</a></li>
			</ul>
		</div>
		</c:if>
		
		
		<table class="table table-striped table-bordered table-condensed">
			<colgroup>
				<col />
				<col />
				<col />
				<col />
				<col width="20%" align="right"/>
			</colgroup>
			<thead>
				<tr>
					<th class="p_id"><fmt:message key="responsibility.list.th.id"/></th>
					<th class="p_name"><fmt:message key="responsibility.list.th.name"/></th>
					<th class="p_deleted"><fmt:message key="responsibility.list.th.status"/></th>
					<th class=""><fmt:message key="responsibility.list.th.updater"/></th>
					<th></th>
					<th></th>
					<th class="p_actions"><fmt:message key="responsibility.list.th.actions"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${responsibilitysearch.results }" var="r">
					<tr id="ss_${r.id }" class="${r.deleted ? 'warning' : '' }">
						<td class="">${r.sourceSystem.name  }</td>
						<td class="">${r.ruleNumber }</td>
						<td class="">${r.personType.toString() }</td>
						<td class="p_deleted">
						<c:if test="${r.deleted }">
						<span class="label label-warning"><fmt:message key="responsibility.list.td.status.deleted"/></span>
						</c:if>
						<c:if test="${ not r.deleted }">
						<span class="label label-success"><fmt:message key="responsibility.list.td.status.notdeleted"/></span>
							</c:if>
						</td>
						<td>
						</td>
						<td>
							<fmt:message key="responsibility.list.td.updated">
								<fmt:param value="${r.auditData.updatedBy }"/>
								<fmt:param value="${r.auditData.updated }"/>
							</fmt:message>
						</td>
						
						<td class="p_actions">
						    <c:if test="${ r.deleted }">
						    	<a class="btn btn-mini" onclick="$('#undelete-responsibility-name').text('${r.ruleNumber }');$('#undelete-responsibility-id').text('${r.id }');$('#undelete-confirm').modal();"><i class="icon-retweet"></i> <fmt:message key="responsibility.list.actions.undelete"/></a>
						    </c:if>
						    <c:if test="${ not r.deleted }">
							    <a class="btn btn-mini" href="${pageContext.request.contextPath }/a/admin/responsibility/${r.id}"><i class="icon-pencil"></i> <fmt:message key="responsibility.list.actions.edit"/></a>
						    	<a class="btn-danger btn btn-mini" onclick="$('#delete-responsibility-name').text('${r.ruleNumber }');$('#delete-responsibility-id').text('${r.id }');$('#delete-confirm').modal();"><i class="icon-trash"></i> <fmt:message key="responsibility.list.actions.delete"/></a>
						    </c:if>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
		</table>
		</c:if>

		</form:form>






		<div class="modal hide fade" id="delete-confirm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>Are you sure?</h3>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to delete '<span id="delete-responsibility-name">this</span>' ?</p>
			</div>
			<span class="hide" id="delete-responsibility-id"></span>
			<div class="modal-footer">
				<button class="btn btn-inverse" data-dismiss="modal" aria-hidden="true">No</button> 
				<a href="#" onclick="document.location='${pageContext.request.contextPath }/a/admin/responsibility/' + $('#delete-responsibility-id').text() + '/delete';" class="btn btn-danger">Yes, Delete It</a>
			</div>
		</div>
		<div class="modal hide fade" id="undelete-confirm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>Are you sure?</h3>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to delete '<span id="undelete-responsibility-name">this</span>' ?</p>
			</div>
			<span class="hide" id="undelete-responsibility-id"></span>
			<div class="modal-footer">
				<button class="btn btn-inverse" data-dismiss="modal" aria-hidden="true">No</button> 
				<a href="#" onclick="document.location='${pageContext.request.contextPath }/a/admin/responsibility/' + $('#undelete-responsibility-id').text() + '/undelete';" class="btn btn-danger">Yes, Delete It</a>
			</div>
		</div>
	</div>
</body>

</html>

