<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p>
<p><a href="${banner.link }"><img src="${banner.image}" /></a></p> 

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="tweets" requestURI="${requestURI}" id="row" excludedParams="*">
	
	<!-- Action links -->
	<jstl:set var = "link" value = "https://twitter.com/${row.fromUser}/status/${row.id}"/>
	<display:column>
		<a href="<jstl:out value="${link}"></jstl:out>"><jstl:out value="URL"></jstl:out></a>
	</display:column>
	
	<!-- Attributes -->
	
	<acme:column sorteable="false" code="tweet.moment" path="createdAt"/>
	<acme:column sorteable="false" code="tweet.text" path="text"/>
	
</display:table>