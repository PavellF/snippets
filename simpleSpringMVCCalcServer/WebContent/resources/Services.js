function CookieService(){
	
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

function MiscServices(){
	
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

function STOMPService(url){
	
	var sock = new SockJS(url);
	var stomp = Stomp.over(sock);
	
	var headers = {
		      login: "",
		      passcode: "",
		    };
	
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
		stomp.send(destination, headers, content);
	}
	
	function _setHeartbeatOutgoing(val){
		stomp.heartbeat.outgoing = val;
	}
	
	function _setHeartbeatIncoming(val){
		stomp.heartbeat.incoming = val
	}
	
	function _connect(urls,callbacks){
		stomp.connect(headers, function(frame) {
			for(var i=0;i<urls.length;i++){
				stomp.subscribe(urls[i], callbacks[i]);
			}
		});
	}
	
	return {
		setHeaders:_setHeaders,
		setLogin:_setLogin,
		setPassword:_setPassword,
		setHeartbeatOutgoing:_setHeartbeatOutgoing,
		setHeartbeatIncoming:_setHeartbeatIncoming,
		connect:_connect,
		send:_send
	}
}