function MathService(){

function _parseToPostfix(sequense,unitsOfMeasurement) {

    const actions = ["÷", "×", "+", "-"];
	var operations = [];
	var parsedString = "";
	var parsedStringsDump = [];
	var operationsDump = arrayInit(12);
	var open = 0;
	var close = 0;
	
	function arrayInit(size){
		var a = [size]; 
		for(var x = 0; x<size;x++){
			a[x] = [];
		}
		return a;
	}
	
	function parseArrayToPostfixNotationString(){
		var size = sequense.length;
		
		for(var i = 0; i<size; i++){
			var value = sequense[i];
			if(isNaN(value)){
				getParsedValue(value,i);
			}else{ //if it is number, just append this to string
				parsedString += value;
			}
			console.log(parsedString);
		}
	}

	function getParsedValue(original,position){
		switch(original){
			case "sin": parsedString +="tSIN "; return;
			case "cos": parsedString +="tCOS "; return;
			case "tan": parsedString +="tTAN "; return;
			case "sinh": parsedString +="tSINh "; return;
			case "cosh": parsedString +="tCOSh "; return;
			case "tanh": parsedString +="tTANh "; return;
			case "√": parsedString +="SQRTX 2 "; return;
			case "ª√": parsedString +="SQRTX "; return;
			case "ln": parsedString +="LOG "+Math.e+" "; return;
			case "lg": parsedString +="LOG 10 "; return;
			case "logX": parsedString +="LOG "; return;
			case ".": parsedString +="."; return;
			case "^": parsedString +=" ^ "; return;
			case "²": parsedString +=" ^ 2"; return;
			case "%": parsedString +=" %"; return;
			case "Rem": parsedString +="rem "; return;
			case "‚": parsedString +=" "; return;
			case "!": parsedString +=" ! "; return;
			case ")": parenthesesClose(); return;
			case "(": parenthesesOpen(); return;
			case "+": plus(); return;
			case "-": minus(position); return;
			case "×": operation("*", "*", "/"); return;
			case "÷": operation("/","*","/"); return;
		}
	}

	function operation(operation,symbolOne,symbolTwo){
		//if this operation (minus, for example) has been met first in whole expression. 
		if(operations.indexOf(" "+symbolOne) == -1 && operations.indexOf(" "+symbolTwo) == -1){
			operations.push(" "+operation); // add operation in operation array
			parsedString += " ";
		}else if(operations.indexOf(" "+symbolOne) != -1){ //if operation array already contains plus (in minus example)
			operations[operations.lastIndexOf(" "+symbolOne)] = " "+operation; //replace plus with minus
			parsedString += " "+symbolOne+" "; //plus pushed into RPN expression
		}else if(operations.indexOf(" "+symbolTwo) != -1){ //done the same for minus (minus replaces minus)
			operations[operations.lastIndexOf(" "+symbolTwo)] = " "+operation; 
			parsedString += " "+symbolTwo+" "; 
		}
	}

	function parenthesesOpen(){
		parsedStringsDump.push(parsedString);
		parsedString = "";
		
		open++;
		
		if(operations.length > 0){
			
			for(var i = 0; i<operations.length; i++){
				if(operations[i] == " "){
					operations.splice(i,1);
					i--;
				}
			}
			
			for(var i = 0; i<operations.length; i++){
				
				if(open == close+1){
					operationsDump[0][i] = operations[i];
				}else{
					operationsDump[open][i] = operations[i];
				}
				
			}
			operations = [];
		}
	}

	function parenthesesClose(){
		
		var notEmpty = false;
		
		parsedString += arrayToString(operations.reverse(),"");
		
		var buffer = parsedStringsDump.pop()
		+_getAnswerFromPostfixExpression(parsedString,unitsOfMeasurement);
		
		parsedString = buffer;
		
		close++;
		
		operations = [];
		
		for(var r = operationsDump.length-1; r >= 0; r--){
			
			if(notEmpty == true) break;
			
			for(var c = 0; c<operationsDump[r].length; c++){
				
				if((operationsDump[r][c] != null) && (r != 0)){
    				
    				operations.push(operationsDump[r][c]);
    				operationsDump[r][c] = null;
    				notEmpty = true;
    			}else if((operationsDump[0][c] != null) && (close == open) && (r == 0)){
    				
    				operations.push(operationsDump[0][c]);
    				operationsDump[0][c] = null;
    			}
			}
			
		}
	}

	function plus(){
		if(operations.indexOf(" *") != -1){//if operations array contains ' *'
			operations.splice(operations.lastIndexOf(" *"),1);
			parsedString +=" *";
		}else if(operations.indexOf(" /") != -1){
			operations.splice(operations.lastIndexOf(" /"),1);
			parsedString +=" /";
		}
    	
    	operation("+", "+", "-");
	}

	function minus(position){
		var elementBefore = sequense[position-1];
		
		if((operations.indexOf(" *") != -1) && elementBefore != "×"){
			operations.splice(operations.lastIndexOf(" *"),1);
			parsedString +=" *";
		}else if((operations.indexOf(" /") != -1) && elementBefore != "÷"){
			operations.splice(operations.lastIndexOf(" /"),1);
			parsedString +=" /";
		}
		
		
		if((position == 0 && sequense[0] == "-") || (sequense.length >= 1 && elementBefore == "(")){
			parsedString +="-";
		  
		}else if((sequense.length >= 1) && (containsElement(actions,elementBefore))){ 
			
			parsedString +="-";
			
		}else{
			operation("-", "-", "+");
		}
	}

	function containsElement(array,element){
		for(var i = 0; i<array.length; i++){
			if(array[i] == element) return true;
		}
		return false;
	}
	
	function arrayToString(array,delimeter){
		var finalString = "";
		for(var i = 0; i<array.length; i++){
			
			if(i!=0) finalString+=delimeter+array[i];
			else finalString+=array[i];
			
		}
		return finalString;
	}
	
	parseArrayToPostfixNotationString();
	
	if(open != close){
		var difference = open-close;
		for(var i = 0; i < difference; i++){
			parenthesesClose();
	    }
	}
	
	parsedString += arrayToString(operations.reverse(),"");
	console.log(parsedString );
	
    return parsedString;
}

function _getAnswerFromPostfixExpression(expression,unitsOfMeasurement){
	
	var array = expression.split(" ");
	
	for(var i = 0;i<array.length;i++){
		var iElement = ""+array[i];
		array[i] = iElement.replace("--","");
		
		if(iElement.startsWith("t") || iElement.startsWith("-t")){
			trigonometry(i,unitsOfMeasurement);
			i--;
		}else if(iElement == "%"){
			percent(i);
			i--;
		}else if((iElement == "LOG") || (iElement == "SQRTX") || (iElement == "rem")){
			advancedFunc(i);
			i--;
		}else if(iElement == "^"){
			degree(i);
			i--;
			i--;
		}else if(iElement == "!"){
			factorial(i);
			i--;
		}else if(iElement == "*" || iElement == "-" || iElement == "+" || iElement == "/"){
			action(i);
			i--;
			i--;
		}
	}
	
	function trigonometry(i,unitsOfMeasurement){
		var current = array[i];
		var nextNumber = array[i+1];
		var result = 0;
		
		if(unitsOfMeasurement == "DEGREES")  nextNumber = (nextNumber * Math.PI / 180);
		
		switch(current.replace("-","")){
		case "tSIN":result = Math.sin(nextNumber);break;
		case "tCOS":result = Math.cos(nextNumber);break;
		case "tTAN":result = Math.tan(nextNumber);break;
		case "tSINh":result = Math.sinh(nextNumber);break;
		case "tCOSh":result = Math.cosh(nextNumber);break;
		case "tTANh":result = Math.tanh(nextNumber);break;
		}
		
		if(current.startsWith("-")) array[i] = "-"+result;
		else  array[i] = result;
		
		array.splice(i+1,1);
	}
	
	function percent(i){
		var nextElement = array[i+1];
		var numberOne = array[i-1];
		var numberTwo = array[i-2];
		
		if(numberTwo === undefined){  //for expressions like 80% + 36
			numberTwo = 1;
			nextElement = array[i+2];
		}
		
		if(nextElement == "*" || nextElement == "/"){
			array[i-1] = (numberOne/100);
		}else if(nextElement == "+" || nextElement == "-"){
			array[i-1] = ((numberOne*numberTwo)/100);
		}
		
		array.splice(i,1);
	}
	
	function advancedFunc(i){
		var result = 0;
		var current = array[i];
		var numberOne = array[i+1];
		var numberTwo = array[i+2];
		
		if(current=="rem") result = numberOne % numberTwo;
		 
		if(current=="SQRTX") result = Math.pow(numberTwo, (1/numberOne));
	
    	if(current=="LOG") result = _getLogarithmWithAnyBase(numberOne, numberTwo);
    	
   
    	if(current.startsWith("-")) array[i] = "-"+result;
    	else array[i] = result;
    
    	array.splice(i+1,2);
    }
	
	function degree(i){
		var numberOne = array[i-1];
		var numberTwo = array[i+1];
		
		array[i] = Math.pow(numberOne, numberTwo);
		
		array.splice(i+1,1);
		array.splice(i-1,1);
	}
	
	function action(i){
		var current = array[i];
		var numberOne = array[i-2];
		var numberTwo = array[i-1];
		
		switch(current){
		case "*": array[i] = numberOne*numberTwo;break;
		case "-": array[i] = numberOne-numberTwo;break;
		case "+": array[i] = (+numberOne)+(+numberTwo);break;
		case "/": array[i] = numberOne/numberTwo;break;
		}
		
		array.splice(i-1,1);
		array.splice(i-2,1);
	}
	
	function factorial(i){
		var answer = "";
		var number = +array[i-1];
		
		if(number < 0){
			answer = "-";
			number = Math.abs(number);
		}
		
		if(Number.isInteger(number)) answer += _getFactorial(number);
		else answer += _gamma(number+1);
			
		array[i] = answer;
		array.splice(i-1,1);
	}
	
	return array[0];
}

function _getFactorial(x){
	if(x<0) x = Math.abs(x);
	
	var result = 1;
	
	if(x != 0){
		for(;x!=1;x--){
			result *= x;
		}
	}
	return result;
}

function _gamma(x){
	
	function logGamma(x){
		var tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
	    var ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
	                       + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
	                       +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
	      return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
	}
	
	return Math.exp(logGamma(x));
}

function _getLogarithmWithAnyBase(base,number){
	if(base<0 || base == 1) 
		return Number.NaN;
	if(number <= 0) 
		return Number.NaN;
	
	return Math.log(number) / Math.log(base);
}

function _getAnswer(sequense, unitsOfMeasurement) {
    return _getAnswerFromPostfixExpression(_parseToPostfix(sequense,unitsOfMeasurement), unitsOfMeasurement);
}
return {
    parseToPostfix: _parseToPostfix,
    getAnswerFromPostfixExpression:_getAnswerFromPostfixExpression,
    getFactorial: _getFactorial,
    gamma: _gamma,
    getLogarithmWithAnyBase: _getLogarithmWithAnyBase,
    getAnswer: _getAnswer
}
}
