
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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="banners" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<spring:message code="banner.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="banner/administrator/view.do?bannerId=${row.id}">
				<spring:message	code="banner.view" />
			</a>
	</display:column>
	
	<spring:message code="banner.edit.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="banner/administrator/edit.do?bannerId=${row.id}">
				<spring:message	code="banner.edit" />
			</a>
	</display:column>
	
	<!-- Attributes -->
	
	<acme:column sorteable="false" code="banner.link" path="link"/>
	<acme:column sorteable="false" code="banner.image" path="image"/>
	
</display:table>

	<a href="banner/administrator/create.do">
				<spring:message	code="banner.create" />
			</a>