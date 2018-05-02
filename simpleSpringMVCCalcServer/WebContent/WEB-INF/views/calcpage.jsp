<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>
   <link rel="stylesheet" type="text/css" href="<s:url value="/resources/style.css" />" >
   <link rel="stylesheet" type="text/css" href="<s:url value="/resources/bootstrap.min.css" />" >
   <link rel="image" type="image/jpeg" href="<s:url value='/resources/images/heic1313b.jpg' />" >
   <link type="application/x-font-ttf" href="<s:url value='/resources/font/robotom.ttf' />" rel='stylesheet'>
   <title>Online calculator</title>
   <script src="<s:url value='/resources/Services.js' />"></script>
   <script src="<s:url value='/resources/sockjs-1.1.1.js' />"></script>
 	<script >
 	var broker_url="<s:url value='/main' />";
 	var broker_login = "${brokerLogin}";
 	var broker_password = "${brokerPassword}";
 	var broker_hb_in = ${heartbeatUserOut};
 	var broker_hb_out = ${heartbeatUserIn};
 	</script>
   <script src="<s:url value='/resources/stomp.js' />"></script>
   <script src="<s:url value='/resources/appWS.js' />"></script>
</head>
<body>
<div class="container">
		<sec:authorize access="hasAuthority( 'root' )">
              	<a href="javascript:stomp.send('/app/main', {'content-type':'application/json'}, JSON.stringify( {wrong:'json'}));">send wrong json</a>
              	<a href="javascript:stomp.send('/app/main', {'content-type':'application/json'}, 'this is not json');">send incorrect json</a>
              	<form  method="GET" action="<s:url value='/stsocsc' />">
  					<input type="submit"  type="button" value="go to user side computing">
				</form>
        </sec:authorize>
        <div class="row">
            <div class="col-md-1"></div>
            
            <div class="col-md-10">
            <div class="header">
             <h1 id="label">ONLINE CALCULATOR</h1>
            </div>
            
            <div class="content">
            	<textarea readonly="readonly" id="display"></textarea>
            		<div class="topbuttons">
            			<button type="button" class="advancedFunc">ª√</button>
            			<button type="button" class="advancedFunc">logX</button>
            			<button type="button" id="comma">‚</button>
            			<button type="button" class="function">√</button>
            			<button type="button" id='openingParenthesis'>(</button>
            			<button type="button" id="closingParenthesis">)</button>
            			<button type="button" id="percent">%</button>
            			<button type="button" id="undo">‹</button>
            		</div>
            	<button type="button" class="userConstants"></button>
            	<button type="button" id="factorial">x!</button>
            	<button type="button" id="switch">hyp</button>
            	<button type="button" id="sin" >sin</button>
            	<button type="button" class="function">ln</button>
            	<button type="button" class="digit">7</button>
            	<button type="button" class="digit">8</button>
            	<button type="button" class="digit">9</button>
            	<button type="button"  class="action">÷</button>
            	<button type="button" class="userConstants"></button>
            	<button type="button" class="function">lg</button>
            	<button type="button" id="reverse">1/x</button>
            	<button type="button" id="cos" >cos</button>
            	<button type="button" id="degree">xª</button>
            	<button type="button" class="digit">4</button> 
            	<button type="button" class="digit">5</button>
            	<button type="button" class="digit">6</button>
            	<button type="button" class="action">×</button>
            	<button type="button" class="userConstants"></button>
            	<button type="button" class="constants">π</button>
            	<button type="button" id="round">Round</button>
            	<button type="button" id="tan">tan</button>
            	<button type="button" id='delete'>DEL</button>
            	<button type="button" class="digit">1</button>
            	<button type="button" class="digit">2</button>
            	<button type="button" class="digit">3</button>
            	<button type="button" class="action">-</button>
            	<button type="button" class="userConstants"></button>
            	<button type="button" class="constants">℮</button>
            	<button type="button" class="advancedFunc">Rem</button>
            	<button type="button" id="square">x²</button>
            	<button type="button" class="constants">ANS</button>
            	<button type="button" id='point'>.</button>
            	<button type="button" class="digit">0</button>
            	<button type="button" id="answerButton">=</button>
            	<button type="button"  class="action">+</button>
            	
            	<div class="swithAndTextField">
            		<button type="button" id="measSwitch">Radian</button> 
            		<label id="alert"></label>
          		</div>
          		
          		<div class="textInfo">
          			<div class="col-md-12">
          			<h2 id="info" class="showHide">Click here for info</h2>
          			<div class="faq" id="faq">
            		<label>1. How to use LogX and ª√  button?</label><br/>
            		<p>Answer: LogX is logarithm with your own base. 
            		For example, logarithm 150 with base 23: LogX(25-2),(100+50) notice comma between brackets.
            		Or even simpler, LogX23,150. Don't forget about comma sign. Radical button ª√ uses same way:
            		 ³⁴√55 = ª√(40-6),(51+4) = ª√34,55 Comma again.</p>
            		 <label>2. What about Rem button?</label><br/>
            		 <p>Answer: Rem is remainder. Uses same way as LogX or radical button. For instance, 
            		 in the division of 10 by 3 we have: 3 × 3 + 1; 1 is remainder. 
            		 In our case Rem10,3 or Rem(2×5),(1+1+1). 
            		 Probably you saw this syntax with comma sign before.</p>
            		 <label>3. Lottery?</label><br/>
            		 <p>Answer: Try your luck. Chances for winning on the first time
            		  depends from start and end number,
            		  for example 1 and 4 equals 1 ÷ 4 = 0.25 = 25% chance rate for win, 5 and 6
            		  is 1 ÷ 2 = 0.5 = 50% (5 and 6 are included);
            		  1 and 50 is 1 ÷ 50 = 0.02 = 2% and so on. If the difference between start and end numbers is very large 
            		  it may will freeze your browser page.</p>
            		  <label>4. I want to bind very large or very small constant.</label><br/>
            		 <p>Answer: You should to use scientific notation. For very small number 0.0000007: 
            		 7E-7 (where 7 your number, -7 count of numbers after dot), very large 88000000000 88E9 
            		 (where 88 your number, 9 is count of zero numbers after 88)</p>
            		 </div>
            		</div>
          		</div>
          		
          		<div class="features">
          			<div class="col-md-4">
          				<h2>Bind constant</h2>
            			<label>1. Select button.</label><br />
            			<select id="selectButton">
  						<option value="One" class="constantsOption"></option>
  						<option value="Two" class="constantsOption"></option>
  						<option value="Three" class="constantsOption"></option>
  						<option value="Four" class="constantsOption"></option>
						</select><br/>
            			<label>2. Select sign.</label><br/>
            			<select id="selectSign">
  						<option>µ</option>
  						<option>ß</option>
  						<option>N</option>
  						<option>Θ</option>
  						<option>Σ</option>
  						<option>Ψ</option>
  						<option>Ω</option>
  						<option>Γ</option>
  						<option>λ</option>
  						<option>ν</option>
  						<option>ρ</option>
  						<option>ӕ</option>
						</select><br/>
            			<label>3. Set value.</label><br/>
            			<input type="text" id="setValueInput">
            			<button type="button" id="bindButton">OK</button>
          			</div>	
                	<div class="col-md-4">
                		<h2>Lottery</h2>
            			<label>Start</label><br/>
            			<input type="text" id="firstLotteryInput" ><br/>
            			<label>End</label><br/>
            			<input type="text" id="secondLotteryInput"><br />
            			<label>Lucky number</label><br/>
            			<input type="text" id="luckyNumber">
            			<button type="button" id="lotteryButton">i'm gonna win!</button><br/>
            			<span>You need</span> <label id="lotteryResult">0</label> <span>tries for win.</span>
                	</div>
            		<div class="col-md-4">
            			<h2>Random</h2>
            			<label>Start</label><br/>
            			<input type="text" id="firstRandomValue"><br/>
            			<label>End</label><br/>
            			<input type="text"id="secondRandomValue"><br/>
            			<label>Result</label><br/>
            			<input type="text" id="randomResult"  readonly="readonly" value="press try">
            			<button type="button" id="randomButton">Try</button>
	            	</div>
	            </div>
          		<div class="historyList">
          			<div class="col-md-12">
          			<h2  id="historyLabel" class="showHide">Nothing here yet.</h2>
          			<div id="history" ></div>
          			</div>
          		</div>
          		
            </div>
            <footer class="footer">
            	<div class="col-md-10">
            	<button type="button" class="styleChangeButtons">hubble</button>
                <button type="button" class="styleChangeButtons" >hearts</button>
                <button type="button" class="styleChangeButtons">cubes</button> 
                <button type="button" class="styleChangeButtons">boring</button> 
                <button type="button" id="showContactButton">Contact</button>
                <label id="contact"></label>
                </div>
            </footer>
               
        </div>
            <div class="col-md-1"></div>
        </div>
   
 </div>
</body>
</html>