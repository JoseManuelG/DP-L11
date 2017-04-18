<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="<spring:url value='/' />">
		<img src="images/logo.png" height="200"  alt="Acme Chorbies Co., Inc." />
	</a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="security/register.do"><spring:message code="master.page.register" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('CHORBI')">
					<li><a href="chorbi/chorbi/myProfile.do"><spring:message code="master.page.chorbi.my.profile" /></a></li>
					<li><a href="chorbi/list.do"><spring:message code="master.page.list.chorbies" /></a></li>
					<li><a href="likes/chorbi/sent.do"><spring:message code="master.page.list.likes" /></a></li>
					<li><a href="creditCard/chorbi/myCreditCard.do"><spring:message code="master.page.credit.card" /></a></li>
					
					<li><a href="chirp/chorbi/received.do"><spring:message code="master.page.chirp.received" /></a></li>
					<li><a href="chirp/chorbi/sent.do"><spring:message code="master.page.chirp.sent" /></a></li>
					<li><a href="searchTemplate/chorbi/search.do"><spring:message code="master.page.search" /></a></li>
					</security:authorize>	
					<security:authorize access="hasRole('ADMINISTRATOR')">
					<li><a href="dashboard/administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="configuration/administrator/view.do"><spring:message code="master.page.administrator.configuration" /></a></li>
					<li><a href="banner/administrator/list.do"><spring:message code="master.page.administrator.banner" /></a></li>
					<li><a href="chorbi/administrator/list.do"><spring:message code="master.page.administrator.chorbies" /></a></li>
					
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

