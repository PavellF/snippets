<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<s:url value="/resources/styleUsersProfile.css" />" >
<title>${username}</title>
<script type="text/javascript">
	window.onload = function(){
		
		var app = new Application(
					JSON.parse('${principalJSON}'),
					window.stomp,
					"<s:url value='/' />",
					"<s:url value='/resources/images/asynh.gif' />");
		
		app.UserProfileLogic(
				JSON.parse('${userJSON}'),
				JSON.parse('${userFriendsJSON}'),
				JSON.parse(JSON.stringify(${notificationsJSON})));
		
		if(${isAuthenticated}){
			app.HeaderLogic();
			
		}
		
		//var json = JSON.stringify();
		
		app.Messages(JSON.parse(JSON.stringify(${messagesJSON})),'USER_PROFILE',${userID});
		
		window.stomp.connect();
	}
</script>
</head>
<body>

	<div class='profilePage'>
		<div class='profileBlock'>
			<div class='pic'>
				<img id='profilePic' alt="profile picture" src="${userProfilePic}">
				<ul id='picMenu'>
				<script>
				(function(){
					if(!${isPrincipalRequest}){
						document.write("<li id='writeALetterButton'>${writeALetterButton}</li>");
						document.write("<li id='askForFriend' data-success='${requestIsSended}'");
						document.write("data-exception='${requestReturnedException}'");
						document.write("data-duplicate='${duplicateRequest}'>${addToFriendButton}</li>");
					}else{
						document.write("<li id='prefsButton'>${prefsButton}</li>");
					}
				})();		
				</script>
				</ul>
			</div>
			<div class='friends'>
				<h3 id='showAllEvents'><a href='#'>${eventLabel}</a> <label class='count'>${amountOfNotifications}</label></h3>
				<ul class='notifNfriendsList' data-noEvents='${noEvents}'></ul>
				
				<h3  id='showAllFriends'><a href="#">${friendsLabel}</a> <label class='count'>${amountOfFriends}</label></h3>
				<ul  class='notifNfriendsList' id="friendsList"  
				data-noFriends='${noFriends}' 
				data-write='${writeToaFriendLabel}' 
				data-since='${friendSinceLabel}' 
				data-already='${alreadyFriends}'
				data-ask='${askForFriend}'
				data-accept='${acceptButton}'
				data-decline='${declineButton}'
				data-friendsFromNow='${friendsFromNow}'
				data-friendsFail='${friendsFail}'
				data-friendsDeslSuccess='${friendsDeslSuccess}'
				data-friendsDeleteSucc='${friendsDeleteSucc}'
				></ul>
			</div>
			<div class='info'>
				<h1>${userLabel} <label class="userInfo"></label></h1>
				<ul id='userInfo'>
					<li><label>Id: </label><label class="userInfo"></label></li>
					<li><label>${regDateLabel} </label><label class="userInfo"></label></li>
					<li><label>${blockedLabel}: </label><label class="userInfo" 
					data-true="${userWasBlocked}" 
					data-false="${userWasNotBlocked}"></label></li>
					<li><label>${roleLabel}: </label><label class="userInfo"
					data-user="${roleUser}"
					data-root="${roleRoot}"
					data-employee="${roleEmployee}"></label></li>
					<li><label>${ratingLabel}: </label><a href='#'><label class="userInfo"></label></a></li>
					<li><label>${popLabel}: </label><label class="userInfo"></label></li>
					<li hidden='true' id='quickSendMessageArea'><textarea></textarea></li>
					<li hidden='true' id='quickSendMessageButton'><a>${sendButton}</a></li>
				</ul>
			</div>
			
			<div class='aboutyslf'>
				<ul id='aboutyList'>
					<li><h3 id='aboutLabel'>${aboutYourselfLabel}</h3></li>
					<li><div id='about' data-empty="${nothingAbout}"></div></li>
				</ul>
			</div>
		</div>
		
		<div class='switch'>
			<ul id='swithList'>
				<li><label>${commentsLabel}</label></li>
				<li><label>${hisCommentsLabel}</label></li>
				<li><label>${hisMaterials}</label></li>
				<li><label>${hisLikes}</label></li>
			</ul>
		</div>
		<div class='commentBlock'>
		
			<div class='messageForm' data-messageSentError='${messageSentError}'>
				<ul id='sendMessage'>
					<script>
						(function(){
							if(${isAuthenticated}){
							document.write("<li><img src=''></li>");
							document.write("<li><textarea id='sendMessageArea' placeholder='${leaveaMessageInside}'></textarea></li>");
							document.write("<li><a id='sendMessageButton'>${sendButton}</a></li>");
						}
				})();		
				</script>
				</ul>
			</div>
			
			<div class='messageSorting'>
				<ul>
					<li><a data-relay='disable'>${messagesByRating}</a></li>
					<li><a data-relay='disable'>${messagesByDate}</a></li>
					<li>${messagesAllString}<label id='messagesCounter'>${amountOfMessages}</label>+<label id='newMessagesCounter'>0</label>${messagesNewString}</li>
				</ul>
			</div>
			
			<div class='messages'>
				<ul class='messagesList' 
				data-up='${upLabel}' 
				data-down='${downLabel}' 
				data-reply='${replyLabel}' 
				data-correct='${correctLabel}' 
				data-delete='${deleteLabel}' 
				data-changed='${messageChanged}'
				data-done='${messageDone}'
				data-deleted='${messagesDeleted}'>
				
				<li hidden='true' id='quickSendArea'>
					<div>
						<ul class='qsBody'>
							<li><img src=''></li>
							<li><textarea placeholder='${leaveaMessageInside}'></textarea></li>
							<li><a>${sendButton}</a></li>
						</ul>
					</div>
				</li>
				
				</ul>
			</div>
			
			<div class='messageRequest'>
				<button>${loadMoreMessages}</button>
			</div>
			
		</div>
	</div>
	
	<div class='fullFriendList'>
	
		<div class='ffl_menu'>
			<button id='ffl_backButton'>${backButton}</button>
			<ul id='ffl_Menu'>
			<li><button class='sortButtons' data-relay='disable'>${byAlphabet}</button></li>
			<li><button class='sortButtons' data-relay='disable'>${byDate}</button></li>
			<li><button class='sortButtons' data-relay='disable'>${byRating}</button></li>
			<li><button class='sortButtons' data-relay='disable'>${byPop}</button></li>
			</ul>
		</div>
	
		<ul id='ffl_list' 
		data-delete="${fflDelete}"
		data-since='${friendSinceLabel}' 
		data-rating='${ratingLabel}'></ul>
	
		<div class='ffl_footer'>
			<button >${loadMore}</button>
		</div>
	
	</div>
	
	<div class='fullEventList'>
	
		<div class='fel_menu'>
			<button id='fel_backButton'>${backButton}</button>
			<label>${felMessage}</label>
			<ul id='fel_Menu'>
			<li><button id='fel_sortButton' data-relay='down'>${byDate}</button></li>
			</ul>
		</div>
	
		<ul id='fel_list' 
		data-felFrom="${felFrom}"
		data-felSince='${felSince}'>
		</ul>
	
		<div class='fel_footer'>
			<button >${loadMore}</button>
		</div>
	
	</div>
	
</body>

</html>