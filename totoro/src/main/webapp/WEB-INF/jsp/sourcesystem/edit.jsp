<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta name="decorator" content="clean-bootstrap" />
<title>Edit Source System</title>
<meta name="where" content="admin.sourcesystem.edit" />

</head>
<body>

	<div id="content">
		<form:form commandName="sourcesystem" cssClass="form-horizontal well">

<fieldset>
    <legend>Source System</legend>

		
			<c:set var="cssClass"><spring:bind path="sourcesystem.id"><c:if test="${status.error}">error</c:if></spring:bind></c:set>
			<div class="control-group ${cssClass }">
				<form:label path="id" cssErrorClass="" cssClass="control-label"><fmt:message key="sourcesystem.form.label.id" /></form:label>
				<div class="controls">
					<form:input path="id" readonly="true"/>
					<form:errors element="span" cssClass="help-inline" path="id"/>
				</div>
			</div>

			<c:set var="cssClass"><spring:bind path="sourcesystem.name"><c:if test="${status.error}">error</c:if></spring:bind></c:set>
			<div class="control-group ${cssClass }">
				<form:label path="name" cssClass="control-label"><fmt:message key="sourcesystem.form.label.name"/></form:label>
			
				<div class="controls">
					<form:input path="name" placeholder="Name"/>
					<form:errors element="span" cssClass="help-inline" path="name"/>
				</div>
			</div>
	</fieldset>

	<%@ include file="/WEB-INF/jsp/_audit_data_fieldset.jsp" %>

			
			<div class="form-actions">
				<button type="submit" class="btn btn-primary" name="_save"><fmt:message key="sourcesystem.form.button.save"/></button>
				<button type="submit" class="btn btn-inverse" name="_cancel"><fmt:message key="sourcesystem.form.button.cancel"/></button>
			</div>

		</form:form>
	</div>
</body>

</html>

