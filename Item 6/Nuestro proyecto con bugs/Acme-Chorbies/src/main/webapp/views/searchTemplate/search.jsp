
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<fieldset>
	<form:form action="searchTemplate/chorbi/search.do" modelAttribute="search">
		<form:hidden path="id" />

		<!-- <acme:textbox code="searchTemplate.desiredRelationship" path="desiredRelationship"/>-->
		<form:label path="desiredRelationship">
		<spring:message code="searchTemplate.desiredRelationship" />
	</form:label>
    <form:select id="desiredRelationship" name="desiredRelationship" path="desiredRelationship">
    	<form:option value="all"	><spring:message code="searchTemplate.all" /></form:option>
    	<form:option value="love"><spring:message code="searchTemplate.relation.love" /></form:option>
    	<form:option value="activities"><spring:message code="searchTemplate.relation.activities" /></form:option>    	
    	<form:option value="friendship"><spring:message code="searchTemplate.relation.friendship" /></form:option>
    </form:select>
    
		<!--<acme:textbox code="searchTemplate.age" path="age"/>-->
	<!--  	<acme:textbox code="searchTemplate.genre" path="genre"/>-->
		<br/>
		<form:label path="age">
			<spring:message code="searchTemplate.age" />
		</form:label>	
		<form:input path="age"  type="number" />	
		<form:errors path="age" cssClass="error" />
		<br/>
		
		<form:label path="genre">
		<spring:message code="searchTemplate.genre" />
	</form:label>
	<form:select id="genre" name="genre" path="genre">
	<form:option value="all"	><spring:message code="searchTemplate.all" /></form:option>
    	<form:option value="man"><spring:message code="security.register.man" /></form:option>
    	<form:option value="woman"><spring:message code="security.register.woman" /></form:option>
    </form:select>
    
		<acme:textbox code="searchTemplate.keyword" path="keyword"/>
		<acme:textbox code="searchTemplate.coordinates.city" path="coordinates.city"/>
		<acme:textbox code="searchTemplate.coordinates.province" path="coordinates.province"/>
		<acme:textbox code="searchTemplate.coordinates.country" path="coordinates.country"/>
		<acme:textbox code="searchTemplate.coordinates.state" path="coordinates.state"/>
		
		
		
		
		
		<acme:submit name="save" code="searchTemplate.search"/>
	</form:form>
</fieldset>

<br/>

<jstl:if test="${!results.isEmpty()}">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="results" requestURI="${requestURI}" id="row" excludedParams="*">
	
		
	<display:column>
		<a href="chorbi/chorbi/view.do?chorbiId=${row.id}">
			<spring:message code="chorbi.view"/>
		</a>
	</display:column>
	
	<jstl:if test="${row.banned}">
		<jstl:set var="style" value="color: red;text-decoration:line-through"/>
	</jstl:if>
	
	<jstl:if test="${!row.banned}">
		<jstl:set var="style" value=""/>
	</jstl:if>

	<acme:maskedColumn sorteable="true" code="chorbi.name" text="${row.name}" highlight="${style}" />
	
	<acme:maskedColumn sorteable="true" code="chorbi.surname" text="${row.surname}" highlight="${style}" />
	
	<acme:maskedColumn sorteable="true" code="chorbi.description" text="${row.description}" highlight="${style}" />

	<acme:maskedColumn sorteable="true" code="chorbi.desiredRelationship" text="${row.desiredRelationship}" highlight="${style}" />

	<acme:maskedColumn sorteable="true" code="chorbi.birthDate" text="${row.birthDate}" highlight="${style}" />

	<acme:maskedColumn sorteable="true" code="chorbi.genre" text="${row.genre}" highlight="${style}" />



</display:table>

</jstl:if>