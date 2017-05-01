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
	name="likes" requestURI="${requestURI}" id="row" excludedParams="*">
	
		
	<display:column>
		<a href="chorbi/chorbi/view.do?chorbiId=${row.liker.id}">
			<spring:message code="chorbi.view"/>
		</a>
	</display:column>
	
	
	
	
	<jstl:if test="${row.liker.banned}">
		<jstl:set var="style" value="color: red;text-decoration:line-through"/>
	</jstl:if>
	
	<jstl:if test="${!row.liker.banned}">
		<jstl:set var="style" value=""/>
	</jstl:if>
	

	<acme:maskedColumn sorteable="true" code="chorbi.name" text="${row.liker.name}" highlight="${style}" />
	
	<acme:maskedColumn sorteable="true" code="chorbi.surname" text="${row.liker.surname}" highlight="${style}" />
	
	<acme:maskedColumn sorteable="true" code="chorbi.description" text="${row.liker.description}" highlight="${style}" />

	<acme:maskedColumn sorteable="true" code="chorbi.desiredRelationship" text="${row.liker.desiredRelationship}" highlight="${style}" />

	<acme:maskedColumn sorteable="true" code="chorbi.birthDate" text="${row.liker.birthDate }" highlight="${style}" />

	<acme:maskedColumn sorteable="true" code="chorbi.genre" text="${row.liker.genre}" highlight="${style}" />



</display:table>

<p><spring:message  code="chorbi.bantip" /></p>

