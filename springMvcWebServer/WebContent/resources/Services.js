function Services(){
	
	if(Services.instance === "object"){
		return Services.instance;
	}


function _CookieService(){
	
	function _getCookieValue(cookieName){
		var cookies = document.cookie.split(';');
		var cookie;
		for(var i = 0;i<cookies.length;i++){
			cookie = cookies[i].split("=");
			if(cookie[0].charAt(0)==' ') cookie[0] = cookie[0].substring(1);
			if(cookie[0] == cookieName) return cookie[1];
		}
	}
	
	function _getCookieValuesToKeyValPairArray(){
		var array = {};
		var cookies = document.cookie.split(';');
		var cookie;
		for(var i = 0;i<cookies.length;i++){
			cookie = cookies[i].split("=");
			if(cookie[0].charAt(0)==' ') cookie[0] = cookie[0].substring(1);
			array[cookie[0]] = decodeURI(cookie[1]);
		}
		return array;
	}
	
	return {
		getCookieValue:_getCookieValue,
		getCookieValuesToKeyValPairArray:_getCookieValuesToKeyValPairArray
	};
}

function _MiscServices(){
	
	function _getRandomNum(start,end){
		if(start>end){
			end = start;
			start = end;
		}
		var diff = end-start + 1;
		return start+Math.floor(diff*Math.random());
	}
	
	function _anyEquals(string,equalsWith){
		var i;
		
		for(i=0;i<equalsWith.length;i++){
			if(string == equalsWith[i]) return true;
		}
		return false;
	}
	
	return {
		getRandomNum:_getRandomNum,
		anyEquals:_anyEquals
	};
}

function _STOMPService(url){
	
	var sock = new SockJS(url);
	var stomp = Stomp.over(sock);
	var urlCallbackPairs = {};
	
	var headers = {
		      login: "",
		      passcode: "",
		    };
	
	var available = true;
	const AVAILABLE_TIME = 5000;
	
	function checkAvailability(){
		if(available){
			available = false;
			window.setTimeout(function(){ available = true; } ,AVAILABLE_TIME);
			return true;
		}
		console.log("Service is unavailable now.");
		return false;
	}
	
	function _addUrlAndCallback(url,callback){
		urlCallbackPairs[url] = callback;
	}
	
	function _getSTOMP(){
		return stomp;
	}
	
	function _setHeaders(h){
		headers = h;
	}
	
	function _setLogin(val){
		headers.login = val;
	}
	
	function _setPassword(val){
		headers.passcode = val;
	}
	
	function _send(destination,headers,content){
		if(checkAvailability()) stomp.send(destination, headers, content);
	}
	
	function _setHeartbeatOutgoing(val){
		stomp.heartbeat.outgoing = val;
	}
	
	function _setHeartbeatIncoming(val){
		stomp.heartbeat.incoming = val
	}
	
	function _connect(){
		
		var urls = Object.keys(urlCallbackPairs);
		var callbacks = Object.values(urlCallbackPairs);
		
		stomp.connect(headers, function(frame) {
			for(var i=0;i<urls.length;i++){
				
				stomp.subscribe(urls[i], callbacks[i]);
				
			}
		});
	}
	
	function _requestReply(url, callback, headers){
		if(checkAvailability()) {
			
			if(headers){
				stomp.subscribe(url,callback,headers);
			}else{
				stomp.subscribe(url,callback);
			}
			
		}
	}
	
	return {
		addUrlAndCallback:_addUrlAndCallback,
		setHeaders:_setHeaders,
		setLogin:_setLogin,
		setPassword:_setPassword,
		setHeartbeatOutgoing:_setHeartbeatOutgoing,
		setHeartbeatIncoming:_setHeartbeatIncoming,
		connect:_connect,
		send:_send,
		requestReply:_requestReply
	}
}

function _DateParser(localMonths,on){
	
	 this.parse = function(date,short){
		
		var out = "";
		
		out += date.getDate()+" "+localMonths[date.getMonth()]+" "+date.getFullYear();
		
		if(!short || short == false){
			
			const mins = date.getMinutes();
			
			out +=" "+on+" ";
			out += date.getHours();
			out +=":";
			out += (mins < 10) ? "0"+mins : mins;
		}
		
		return out;
	}
	
}

Services.instance = this;

return {
	DateParser:_DateParser,
	STOMPService:_STOMPService,
	MiscServices:_MiscServices,
	CookieService:_CookieService,
};

}