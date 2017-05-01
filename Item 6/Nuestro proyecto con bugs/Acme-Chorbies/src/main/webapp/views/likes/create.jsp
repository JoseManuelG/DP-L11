
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<form:form action="likes/chorbi/create.do" modelAttribute="likes">

		<form:hidden path="liked" />

	
	<br />
	
	<acme:textbox code="likes.comment" path="comment"/>
	<br />
	<form:label path="stars">
		<spring:message code="likes.stars" />:
	</form:label>
	<form:input type="number" step="any" path="stars"/>
	<form:errors path="stars" cssClass="error" />
	<br/>	
		
	<acme:submit name="save" code="likes.save"/>
	
	<acme:cancel url='${requestURI}' code="likes.cancel"/>
	
	<br>
</form:form>
