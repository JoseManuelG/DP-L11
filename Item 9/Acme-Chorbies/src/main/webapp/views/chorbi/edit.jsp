 <%--
 * login.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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

<form:form action="${requestURI}" modelAttribute="chorbiForm">
	
	<fieldset>
	<acme:textbox code="security.register.username" path="userAccount.username"/>
	
	<acme:password code="security.password" path="userAccount.password"/>
	
	<div>
		<form:label path="confirmPassword">
			<spring:message code="security.confirm.password" />
		</form:label>
		<form:password path="confirmPassword"/>
		<form:errors path="valid" cssClass="error" />
	</div>
    </fieldset>
    
	<fieldset>
    <acme:textbox code="security.register.name" path="name"/>
	
	<acme:textbox code="security.register.surname" path="surname"/>
	
	<acme:textbox code="security.register.email" path="email"/>
	
	<acme:textbox code="security.register.phone" path="phone"/>
	
	<acme:textbox code="security.register.picture" path="picture"/>
	
	<acme:textbox code="security.register.description" path="description"/>
	
	<form:label path="desiredRelationship">
		<spring:message code="security.register.desiredRelationship" />
	</form:label>		
	
	<form:select id="desiredRelationship" name="desiredRelationship" path="desiredRelationship">
    	<form:option value="love"><spring:message code="security.register.love" /></form:option>
    	<form:option value="activities"><spring:message code="security.register.activities" /></form:option>    	
    	<form:option value="friendship"><spring:message code="security.register.friendship" /></form:option>
    </form:select>
	<br/>
	<form:label path="genre">
		<spring:message code="security.register.genre" />
	</form:label>
	
	<form:select id="genre" name="genre" path="genre">
    	<form:option value="man"><spring:message code="security.register.man" /></form:option>
    	<form:option value="woman"><spring:message code="security.register.woman" /></form:option>
    </form:select>
	<br/>
	<acme:textbox code="security.register.birthDate" path="birthDate" placeholder="dd/mm/aaaa"/>
	
    </fieldset>
    
	<fieldset>
	<acme:textbox code="security.register.country" path="country"/>
	
	<acme:textbox code="security.register.province" path="province"/>

	<acme:textbox code="security.register.state" path="state"/>

	<acme:textbox code="security.register.city" path="city"/>
	
    </fieldset>
    
    <jstl:if test="${isNew}">
    	<a target="_blank" href="law/terms-conditions.do">
		<spring:message code="security.register.terms" />
		</a>
	
		<br/>
		
		<acme:checkbox code="security.register.AceptTerms" path="acepted"/>
	
    </jstl:if>
    
	<acme:submit code="security.register.save" name="save"/>
	
    <jstl:if test="${!isNew}">
	<acme:submit code="security.register.delete" name="delete"/>
    </jstl:if>
	
	<acme:cancel url="" code="security.register.cancel"/>
	
</form:form>