<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="events" requestURI="${requestURI}" id="row" excludedParams="*">
	
	<spring:message code="event.picture" var="titleName" />
	<display:column title="titleName">
	<acme:image url="${row.picture}"/>
	</display:column>

	<acme:maskedColumn sorteable="false" code="event.title" text="${row.title}"/>
	
	<acme:maskedColumn sorteable="false" code="event.description" text="${row.description}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.organisedMoment" text="${row.organisedMoment}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.seatsOffered" text="${row.seatsOffered}"/>
	
	<display:column>
		<a href="event/view.do?eventId=${row.id}">
			<spring:message code="event.view"/>
		</a>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
		<a href="event/manager/edit.do?eventId=${row.id}">
			<spring:message code="event.edit"/>
		</a>
	</display:column>
	<display:column>
		<a href="chirp/manager/broadcast.do?eventId=${row.id}">
			<spring:message code="event.chirp"/>
		</a>
	</display:column>
	</security:authorize>
</display:table>


<security:authorize access="hasRole('MANAGER')">
	<a href="event/manager/create.do">
		<spring:message code="event.new"/>
	</a>
</security:authorize>