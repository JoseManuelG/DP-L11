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

<jsp:useBean id="now" class="java.util.Date" />

<jstl:if test="${!sorted}">
	<a href="${requestURI}?sorted=true">
		<spring:message code="event.sort"/>
	</a>
</jstl:if>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="events" requestURI="${requestURI}" id="row" excludedParams="*">
	

	<jstl:choose>
		<jstl:when test="${!empty eventsCloseToFinish and row.organisedMoment<now}">
			<jstl:set var="style" value="color: grey"/>
		</jstl:when>
		<jstl:when test="${!empty eventsCloseToFinish and eventsCloseToFinish.contains(row)}">
			<jstl:set var="style" value="color: green; font-size:150%"/>
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="style" value=""/>
		</jstl:otherwise>
	</jstl:choose>
	
	
	<acme:maskedColumn sorteable="false" code="event.title" text="${row.title}" highlight="${style}"/>
	
	<acme:maskedColumn sorteable="false" code="event.description" text="${row.description}" highlight="${style}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.organisedMoment" text="${row.organisedMoment}" highlight="${style}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.seatsOffered" text="${row.seatsOffered}" highlight="${style}"/>
	
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