<%@ include file="/WEB-INF/jsp/d.jsp" %>
<html>
<head>
<meta name="decorator" content="clean-bootstrap" />
<meta name="where" content="admin.responsibility.new" />
<title>New User Group</title>
</head>
<body>

	<div id="content">
		
	
		<form:form commandName="responsibility" cssClass="form-horizontal well">


<fieldset>
    <legend>Basic Information</legend>
		


			<c:set var="cssClass"><spring:bind path="responsibility.sourceSystem"><c:if test="${status.error}">error</c:if></spring:bind></c:set>
			<div class="control-group ${cssClass }">
				<form:label path="id" cssErrorClass="control-label" cssClass="control-label"><fmt:message key="responsibility.form.label.sourceSystem" /></form:label>
				<div class="controls">
					<form:select path="sourceSystem" cssErrorClass="error">
				    	<form:options items="${sourceSystems }" itemValue="identifier" itemLabel="name"/>
		    		</form:select>
					<form:errors element="span" cssClass="help-inline" path="sourceSystem"/>
				</div>
			</div>


			<c:set var="cssClass"><spring:bind path="responsibility.ruleNumber"><c:if test="${status.error}">error</c:if></spring:bind></c:set>
			<div class="control-group ${cssClass }">
				<form:label path="ruleNumber" cssClass="control-label"><fmt:message key="responsibility.form.label.ruleNumber"/></form:label>
			
				<div class="controls">
					<form:input path="ruleNumber" placeholder="Rule Number" cssErrorClass="input-small error" cssClass="input-small"  />
					<form:errors element="span" cssClass="help-inline" path="ruleNumber"/>
				</div>
			</div>

		
			    
			<c:set var="cssClass"><spring:bind path="responsibility.personType"><c:if test="${status.error}">error</c:if></spring:bind></c:set>
			<div class="control-group ${cssClass }">
				<form:label path="id" cssErrorClass="control-label" cssClass="control-label"><fmt:message key="responsibility.form.label.personType" /></form:label>
				<div class="controls">
					<form:select path="personType" cssErrorClass="error">
						<form:options items="${personTypes }"/>
		    		</form:select>
					<form:errors element="span" cssClass="help-inline" path="personType"/>
				</div>
			</div>


			    
		
			<c:set var="cssClass"><spring:bind path="responsibility.person"><c:if test="${status.error}">error</c:if></spring:bind></c:set>
			<div class="control-group ${cssClass }">
				<form:label path="id" cssErrorClass="control-label" cssClass="control-label"><fmt:message key="responsibility.form.label.person" /></form:label>
				<div class="controls">
					<form:input path="person" placeholder="" cssErrorClass="input-xlarge error" cssClass="input-xlarge" />
					<form:errors element="span" cssClass="help-inline" path="person"/>
				</div>
			</div>

			
				
			

</fieldset>			

			


			
			<div class="form-actions">
				<button type="submit" class="btn btn-primary" name="_save"><fmt:message key="responsibility.form.button.save"/></button>
				<button type="submit" class="btn btn-inverse" name="_cancel"><fmt:message key="responsibility.form.button.cancel"/></button>
			</div>

		</form:form>
		
		<script>
		$(function(){ 
			$("input[type=text]").each(function(idx,el) {
				if (!$(el).hasClass("error")) {
					$(el).tooltip({placement:'right'}); 
				}
			});
		});
		
		</script>
	</div>
</body>

</html>

