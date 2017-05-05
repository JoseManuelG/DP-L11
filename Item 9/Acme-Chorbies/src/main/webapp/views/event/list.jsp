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

<jstl:if test="${!sorted and !empty events}">
	<a href="${requestURI}?sorted=true">
		<spring:message code="event.sort"/>
	</a>
</jstl:if>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="events" requestURI="${requestURI}" id="row" excludedParams="*">
	

	<jstl:choose>
		<jstl:when test="${!empty eventsCloseToFinish and row[0].organisedMoment<now}">
			<jstl:set var="style" value="background-color: grey; color: white"/>
		</jstl:when>
		<jstl:when test="${!empty eventsCloseToFinish and eventsCloseToFinish.contains(row[0])}">
			<jstl:set var="style" value="background-color: green; color: white; font-size:150%"/>
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="style" value="background-color: white"/>
		</jstl:otherwise>
	</jstl:choose>
	
	
	<acme:maskedColumn sorteable="false" code="event.title" text="${row[0].title}" highlight="${style}"/>
	
	<acme:maskedColumn sorteable="false" code="event.description" text="${row[0].description}" highlight="${style}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.organisedMoment" text="${row[0].organisedMoment}" highlight="${style}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.seatsOffered" text="${row[0].seatsOffered}" highlight="${style}"/>
	
	<acme:maskedColumn  sorteable="false" code="event.free.seats" text="${row[1]}" highlight="${style}"/>
	
	<display:column style="${style}">
		<a href="event/view.do?eventId=${row[0].id}">
			<spring:message code="event.view"/>
		</a>
	</display:column>
	
	<jstl:if test="${requestURI eq 'event/manager/list.do' or requestURI eq 'event/manager/list.do?sorted=true' }">
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
		<a href="event/manager/edit.do?eventId=${row[0].id}">
			<spring:message code="event.edit"/>
		</a>
	</display:column>
	<display:column>
		<a href="chirp/manager/broadcast.do?eventId=${row[0].id}">
			<spring:message code="event.chirp"/>
		</a>
	</display:column>
	</security:authorize>
	</jstl:if>
</display:table>
<jstl:if test="${requestURI eq 'event/list/all.do' or requestURI eq 'event/list/all.do?sorted=true' }">
<spring:message code="event.list.legend"/>
</jstl:if>

<security:authorize access="hasRole('MANAGER')">
	<a href="event/manager/create.do">
		<spring:message code="event.new"/>
	</a>
</security:authorize>