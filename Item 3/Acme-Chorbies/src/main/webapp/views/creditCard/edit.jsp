
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

<form:form action="creditCard/customer/edit.do" modelAttribute="creditCard">
	
	<form:hidden path="id" />
	
	<br />
	
	<acme:textbox code="creditCard.holderName" path="holderName"/>
	
	<form:label path="brandName">
		<spring:message code="creditCard.brandName" />
	</form:label>
	<form:select id="brandName" name="brandName" path="brandName">
    	<form:option value="VISA"><spring:message code="creditCard.brandName.visa" /></form:option>
    	<form:option value="MASTERCARD"><spring:message code="creditCard.brandName.mastercard" /></form:option>    	
    	<form:option value="DISCOVER"><spring:message code="creditCard.brandName.discover" /></form:option>
    	<form:option value="DINNERS"><spring:message code="creditCard.brandName.dinners" /></form:option>
    	<form:option value="AMEX"><spring:message code="creditCard.brandName.amex" /></form:option>
    </form:select>
    <br />
	<acme:textbox code="creditCard.number" path="number"/>
	<acme:textbox code="creditCard.expirationMonth" path="expirationMonth"/>
	<acme:textbox code="creditCard.expirationYear" path="expirationYear"/>
	<form:label path="cvvCode">
		<spring:message code="creditCard.cvvCode" />:
	</form:label>
	<form:input type="number" step="any" path="cvvCode"/>
	<form:errors path="cvvCode" cssClass="error" />
	<br/>	

	
	
	<acme:submit name="save" code="creditCard.save"/>
	
	<jstl:if test="${creditCard.id != 0}">
		<acme:submit name="delete" code="creditCard.delete"/>
	</jstl:if>
	
	<acme:cancel url="creditCard/customer/myCreditCard.do" code="creditCard.cancel"/>
	<br>
</form:form>