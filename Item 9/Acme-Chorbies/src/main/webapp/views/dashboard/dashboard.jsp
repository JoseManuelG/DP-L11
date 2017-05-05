
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
	
	
	<spring:message code="dashboard.actorName" var="actorName" />
	<spring:message code="dashboard.city" var="city" />
	<spring:message code="dashboard.country" var="country" />
	<spring:message code="dashboard.min" var="min" />
	<spring:message code="dashboard.max" var="max" />
	<spring:message code="dashboard.avg" var="avg" />
	<spring:message code="dashboard.fee" var="fee" />
	<spring:message code="dashboard.events" var="events" />

	<spring:message code="dashboard.chorbiesGroupedByCity" />:<br>
	<display:table pagesize="5" class="displaytag1" name="chorbiesGroupedByCity"
		requestURI="${requestURI}" id="row" uid="chorbiesGroupedByCity" excludedParams="*">
		
	    <display:column title="${city}">
	   	  <jstl:out value="${chorbiesGroupedByCity[1]}"/>
	    </display:column>
	    
	     <display:column >
	   	  <jstl:out value="${chorbiesGroupedByCity[0]}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.chorbiesGroupedByCountry" />:<br>
	<display:table pagesize="5" class="displaytag1" name="chorbiesGroupedByCountry"
		requestURI="${requestURI}" id="row" uid="chorbiesGroupedByCountry" excludedParams="*">
		
	    <display:column title="${country}">
	   	  <jstl:out value="${chorbiesGroupedByCountry[1]}"/>
	    </display:column>
	    
	     <display:column >
	   	  <jstl:out value="${chorbiesGroupedByCountry[0]}"/>
	    </display:column>
	</display:table>
	<br>

	<spring:message code="dashboard.minumumChorbiAge" />: <jstl:out value="${minumumChorbiAge}"/><br/>
	<spring:message code="dashboard.maximumChorbiAge" />: <jstl:out value="${maximumChorbiAge}"/><br/>
	<spring:message code="dashboard.averageChorbiAge" />: <jstl:out value="${averageChorbiAge}"/><br/>
	<spring:message code="dashboard.ratioOfNoCCAndInvalidCCVersusValidCC" />: <jstl:out value="${ratioOfNoCCAndInvalidCCVersusValidCC}"/><br/>
	
	<spring:message code="dashboard.ratioActivitiesSearch" />: <jstl:out value="${ratioActivitiesSearch}"/><br/>
	<spring:message code="dashboard.ratioFriendshipSearch" />: <jstl:out value="${ratioFriendshipSearch}"/><br/>
	<spring:message code="dashboard.ratioLoveSearch" />: <jstl:out value="${ratioLoveSearch}"/><br/>
	
	<spring:message code="dashboard.chorbiesOrderedByLikes" />:<br>
	<display:table pagesize="5" class="displaytag1" name="chorbiesOrderedByLikes"
		requestURI="${requestURI}" id="row" uid="chorbiesOrderedByLikes" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${chorbiesOrderedByLikes.name}"/>
	   	  <jstl:out value="${chorbiesOrderedByLikes.surname}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.minimumChorbiLikes" />: <jstl:out value="${minimumChorbiLikes}"/><br/>
	<spring:message code="dashboard.maximumChorbiLikes" />: <jstl:out value="${maximumChorbiLikes}"/><br/>
	<spring:message code="dashboard.averageChorbiLikes" />: <jstl:out value="${averageChorbiLikes}"/><br/>
	
	
	<spring:message code="dashboard.minimumChirpsReceivedByChorbi" />: <jstl:out value="${minimumChirpsReceivedByChorbi}"/><br/>
	<spring:message code="dashboard.maximumChirpsReceivedByChorbi" />: <jstl:out value="${maximumChirpsReceivedByChorbi}"/><br/>
	<spring:message code="dashboard.averageChirpsReceivedByChorbi" />: <jstl:out value="${averageChirpsReceivedByChorbi}"/><br/>
	<spring:message code="dashboard.minimumChirpsSentByChorbi" />: <jstl:out value="${minimumChirpsSentByChorbi}"/><br/>
	<spring:message code="dashboard.maximumChirpsSentByChorbi" />: <jstl:out value="${maximumChirpsSentByChorbi}"/><br/>
	<spring:message code="dashboard.averageChirpsSentByChorbi" />: <jstl:out value="${averageChirpsSentByChorbi}"/><br/>
	
	
	<spring:message code="dashboard.ChorbiesWithMoreReceivedChirps" />:<br>
	<display:table pagesize="5" class="displaytag1" name="ChorbiesWithMoreReceivedChirps"
		requestURI="${requestURI}" id="row" uid="ChorbiesWithMoreReceivedChirps" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${ChorbiesWithMoreReceivedChirps.name}"/>
	   	  <jstl:out value="${ChorbiesWithMoreReceivedChirps.surname}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.ChorbiesWithMoreSentChirps" />:<br>
	<display:table pagesize="5" class="displaytag1" name="ChorbiesWithMoreSentChirps"
		requestURI="${requestURI}" id="row" uid="ChorbiesWithMoreSentChirps" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${ChorbiesWithMoreSentChirps.name}"/>
	   	  <jstl:out value="${ChorbiesWithMoreSentChirps.surname}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.getManagersOrderedByEvents" />:<br>
	<display:table pagesize="5" class="displaytag1" name="getManagersOrderedByEvents"
		requestURI="${requestURI}" id="row" uid="getManagersOrderedByEvents" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${getManagersOrderedByEvents[0].name}"/>
	   	  <jstl:out value="${getManagersOrderedByEvents[0].surname}"/>
	    </display:column>
	    <display:column title="${events}">
	   	  <jstl:out value="${getManagersOrderedByEvents[1]}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.getManagersWithChargedFee" />:<br>
	<display:table pagesize="5" class="displaytag1" name="getManagersWithChargedFee"
		requestURI="${requestURI}" id="row" uid="getManagersWithChargedFee" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${getManagersWithChargedFee.name}"/>
	   	  <jstl:out value="${getManagersWithChargedFee.surname}"/>
	    </display:column>
	    <display:column title="${fee}">
	   	  <jstl:out value="${getManagersWithChargedFee.chargedFee}"/>
	    </display:column>
	</display:table>
	<br>

	<spring:message code="dashboard.getChorbiesOrderedByEvents" />:<br>
	<display:table pagesize="5" class="displaytag1" name="getChorbiesOrderedByEvents"
		requestURI="${requestURI}" id="row" uid="getChorbiesOrderedByEvents" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${getChorbiesOrderedByEvents[0].name}"/>
	   	  <jstl:out value="${getChorbiesOrderedByEvents[0].surname}"/>
	    </display:column>
	    <display:column title="${events}">
	   	  <jstl:out value="${getChorbiesOrderedByEvents[1]}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.getChorbiesWithChargedFee" />:<br>
	<display:table pagesize="5" class="displaytag1" name="getChorbiesWithChargedFee"
		requestURI="${requestURI}" id="row" uid="getChorbiesWithChargedFee" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${getChorbiesWithChargedFee.name}"/>
	   	  <jstl:out value="${getChorbiesWithChargedFee.surname}"/>
	    </display:column>
	    <display:column title="${fee}">
	   	  <jstl:out value="${getChorbiesWithChargedFee.chargedFee}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.getChorbiesWithMinMaxAvgStars" />:<br>
	<display:table pagesize="5" class="displaytag1" name="getChorbiesWithMinMaxAvgStars"
		requestURI="${requestURI}" id="row" uid="getChorbiesWithMinMaxAvgStars" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${getChorbiesWithMinMaxAvgStars[0].name}"/>
	   	  <jstl:out value="${getChorbiesWithMinMaxAvgStars[0].surname}"/>
	    </display:column>
	    <display:column title="${min}">
	   	  <jstl:out value="${getChorbiesWithMinMaxAvgStars[1]}"/>
	    </display:column>
	    <display:column title="${max}">
	   	  <jstl:out value="${getChorbiesWithMinMaxAvgStars[2]}"/>
	    </display:column>
	    <display:column title="${avg}">
	   	  <jstl:out value="${getChorbiesWithMinMaxAvgStars[3]}"/>
	    </display:column>
	</display:table>
	<br>
	
	<spring:message code="dashboard.getChorbiesWithAvgStarsOrderedByAvgStars" />:<br>
	<display:table pagesize="5" class="displaytag1" name="getChorbiesWithAvgStarsOrderedByAvgStars"
		requestURI="${requestURI}" id="row" uid="getChorbiesWithAvgStarsOrderedByAvgStars" excludedParams="*">
		
	    <display:column title="${actorName}">
	   	  <jstl:out value="${getChorbiesWithAvgStarsOrderedByAvgStars[0].name}"/>
	   	  <jstl:out value="${getChorbiesWithAvgStarsOrderedByAvgStars[0].surname}"/>
	    </display:column>
	    <display:column title="${avg}">
	   	  <jstl:out value="${getChorbiesWithAvgStarsOrderedByAvgStars[1]}"/>
	    </display:column>
	</display:table>
	<br>
	
</fieldset>
