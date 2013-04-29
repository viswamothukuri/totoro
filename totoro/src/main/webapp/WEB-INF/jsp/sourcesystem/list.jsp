<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Source Systems</title>
<meta name="decorator" content="clean-bootstrap" />
<meta name="where" content="admin.sourcesystem.list" />

</head>
<body>

	<div id="content">
	
		<form class="form-inline">
			    <input type="text" class="span2" name="q" id="q" value="${param.q }" />
			    <input type="submit" class="btn" value="Search"/>
			    <input type="button" class="btn" value="Reset" onclick="$('#q').attr('value','');this.form.submit();"/>
		  	<a class="btn btn-success" href="${pageContext.request.contextPath }/a/admin/sourcesystem/new"><i class="icon-plus"></i> <fmt:message key="sourcesystem.list.actions.addnew"/></a>
		</form>

		<c:if test="${empty results }">
			<div class="alert alert-info">
				<fmt:message key="sourcesystem.list.noresults">
					<fmt:param value="${param.q }"/>
				</fmt:message>
			</div>
		</c:if>

		<c:if test="${not empty results }">
		<table class="table table-striped table-bordered table-condensed">
			<colgroup>
				<col />
				<col />
				<col />
				<col />
				<col />
				<col width="20%" align="right"/>
			</colgroup>
			<thead>
				<tr>
					<th class="p_id"><fmt:message key="sourcesystem.list.th.id"/></th>
					<th class="p_name"><fmt:message key="sourcesystem.list.th.name"/></th>
					<th class="p_deleted"><fmt:message key="sourcesystem.list.th.status"/></th>
					<th class=""><fmt:message key="sourcesystem.list.th.updater"/></th>
					<th class="p_actions"><fmt:message key="sourcesystem.list.th.actions"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${results }" var="r">
					<tr id="ss_${r.identifier }" class="${r.deleted ? 'warning' : '' }">
						<td class="p_id">${r.identifier }
						</td>
						<td class="p_name">${r.name }</td>
						<td class="p_deleted">
						<c:if test="${r.deleted }">
						<span class="label label-warning"><fmt:message key="sourcesystem.list.td.status.deleted"/></span>
						</c:if>
						<c:if test="${ not r.deleted }">
						<span class="label label-success"><fmt:message key="sourcesystem.list.td.status.notdeleted"/></span>
						</c:if>
						</td>
						<td>
							<fmt:message key="sourcesystem.list.td.updated">
								<fmt:param value="${r.auditData.updatedBy }"/>
								<fmt:param value="${r.auditData.updated }"/>
							</fmt:message>
						</td>
						
						<td class="p_actions">
						    <c:if test="${ r.deleted }">
						    	<a class="btn btn-mini" onclick="$('#undelete-sourcesystem-name').text('${r.name }');$('#undelete-sourcesystem-id').text('${r.identifier }');$('#undelete-confirm').modal();"><i class="icon-retweet"></i> <fmt:message key="sourcesystem.list.actions.undelete"/></a>
						    </c:if>
						    <c:if test="${ not r.deleted }">
							    <a class="btn btn-mini" href="${pageContext.request.contextPath }/a/admin/sourcesystem/${r.identifier}"><i class="icon-pencil"></i> <fmt:message key="sourcesystem.list.actions.edit"/></a>
						    	<a class="btn-danger btn btn-mini" onclick="$('#delete-sourcesystem-name').text('${r.name }');$('#delete-sourcesystem-id').text('${r.identifier }');$('#delete-confirm').modal();"><i class="icon-trash"></i> <fmt:message key="sourcesystem.list.actions.delete"/></a>
						    </c:if>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
		</table>
		</c:if>







		<div class="modal hide fade" id="delete-confirm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>Are you sure?</h3>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to delete '<span id="delete-sourcesystem-name">this</span>' ?</p>
			</div>
			<span class="hide" id="delete-sourcesystem-id"></span>
			<div class="modal-footer">
				<button class="btn btn-inverse" data-dismiss="modal" aria-hidden="true">No</button> 
				<a href="#" onclick="document.location='${pageContext.request.contextPath }/a/admin/sourcesystem/' + $('#delete-sourcesystem-id').text() + '/delete';" class="btn btn-danger">Yes, Delete It</a>
			</div>
		</div>
		<div class="modal hide fade" id="undelete-confirm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>Are you sure?</h3>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to delete '<span id="undelete-sourcesystem-name">this</span>' ?</p>
			</div>
			<span class="hide" id="undelete-sourcesystem-id"></span>
			<div class="modal-footer">
				<button class="btn btn-inverse" data-dismiss="modal" aria-hidden="true">No</button> 
				<a href="#" onclick="document.location='${pageContext.request.contextPath }/a/admin/sourcesystem/' + $('#undelete-sourcesystem-id').text() + '/undelete';" class="btn btn-danger">Yes, Delete It</a>
			</div>
		</div>
	</div>
</body>

</html>

