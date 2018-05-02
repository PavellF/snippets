<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<div class ='footer'>
<ul id="footerlinks">
<sec:authorize access="!isAuthenticated()">
	<li><a href="<s:url value='/login' />" >${footerlogin}</a></li>
	<li><a href="<s:url value='/signin' />" >${footersignin}</a></li>
	<li><a href="#" >${footerabout}</a></li>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <li><span>${footerhello} <sec:authentication property="principal.username" />!</span></li>
    <li><a href="<s:url value='/signin' />" >${footersignin}</a></li>
	<li><a href="#" >${footerabout}</a></li>
	<li><a href="<s:url value='/logout' />">${footerlogout}</a></li>
</sec:authorize>
</ul>
</div>

	