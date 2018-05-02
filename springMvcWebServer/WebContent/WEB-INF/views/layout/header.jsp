<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
 <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link rel="stylesheet" type="text/css" href="<s:url value="/resources/font/font-awesome-4.7.0/css/font-awesome.min.css" />" >
   <script src="<s:url value='/resources/Services.js' />"></script>
   <script src="<s:url value='/resources/sockjs-1.1.1.js' />"></script>
   <script src="<s:url value='/resources/stomp.js' />"></script>
   <script src="<s:url value='/resources/AppLogic.js' />"></script>
   <script>
   
   	window.services = new Services();
   	window.stomp  =  new services.STOMPService("<s:url value='/messaging/main' />");
   	window.stomp.setHeartbeatOutgoing(10000);
   	window.stomp.setHeartbeatIncoming(10000);
   	
   	window.dateParser = new services.DateParser(["${January}","${February}","${March}","${April}","${May}"
   	                                    ,"${June}","${July}","${August}","${September}","${October}","${November}","${December}"],"${date_on}");
   </script>
 </head>
  
<body>
	
	<ul id='NotificationList'>
	
		<li><div class='errNotification' hidden='true' 
				data-accDenied='${notif_accessDenied}' 
				data-requestError='${notif_requestError}'></div>
		</li>
		
		<li><div class='newNotification' hidden='true'></div></li>
	
	</ul>
	
	<div class="header">
	<div class="toplinks">
		<ul>
		<li><a class='topMenuLinks' href="#">${headFirstLabel}</a></li>
		<li><a class='topMenuLinks' href="#">${headSecondLabel}</a></li>
		<li><a class='topMenuLinks' href="#">${headThirdLabel}</a></li>
		<li><a class='topMenuLinks' href="#">${headFourLabel}</a></li>
		
		<li id="searchinput"><input type="text" name="fname" placeholder="${headerplaceholder}"></li>
		
		<li><a href="#"><i class="fa fa-search" aria-hidden="true"></i></a></li>
		<sec:authorize access="isAuthenticated()">
			
			<li><a href="#"><i class="fa fa-inbox" aria-hidden="true"></i></a></li>
			<li><a><i class="fa fa-comment-o" aria-hidden="true"></i></a></li>
			<li><a href="<s:url value='/users/me' />"><img id='headerUserPic' src="${userProfilePic}"></a></li>
			
		</sec:authorize>
		</ul>
	</div>
	</div>
	
	<div hidden='true' class='notificationDropList'  >
		<ul class='notificationList' data-empty='${emptyNotifList}'></ul>
		<label class='notifRef' data-label='${refreshNotif}'>${refreshNotif}</label>
	</div>
	
</body>
</html>
