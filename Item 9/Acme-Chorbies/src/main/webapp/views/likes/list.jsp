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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="likes" requestURI="${requestURI}" id="row" excludedParams="*">


	<spring:message code="likes.liked" var="likedTitle"/>
	<display:column title="${likedTitle}">
		<a href="chorbi/chorbi/view.do?chorbiId=${row.liked.id}">
			<acme:mask text="${row.liked.name} ${row.liked.surname}"/>
		</a>
	</display:column>

	<acme:maskedColumn sorteable="true" code="likes.comment" text="${row.comment}"/>
	
	
	<display:column>
	
		<a href="likes/chorbi/view.do?likesId=${row.id}">
			<spring:message code="likes.view"/>
		</a>
	</display:column>

</display:table>


