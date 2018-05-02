<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script src="<s:url value='/resources/jquery-3.1.1.js' />"></script>
<script src="<s:url value='/resources/sockjs-1.1.1.js' />"></script>
  <script src="<s:url value='/resources/stomp.js' />"></script>
<link rel="stylesheet" type="text/css" href="<s:url value="/resources/stylesignup.css" />" >
<title>Sign Up</title>
<script type="text/javascript">
	
	const ALPHABET = ["a","b","c","d","e","f","g","h","i","j","k","l","m",
                  "n","o","p","q","r","s","t","u","v","w","x","y","z",
                  "A","B","C","D","E","F","G","H","I","J","K","L","M",
                  "N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                  "0","1","2","3","4","5","6","7","8","9"];
	const ALPHABET_LENGTH = ALPHABET.length;
	
	var emailAlreadyExists;
	var usernameAlreadyExists;
	
	
	window.onload = function(){
		
		document.f.email.focus();
		emailAlreadyExists = document.getElementById("emailAlreadyExists");
		usernameAlreadyExists = document.getElementById("usernameAlreadyExists");
		
		const url = "<s:url value='/messaging/signup' />";
		var sock = new SockJS(url);
		var stomp = Stomp.over(sock);
		const headers = {
			      login: 'root',
			      passcode: 'root',
			    };
		
		stomp.connect(headers, function(frame) {
			stomp.subscribe("/user/queue/checkUsername", checkUsername);
			stomp.subscribe("/user/queue/checkEmail", checkEmail);
		});
		stomp.heartbeat.outgoing = 7000;
		stomp.heartbeat.incoming = 7000;
		
		$(document).ready(function() {
		    	$.ajax({
		    		dataType: "json",
		        	url: "<s:url value='/userinfoapi/lastSignUp' />"
		    	}).then(function(data) {
		       		document.getElementById("lastsignupusers").innerHTML = "${lastSignUpMessage}";
		       		for(var i = 0;i<data.max;i++){
		       			var username = data.queue[i];
		       			if(username == undefined){
		       				$(".lastsignupusers").append("<b></b></br>");
		       			}else{
		       				$(".lastsignupusers").append("<b>"+username+"</b></br>");
		       			}
		       		}
		    	});
		});
			
		document.f.email.onchange = function(){
			var headers = {"Content-Type": "text/plain"};
			stomp.send("/app/meessaging/checkEmail",headers, this.value);
		}
		
		document.f.username.onchange = function(){
			var headers = {"Content-Type": "text/plain"};
			stomp.send("/app/meessaging/checkUsername",headers, this.value);
		}
		
		document.getElementById("generateUsername").onclick = function(){
			document.f.username.value = generate();
		}
		
		document.getElementById("generatePassword").onclick = function(){
			document.f.password.value = generate();
		}
}
	
	function generate(){
		
		function generateRandomNumber(start,end){
			if(start>end) return;
			var diff = end-start + 1;
			return start+Math.floor(diff*Math.random());
		}
	
		var wordLength = generateRandomNumber(5,9);
		var word = "";
	
		for(var i = 0; i<wordLength; i++){
			word += ALPHABET[generateRandomNumber(0,ALPHABET_LENGTH-1)];
		}
		return word;
	}
	
	function checkUsername(incoming){
		if(incoming.body == 'true'){
			
			usernameAlreadyExists.innerHTML = "${thatUsernameAlreadyExists}";
			usernameAlreadyExists.style.color = "red";
			
		}else{
			usernameAlreadyExists.innerHTML = "${correctUsernameMessage}";
			usernameAlreadyExists.style.color = "green";
		}
	}
	
	function checkEmail(incoming){
		
		if(incoming.body == 'true'){
			emailAlreadyExists.innerHTML = "${emailAlreadyExists}";
			emailAlreadyExists.style.color = "red";
		}else{
			emailAlreadyExists.innerHTML = "${correctEmailMessage}";
			emailAlreadyExists.style.color = "green";
		}
	}

</script>
</head>
<body>

	<div class='signupform'>
	<div class='forminner'>
	<h1>${headerh1}</h1>
	<small>${infoMessage}</small>
	
	<f:form method="POST" name="f" action='signup' >
	
	<label>${email}</label><br>
	<input type='email' name='email' class="fields" id="email" required="required" /><br>
	<small id="emailAlreadyExists"></small><br><br>
	
	<label>${username}</label><br>
	<input type='text' name='username'  class="fields"  required="required" pattern="[a-zA-Z0-9]{2,36}" title="${jserrorusernameField}"><br>
	<a id="generateUsername">${generateusername}</a>
	<small id="usernameAlreadyExists"></small><br><br>
	
	<label>${password}</label><br>
	<input type='text' name='password' class="fields"  required="required" pattern="[a-zA-Z0-9-_]{4,36}" title="${jserrorpasswordField}"/><br>
	<a id="generatePassword">${generatepassword}</a>
	
	<label id="error" >${signuperror}${duplicateerror}</label>
	
	<input name="submit"   type="submit" value="${signupbutton}" class="fields" id='signupbtn'/>
	<label>${loginquestion}</label>
	</f:form>
	</div>
	</div>
	
	<div class='whoislast'>
		<div class="lastsignup">
		<h1 class="lastsignupusers" id="lastsignupusers"></h1>
		</div>
	</div>
	
</body>
</html>