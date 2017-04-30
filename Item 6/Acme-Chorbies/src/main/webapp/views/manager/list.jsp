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
	name="managers" requestURI="${requestURI}" id="row" excludedParams="*">

	<acme:maskedColumn sorteable="true" code="manager.name" text="${row.name}" highlight="${style}" />
	
	<acme:maskedColumn sorteable="true" code="manager.surname" text="${row.surname}" highlight="${style}" />
	
	<acme:column sorteable="true" code="manager.email" path="email"/>
	
	<acme:column sorteable="true" code="manager.phone" path="phone"/>
	
	<acme:column sorteable="true" code="manager.company" path="company"/>
	
	<acme:column sorteable="true" code="manager.VAT" path="VAT"/>
	
	<acme:column sorteable="true" code="manager.chargedFee" path="chargedFee"/>



</display:table>
