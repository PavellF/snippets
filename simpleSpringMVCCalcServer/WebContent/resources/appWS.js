var stomp = new STOMPService(broker_url);
  
   window.onload = function (){
		
   	 	stomp.setLogin(broker_login);
 		stomp.setPassword(broker_password);
 		stomp.setHeartbeatOutgoing(broker_hb_in);
 		stomp.setHeartbeatIncoming(broker_hb_out);
		stomp.connect(["/user/queue/output","/user/queue/errors"],[handleSTOMPMessage, handleSTOMPError]);
		
		var cookieService = new CookieService();
		var miscServices = new MiscServices();
		var cookiesArray = cookieService.getCookieValuesToKeyValPairArray();
		
		(function (){
			if((Object.keys(cookiesArray).length < 9) || (document.cookie == "")) {
				
				document.cookie = "constOneSign=Bind Me!; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				document.cookie = "constOneVal=0; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				
				document.cookie = "constTwoSign=Bind Me!; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				document.cookie = "constTwoVal=0; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				
				document.cookie = "constThreeSign=Bind Me!; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				document.cookie = "constThreeVal=0; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				
				document.cookie = "constFourSign=Bind Me!; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				document.cookie = "constFourVal=0; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				
				document.cookie = "style=hubble; expires=Fri, 31 Dec 9999 23:59:59 GMT";
				
				cookiesArray = cookieService.getCookieValuesToKeyValPairArray();
			}
		})();
		
			var displayElementsArray = [];
			
			var display = document.querySelector("#display");
			var alert = document.querySelector("#alert");
			var constants = document.querySelectorAll(".constants");
			var userConstants = document.querySelectorAll(".userConstants");
			var constantOptions = document.querySelectorAll(".constantsOption");
			var sinButton = document.querySelector("#sin");
			var cosButton = document.querySelector("#cos");
			var tanButton = document.querySelector("#tan");
			var advancedFunctions = document.querySelectorAll(".advancedFunc");
			var actions = document.querySelectorAll(".action");
			var digits = document.querySelectorAll(".digit");
			var measSwitch = document.querySelector("#measSwitch");
			var firstLotteryInput = document.querySelector("#firstLotteryInput");
			var secondLotteryInput = document.querySelector("#secondLotteryInput");
			var lotteryButton = document.querySelector("#lotteryButton");
			var luckyNumber = document.querySelector("#luckyNumber");
			var lotteryResult = document.querySelector("#lotteryResult");
			var firstRandomValue = document.querySelector("#firstRandomValue");
			var secondRandomValue = document.querySelector("#secondRandomValue");
			var randomResult = document.querySelector("#randomResult");
			var randomButton = document.querySelector("#randomButton");
			var setValueInput = document.querySelector("#setValueInput");
			var bindButton = document.querySelector("#bindButton");
			var selectSign = document.querySelector("#selectSign");
			var selectButton =  document.querySelector("#selectButton");
			var history = document.querySelector("#history");
			var faq = document.querySelector("#faq");
			var historySwitch = document.querySelector("#historyLabel");
			var functions = document.querySelectorAll(".function");
			var styleChangeButtons = document.querySelectorAll(".styleChangeButtons");
			
			var expression;
			var complex = 0;
			var ans = 0;
			
			var anyFunction =  function (){
				addMultiply();
				var type = this.innerHTML;
				
				displayElementsArray.push(type);
				displayElementsArray.push("(");
				display.value += type+"(";
			}
			
			var del = function(){
				displayElementsArray = [];
				display.value = "";
			}

			var undo =	function (){
				var newArray = displayElementsArray;
				del();
				var out = "";
				for(var a = 0;a<newArray.length-1;a++){
					displayElementsArray.push(newArray[a]);
					out += displayElementsArray[a];
				}
				display.value = out;
			}
			
			var addMultiply = function (){
				var lastElement;
				
				if(displayElementsArray.length == 0){
					return;
				}else{
					lastElement = displayElementsArray[displayElementsArray.length-1];
				}
				
				if(!miscServices.anyEquals(lastElement,["-","+","÷","logX","ª√","Rem‚","×","(","‚"])){
					displayElementsArray.push("×");
					console.log("multiply has been pushed before "+lastElement);
				}
			}
			
			var add = function (){
				addMultiply();
				display.value += this.innerHTML;
				displayElementsArray.push(this.innerHTML);
			}

			var  setStyle = function(styleName,reload){
				
				if(typeof arguments[0] != "string") {
					styleName = this.innerHTML;
					reload = false;
				}
				
				document.body.className = styleName;
				
				document.cookie = "style="+styleName+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
			    cookiesArray = cookieService.getCookieValuesToKeyValPairArray();
			    if(reload === true) location.reload();
			}
			
			var action = function (){
				var lastElement;
				var action = this.innerHTML;
				
				lastElement = displayElementsArray[displayElementsArray.length-1];
				
				if((lastElement == "("|| displayElementsArray.length == 0) && action != "-") return;
							
				if(action == "-" && lastElement == "-"){
					undo();
					action("+");
					return;
				}
				
				if(lastElement == "+" || lastElement == "×" || lastElement == "÷" || lastElement == "-") return;
				
				display.value += action;
				displayElementsArray.push(action);
			}

			var advancedSign = function (value){
				var lastPosition = displayElementsArray[displayElementsArray.length-1];
				if(lastPosition == ")" || !isNaN(lastPosition) || isConstant(lastPosition)){
					display.value += value;
					displayElementsArray.push(value);
				}
			}
			
			var digital = function(){
				
				const length = displayElementsArray.length;
				if( length == 0 || (displayElementsArray[0] == ans &&  length == 1)){
						del();
				}
				var val = this.innerHTML;
				display.value += val;
				displayElementsArray.push(val);
			}
			
			function isConstant(value){
				return  miscServices.anyEquals(value,
										[cookiesArray.constOneSign,
				                         cookiesArray.constTwoSign,
				                         cookiesArray.constThreeSign,
				                         cookiesArray.constFourSign,
				                         '℮','π']);
				}
			
			function getMeasurement(){
				if(measSwitch.innerHTML == "Radian"){
					return "RADIAN";
				}else{
					return "DEGREES";
				}
			}
			
			var parseLotteryVal =  function (){
				
				if(firstLotteryInput.value == secondLotteryInput.value){
					lotteryButton.innerHTML = "win";
				}else{
					lotteryButton.innerHTML = "i'm gonna win!";
				}
			}
			
			function parseDisplayElementsArray(){
				var length = displayElementsArray.length;
				expression = "";
				complex = 0;
				for(var i = 0;i<length;i++){
					expression += displayElementsArray[i];
					switch(displayElementsArray[i]){
					case "℮": displayElementsArray[i] = Math.E; break;
					case "ANS": displayElementsArray[i] = ans; break;
					case "π": displayElementsArray[i] = Math.PI; break;
					case cookiesArray.constOneSign: displayElementsArray[i] = cookiesArray.constOneVal; break;
					case cookiesArray.constTwoSign: displayElementsArray[i] = cookiesArray.constTwoVal; break;
					case cookiesArray.constThreeSign: displayElementsArray[i] = cookiesArray.constThreeVal; break;
					case cookiesArray.constFourSign: displayElementsArray[i] = cookiesArray.constFourVal; break;
					}
					if(isNaN(displayElementsArray[i]) 
							&& displayElementsArray[i] != '.' 
								&& displayElementsArray[i] != '‚'
									&& displayElementsArray[i] != ')'
										&& displayElementsArray[i] != '(') complex++;
				}
				return displayElementsArray;
			}
			
			function handleSTOMPMessage(incoming){
				var body = JSON.parse(incoming.body);
				printAnswerAndAddEntryToHistory(body.answer);
			}
			
			function handleSTOMPError(incoming){
				var body = JSON.parse(incoming.body);
				alert.innerHTML = ""+body.message;
			}
			
			function parseNumber(number){
				var length = number.length;
				if(!isNaN(+number) && number.charAt(length-1) == "0" && number.charAt(length-2) == "."){
				  return number.substr(0,length-2);
				}else{
					return number;
				}
			}	
			
			function printAnswerAndAddEntryToHistory(answer){
				historySwitch.innerHTML = "History";
				ans = parseNumber(answer);
				del();
				display.value = ans;
				
				var br = document.createElement("br");
				var label = document.createElement("label");
				label.innerHTML = expression+"="+ans;
				
				history.appendChild(br);
				history.appendChild(label);
				
				displayElementsArray.push(ans);
			}
			
			setStyle(cookiesArray.style,false);
			setValuesOnConstButtons();
			
			function setValuesOnConstButtons(){
				
				userConstants[0].innerHTML = decodeURI(cookiesArray.constOneSign);
				userConstants[1].innerHTML = decodeURI(cookiesArray.constTwoSign);
				userConstants[2].innerHTML = decodeURI(cookiesArray.constThreeSign);
				userConstants[3].innerHTML = decodeURI(cookiesArray.constFourSign);
				
				constantOptions[0].innerHTML = decodeURI(cookiesArray.constOneSign);
				constantOptions[1].innerHTML = decodeURI(cookiesArray.constTwoSign);
				constantOptions[2].innerHTML = decodeURI(cookiesArray.constThreeSign);
				constantOptions[3].innerHTML = decodeURI(cookiesArray.constFourSign);
			}

			document.querySelector("#switch").onclick = function (){
				
				if(sinButton.innerHTML == "sin") sinButton.innerHTML = "sinh";
				else sinButton.innerHTML = "sin";
				
				if(cosButton.innerHTML == "cos") cosButton.innerHTML = "cosh";
				else cosButton.innerHTML = "cos";
				
				if(tanButton.innerHTML == "tan") tanButton.innerHTML = "tanh";
				else tanButton.innerHTML = "tan";
				
			};
			
			randomButton.onclick = function (){
				var valOne = +firstRandomValue.value;
				var valTwo = +secondRandomValue.value;
				
				if(!Number.isInteger(valOne) || !Number.isInteger(valTwo)){
					randomButton.innerHTML = "only integers allowed";
					return;
				}
				if(valOne > valTwo){
					randomButton.innerHTML = "start greater than end!";
					return;
				}
				randomButton.innerHTML = "Try";
				randomResult.value = miscServices.getRandomNum(valOne,valTwo);
			}
			
			sinButton.onclick =anyFunction;
			cosButton.onclick = anyFunction;
			tanButton.onclick = anyFunction;
			
			for(var m = 0;m<functions.length;m++){
				functions[m].onclick = anyFunction;
			}
			
		document.querySelector("#delete").onclick = del;
		document.querySelector("#undo").onclick = undo;

		bindButton.onclick = 	function (){
			var value = +setValueInput.value;
			
			if(isNaN(value)){
				bindButton.innerHTML = "only numbers";
				return;
			}
			
			var sign = selectSign.options[selectSign.selectedIndex ].value;
			var buttonNo = selectButton.options[ selectButton.selectedIndex ].value; //should begin from capital letter
			
			document.cookie = "const"+buttonNo+"Sign="+encodeURI(sign)+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
			document.cookie = "const"+buttonNo+"Val="+value+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
			
			cookiesArray = cookieService.getCookieValuesToKeyValPairArray();
			setValuesOnConstButtons();
		}
		
		var hideElement =  function (element){
			 var isHidden = element.style.display ;
			
			if(isHidden != "none") {
				element.style.display='none';
			}else{
				element.style.display='block';
			}
		}
		
		document.querySelector("#openingParenthesis").onclick = function (){
			addMultiply();
			display.value += "(";
			displayElementsArray.push("(");
		}
	
		document.querySelector("#closingParenthesis").onclick = function (){
			
			if(displayElementsArray.length != 0){
				var e = displayElementsArray[displayElementsArray.length-1];
				
				var statement = (!isNaN(e) || e == "²" || e == "!" || e == "%" || e == ")" || isConstant(e));
				
				if(statement){
					display.value += ")";
					displayElementsArray.push(")");
				}
			}
		}
	
		for(var i = 0; i<advancedFunctions.length;i++){
			advancedFunctions[i].onclick = add;
		}
	
		for(var i = 0; i<actions.length;i++){
			actions[i].onclick = action;
		}
		
		for(var i = 0; i<styleChangeButtons.length;i++){
			styleChangeButtons[i].onclick = setStyle;
		}
		
		document.querySelector("#degree").onclick =  function (){
			var lastPosition = displayElementsArray[displayElementsArray.length-1];
			
			if(lastPosition == ")" || !isNaN(lastPosition) || isConstant(lastPosition)){
			
				display.value += "^(";
				displayElementsArray.push("^");
				displayElementsArray.push("("); 
			}
		}
		
		document.querySelector("#factorial").onclick =  function(){ advancedSign.call(this,"!"); };
		document.querySelector("#comma").onclick =  function(){ advancedSign.call(this,"‚"); };
		document.querySelector("#percent").onclick =  function(){ advancedSign.call(this,"%");};
		document.querySelector("#square").onclick =  function(){ advancedSign.call(this,'²'); };
		
		document.querySelector("#point").onclick = function (){
			if(Number.isInteger(+displayElementsArray[displayElementsArray.length-1])){
				displayElementsArray.push(".");
				display.value += ".";
			}
		};
	
		for(var i =0 ;i<constants.length;i++){
			constants[i].onclick = add;
		}
		
		for(var i =0 ;i<userConstants.length;i++){
			userConstants[i].onclick = add;
		}
		
		for(var i =0 ;i<digits.length;i++){
			digits[i].onclick = digital;
		}
		
		document.querySelector("#reverse").onclick = function (){
			var val = display.value;
			
			if(!isNaN(val)){
				del();
				var result = 1/val;
				display.value = result;
				displayElementsArray.push(result);
			}else{
				alert.innerHTML = "Only numbers.";
			}
		};
		
		 document.querySelector("#round").onclick = function (){
			var displayValue = document.getElementById("display").value;
			
			if(!isNaN(displayValue)){
				del();
				display.value = Math.round(displayValue);
				displayElementsArray.push(Math.round(displayValue));
			}else{
				alert.innerHTML = "Only numbers.";
			}
		};
		
		measSwitch.onclick = function (){
			if(measSwitch.innerHTML == "Radian"){
				measSwitch.innerHTML = "Degree";
				measSwitch.style = "background-color: transparent;color: white;";
				return "DEGREES";
			}else{
				measSwitch.innerHTML = "Radian";
				measSwitch.style = "background-color:white;color: black;";
				return "RADIAN";
			}
		}
		
		firstLotteryInput.onchange = parseLotteryVal;
		secondLotteryInput.onchange = parseLotteryVal;
		
		lotteryButton.onclick = function (){
			
			var lucky = +luckyNumber.value;
			var valOne = +firstLotteryInput.value;
			var valTwo = +secondLotteryInput.value;
			
			if(valOne > valTwo){
				lotteryButton.innerHTML = "start greater than end!";
				return;
			}
			if(isNaN(lucky) || lucky<valOne || lucky>valTwo){
				lotteryButton.innerHTML = "correct lucky number";
				return;
			}
			if(!Number.isInteger(lucky) || !Number.isInteger(valOne) || !Number.isInteger(valTwo)){
				lotteryButton.innerHTML = "only integers allowed";
				return;
			}
			
			var tries = 0;
			while(true){
				tries++;
				if(lucky == miscServices.getRandomNum(valOne,valTwo)){
					lotteryResult.innerHTML = tries;
					return;
				}
			}
		}
		
		historySwitch.onclick = function(){ hideElement.call(this,history); };
		document.querySelector("#info").onclick = function(){ hideElement.call(this,faq); };
		
		document.querySelector("#answerButton").onclick = function(){
			
			if(displayElementsArray.length == 0) return;
				
			var expression = {
				elements: parseDisplayElementsArray(),
				measurement:getMeasurement(),
				complex: complex
			}
			console.log("Complexity: "+complex);
			if(expression.elements.length > 1){
			stomp.send("/app/main", {"content-type":"application/json"}, JSON.stringify(expression));
			console.log("Sended to: /app/main");
			}
		}
		
		document.querySelector("#showContactButton").onclick = function (){
			document.querySelector('#contact').innerHTML = "<a href='https://github.com/uphawu'>github.com/uphawu</a>";
		}
	}