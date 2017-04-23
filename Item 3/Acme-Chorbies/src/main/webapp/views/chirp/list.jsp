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
	name="chirps" requestURI="${requestURI}" id="row" excludedParams="*">

	<acme:maskedColumn sorteable="true" code="chirp.subject" text="${row.subject}"/>
	
	<acme:maskedColumn sorteable="true" code="chirp.from" text="${ row.senderName}"/>
	
	<acme:maskedColumn  sorteable="true" code="chirp.for" text="${row.recipientName}"/>
	
	<display:column>
		<a href="chirp/customer/view.do?chirpId=${row.id}">
			<spring:message code="chirp.view"/>
		</a>
	</display:column>

</display:table>

<a href="chirp/customer/write.do">
	<spring:message code="chirp.new"/>
</a>
