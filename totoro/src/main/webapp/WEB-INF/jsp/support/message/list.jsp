<%@ include file="/WEB-INF/jsp/d.jsp" %>
<html>
<head>
<title>Messages</title>
<meta name="decorator" content="clean-bootstrap" />
<meta name="where" content="support.messagelist" />
</head>
<body>

	<div id="content">
		<form:form commandName="messageSearchForm" cssClass="form-inline" method="POST">
				<form:label path="query">Search</form:label>
				<form:input path="query"/>
				<script>
				$(function() {
					highlightSearchResult($("#query").attr("value"));
				});
				</script>
				
			    <form:label path="source">Source</form:label>
			    <form:select path="source">
			    	<spring:message var="any" code="defaults.searchform.criteria.dropdown.any" />
			    	<form:option value="" label="${any }"/>
			    	<form:options items="${messageSearchForm.sourceOptions }" />
			    </form:select>
			    <form:label path="processed">Processed</form:label>
			    <form:select path="processed">
			    	<spring:message var="any" code="defaults.searchform.criteria.dropdown.any" />
			    	<form:option value="" label="${any }"/>
					<form:options items="${messageSearchForm.processedOptions }"/>
			    </form:select>
			    
			    <input type="submit" class="btn btn-search" value="Search"/>
			    <input type="button" class="btn btn-reset" value="Reset" onclick="$('#q').attr('value','');this.form.submit();"/>
		  		<!-- <a class="btn btn-success" href="${pageContext.request.contextPath }/admin/responsibility/new"><i class="icon-plus"></i> <fmt:message key="message.list.actions.addnew"/></a> -->
		
	

		<c:if test="${not empty messageSearchForm.results }">
		
		<div class="pagination">
			<div class="pagination pull-left">
			Total Count : <span class="label label-info">${ messageSearchForm.paging.totalCount}</span>
			</div>
			
			
			<c:if test="${messageSearchForm.paging.pageCount gt 1 }">
			<div class="pagination pagination-right">
				<ul><meta name="where" content="admin.message.list" />
				
				    <li class="${messageSearchForm.paging.page eq 0 ? 'disabled' : ''}"><a href="#">&laquo;</a></li>
					<c:forEach items="${messageSearchForm.paging.pages}" var="pg">
					<li class="${messageSearchForm.paging.page eq pg ? 'active' : ''}"><a href="#">${pg + 1}</a></li>
					</c:forEach>
					<li class="${messageSearchForm.paging.page eq (messageSearchForm.paging.pageCount - 1) ? 'disabled' : ''}"><a href="#">&raquo;</a></li>
				</ul>
			</div>
			</c:if>
		</div>
		
		
		<table class="table table-striped table-bordered table-condensed">
			<colgroup>
				<col />
				<col width="5%"/>
				<col  width="5%" />
				<col width="20%"/>
				<col width="10%" align="right"/>
			</colgroup>
			<thead>
				<tr>
					<th width="5%"><fmt:message key="message.list.th.source"/></th>
					<th><fmt:message key="message.list.th.status"/></th>
					<th width="50%"><fmt:message key="message.list.th.data"/></th>
					<th><fmt:message key="message.list.th.updater"/></th>
					<th class="p_actions"><fmt:message key="message.list.th.actions"/></th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${messageSearchForm.results }" var="r">
					<tr id="ss_${r.identifier }">
						<td class="">${r.source  }</td>
						<td class="p_deleted">
						<c:if test="${not r.processed }">
						<span class="label label-warning"><fmt:message key="message.list.td.status.notprocessed"/></span>
						</c:if>
						<c:if test="${ r.processed }">
						<span class="label label-success"><fmt:message key="message.list.td.status.processed"/></span>
							</c:if>
						</td>
						<td class="highlight-search-result">${r.data  }</td>
						<td>
							<fmt:message key="message.list.td.updated">
								<fmt:param value="${r.auditData.updatedBy }"/>
								<fmt:param value="${r.auditData.updated }"/>
							</fmt:message>
						</td>
						
						<td class="p_actions">
						    <a class="btn btn-mini" href="${pageContext.request.contextPath }/admin/responsibility/${r.identifier}"><i class="icon-pencil"></i> <fmt:message key="message.list.actions.edit"/></a>
						    <c:if test="${ not r.processed }">
						    	<a class="btn-danger btn btn-mini" onclick="$('#message-data').text('${r.identifier }');$('#reprocess-id').text('${r.identifier }');$('#reprocess-confirm').modal();"><i class="icon-trash"></i> <fmt:message key="message.list.actions.process"/></a>
						    </c:if>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
		</table>
		</c:if>

		</form:form>

		<c:if test="${empty messageSearchForm.results  }">
			<div class="alert alert-info">
				<fmt:message key="message.list.noresults">
					<fmt:param value="${param.q }"/>
				</fmt:message>
			</div>
		</c:if>





		<div class="modal hide fade" id="reprocess-confirm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>Are you sure?</h3>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to reprocess '<span id="message-data">this</span>' ?</p>
			</div>
			<span class="hide" id="reprocess-id"></span>
			<div class="modal-footer">
				<button class="btn btn-inverse" data-dismiss="modal">No</button> 
				<a href="#" onclick="document.location='${pageContext.request.contextPath }/support/message/' + $('#delete-responsibility-id').text() + '/reprocess';" class="btn btn-success">Yes, Reprocess It</a>
			</div>
		</div>
		
		
	</div>
</body>

</html>

