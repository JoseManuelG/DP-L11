<%--
 *
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="event/manager/edit.do" modelAttribute="event">

	<form:hidden path="id" />
	
	<acme:textbox code="event.title" path="title"/>
	<acme:textbox code="event.organisedMoment" path="organisedMoment" placeholder="dd/mm/yyyy hh:MM"/>
	<acme:textbox code="event.description" path="description"/>
	<acme:textbox code="event.picture" path="picture"/>
	<acme:textbox code="event.seatsOffered" path="seatsOffered"/>
	<br>

	<acme:submit code="event.save" name="save" />
	<jstl:if test="${event.id != 0}">
		<acme:submit name="delete" code="event.delete"/>
	</jstl:if>
	<acme:cancel code="event.cancel" url="/event/manager/list.do" />
	
</form:form>