<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="chirp/manager/broadcast.do" modelAttribute="chirpBroadcastForm">

		<form:hidden path="event" />

		<acme:textbox code="chirp.subject" path="subject" />
		<acme:textbox code="chirp.text" path="text" />

		<br />
	
	<jstl:forEach items="${chirpBroadcastForm.attachments}" var="attachment"
		varStatus="i">
		<acme:textbox code="chirp.attachment.name"
			path="attachments[${i.index}].name" />
		<acme:textbox code="chirp.attachment.url"
			path="attachments[${i.index}].url" />
	</jstl:forEach>

	<acme:submit code="chirp.save" name="save" />

	<acme:submit code="chirp.addAttachment" name="addAttachment" />

	<jstl:if test="${chirpBroadcastForm.attachments.size()>0}">
		<acme:submit code="chirp.removeAttachment" name="removeAttachment" />
	</jstl:if>

	<acme:cancel code="chirp.cancel" url="" />
</form:form>