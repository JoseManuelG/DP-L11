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

<acme:image url="${event.picture}"/>

<spring:message code="event.manager"/>:
<acme:mask text="${event.manager.name} ${event.manager.surname}"/>

<spring:message code="event.title"/>:
<acme:mask text="${event.title}"/>

<spring:message code="event.description"/>:
<acme:mask text="${event.description}"/>

<spring:message code="event.organisedMoment"/>:
<acme:mask text="${event.organisedMoment}"/>

<spring:message code="event.seatsOffered"/>:
<acme:mask text="${event.seatsOffered}"/>
	
<security:authorize access="hasRole('CHORBI')">
	<jstl:if test="${siteFree and !expired}">
		<jstl:if test="${!registered}">
			<a href="event/chorbi/register.do?eventId=${row.id}">
				<spring:message code="event.register"/>
			</a>
		</jstl:if>
		<jstl:if test="${registered}">
			<a href="event/chorbi/unregister.do?eventId=${row.id}">
				<spring:message code="event.unregister"/>
			</a>
		</jstl:if>
	</jstl:if>
</security:authorize>