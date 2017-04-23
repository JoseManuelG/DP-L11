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

<spring:message code="chirp.from"/>:
<acme:mask text="${res.senderName}"/><br/>

<spring:message code="chirp.for"/>:
<acme:mask text="${res.recipientName}"/><br/>

<spring:message code="chirp.sendingMoment"/>:
<acme:mask text="${res.sendingMoment}"/><br/>

<spring:message code="chirp.subject"/>:
<acme:mask text="${res.subject}"/><br/>

<fieldset>
	<legend><spring:message code="chirp.text"/></legend>
	<acme:mask text="${res.text}"/>
</fieldset><br/>

<jstl:if test="${attachments.size()!=0}">
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="attachments" requestURI="${requestURI}" id="row" excludedParams="*">
		
		<spring:message code="chirp.attachments" var="varAttachments" />
		<display:column title="${varAttachments}" sortable="false">
			<a href="${row.url}">
				<jstl:choose>
					<jstl:when test="${row.name.equals('')}">
						${row.url}
					</jstl:when>
					<jstl:otherwise>
						<acme:mask text="${row.name}"/>
					</jstl:otherwise>
				</jstl:choose>
			</a>
		</display:column>
		
	</display:table>
</jstl:if>

<jstl:if test="${res.sender!=null}">
	<a href="chirp/customer/reply.do?chirpId=${res.id}">
		<spring:message code="chirp.reply"/>
	</a> | 
</jstl:if>

<a href="chirp/customer/forward.do?chirpId=${res.id}">
	<spring:message code="chirp.forward"/>
</a> | 

<a href="chirp/customer/delete.do?chirpId=${res.id}" onclick="return confirm('<spring:message code="confirm.delete" />')">
	<spring:message code="chirp.delete"/>
</a>