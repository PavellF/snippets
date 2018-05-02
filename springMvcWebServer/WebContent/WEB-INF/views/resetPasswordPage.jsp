<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<s:url value="/resources/styleResetPassword.css" />" >
<title>Reset pasword</title>
<script type="text/javascript">

	const ALPHABET = ["a","b","c","d","e","f","g","h","i","j","k","l","m",
                  "n","o","p","q","r","s","t","u","v","w","x","y","z",
                  "A","B","C","D","E","F","G","H","I","J","K","L","M",
                  "N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                  "0","1","2","3","4","5","6","7","8","9"];
	const ALPHABET_LENGTH = ALPHABET.length;
	
	function parsePassword(){
		var password = document.getElementById("password").value;
		var length = password.length;
		
		if(length < 4 || length > 36 || length == null || length == undefined){
			document.getElementById("submitButton").disabled = true;
			document.getElementById("error").innerHTML = "${wrongPassword}";
			return false;
		}else{
			document.getElementById("submitButton").disabled = false;
			document.getElementById("error").innerHTML = "";
			return true;
		}
		return false;
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
	
	function generatePassword(){
		document.getElementById("password").value = generate();
		parsePassword();
	}
	
</script>
</head>
<body onload='document.f.username.focus();' >

	<div class='resetForm'>
	<div class='forminner'>
	<h1>${title}</h1>
	
	<f:form method="POST" name="f" onsubmit="return parsePassword();" action='${postURL}' >
	
	<label>${password}</label><br>
	<input type='text' name='password' class="fields" id="password" onChange="parsePassword();"/><br>
	<p onclick='generatePassword();' id="forme">${generatepassword}</p>
	
	<label id="error" >${resetPassword}${passwordLengthError}</label>
	
	<input name="submit"   type="submit" value="${submitButton}" class="fields" id='submitButton'/>
	</f:form>
	</div>
	</div>
	
	
</body>
</html>