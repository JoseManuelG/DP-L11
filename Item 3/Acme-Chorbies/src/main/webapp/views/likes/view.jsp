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

<spring:message code="likes.from"/>:
<a href="chorbi/chorbi/view.do?chorbiId=${likes.liker.id}">
       	<acme:mask text="${likes.liker.name} ${likes.liker.surname}"/><br/>
</a>

<spring:message code="likes.for"/>:
<a href="chorbi/chorbi/view.do?chorbiId=${likes.liked.id}">
       	<acme:mask text="${likes.liked.name} ${likes.liked.surname}"/><br/>
</a>

<spring:message code="likes.sendingMoment"/>:
<acme:mask text="${likes.moment}"/><br/>
<spring:message code="likes.stars"/>:
<acme:mask text="${likes.stars}"/><br/>


<fieldset>
	<legend><spring:message code="likes.comment"/></legend>
	<acme:mask text="${likes.comment}"/>
</fieldset><br/>
<jstl:if test="${aux}">
<a href="likes/chorbi/delete.do?likesId=${likes.id}" onclick="return confirm('<spring:message code="confirm.delete" />')">
	<spring:message code="likes.delete"/>
</a>
</jstl:if>