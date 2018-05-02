<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script src="<s:url value='/resources/jquery-3.1.1.js' />"></script>
<link rel="stylesheet" type="text/css" href="<s:url value="/resources/styleloginpage.css" />" >
<title>Login</title>
<script type="text/javascript">
	
	window.onload = function(){
		
		var loginForm = document.getElementById("loginForm");
		var forgotPasswordForm = document.getElementById("forgotPasswordForm");
		var checkBox = document.getElementById("remember_me");
		
		document.f.username.focus();
		
		$(document).ready(function() {
	    	$.ajax({
	    		dataType: "json",
	        	url: "<s:url value='/userinfoapi/lastLoggedIn' />"
	    	}).then(function(data) {
	    		document.getElementById("lastLoggedIn").innerHTML = "${lastLoggedInMessage}";
	       		for(var i = 0;i<data.max;i++){
	       			var username = data.queue[i];
	       			
	       			if(username == undefined){
	       				$(".lastLoggedIn").append("<b></b><br/>");
	       			}else{
	       				$(".lastLoggedIn").append("<b>"+username+"</b><br/>");
	       			}
	       		}
	    	});
	});
		
		document.getElementById("showEmailForm").onclick = function(){
			loginForm.hidden=true;
			forgotPasswordForm.hidden=false;
		}
		
		document.getElementById("showLoginForm").onclick = function(){
			forgotPasswordForm.hidden=true;
			loginForm.hidden=false;
		}
		
		document.getElementById("remembermelabel").onclick = function(){
			
			if(checkBox.cheked == true){
				checkBox.cheked = false;
				this.innerHTML = "${jsmessagerememberno}";
			}else{
				checkBox.cheked = true;
				this.innerHTML = "${jsmessagerememberyes}";
			}
		}
}
	
</script>
</head>
<body >

	<div class='loginform' id='loginForm'>
	<div class='forminner'>
	<h1>${headerh1}</h1>
	
	<f:form method="POST" name="f" action='login' >
	
	<label>${username}</label><br />
	<input type='email' name='username' value='' class="fields" required="required"><br /><br />
	
	<label>${password}</label><br />
	<input type='text' name='password' class="fields"  required="required"  pattern="[a-zA-Z0-9-_]{4,36}" title="${jserrorpasswordField}"/><br /><br />
	
	<input id="remember_me" name="rememberme" type="checkbox" hidden='true'/>
    <label for="remember_me" id="remembermelabel" class="fields" >${jsmessagerememberno}</label><br />
    
    <label id="error" >${loginerror}</label>
	
	<input name="submit" type="submit" value="${signinbutton}" class="fields" id='loginbtn'/>
	<label>${registerquestion}</label>
	<label id="showEmailForm">${forgotpassword}</label>
	</f:form>
	
	</div>
	</div>
	
	<div class='forgotPasswordForm' id='forgotPasswordForm' hidden='true'>
	<div class='forminner'>
		<h1>${forgotPasswordForm}</h1>
		
		<f:form method="POST" action='resetWord' >
			<label>Email</label><br>
			<input type='email' name='email' class="fields" id="email" required="required"/><br>
			<small id='infoText'>${emailHelp}</small><br><br>
			<input style='height: 49px;' name="submit" type="submit" value="${submitButton}" class="fields" id='resetButton'/>
		</f:form>
		<label id='showLoginForm'>${back}</label>
	</div>
	</div>
	
	<div class='whoislast'>
		<div class="lastinner">
		<h1 class="lastLoggedIn" id="lastLoggedIn"></h1>
		</div>
	</div>
	
</body>
</html>