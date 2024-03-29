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

<form:form action="configuration/administrator/edit.do" modelAttribute="configurationForm">

	<acme:textbox code="configuration.hours" path="hours"/>
	<acme:textbox code="configuration.minutes" path="minutes"/>
	<acme:textbox code="configuration.seconds" path="seconds"/>
	
	<acme:textbox code="configuration.chorbiFee" path="chorbiFee"/>
	<acme:textbox code="configuration.managerFee" path="managerFee"/>
	
	<acme:submit code="configuration.save" name="save" />
	<acme:cancel code="configuration.cancel" url="/configuration/administrator/view.do" />
	
</form:form>