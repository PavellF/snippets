function Application(principal,stomp,baseURL,ajaxAnimURL){

	 if (typeof Application.instance === "object") {
         return Application.instance;
     }
	
	 var errNotification = document.querySelector(".errNotification");
	 var newNotification = document.querySelector(".newNotification");
	 var notificationList = document.querySelector("#notificationList");
	 
	 var showNotification = function(message, type){
		 
		 if(type.hidden){
			 type.hidden = false;
			 type.innerHTML = message;
			 type.onclick = function(){
				 type.hidden = true;
			 };
		 }else{
			 var newNotif = type.cloneNode(true);
			 var newListItem = document.createElement("li");
			 
			 newNotif.innerHTML = message;
			 newNotif.onclick = function (){
				 newNotif.hidden = true;
			};
			 
			 newListItem.appendChild(newNotif);
			 notificationList.appendChild(newListItem);
		 }
		 
	};
	
	
function _UserProfileLogic(user,friends,events){
	
	if(arguments.length != 3) throw new Error("Function called with less than three arguments passed.");
	
	function setUserFriends(userFriends){
		
		const length = userFriends.length;
		 
		 if(length == 0){
				var l = document.createElement("label");
				l.innerHTML= friendsList.getAttribute("data-noFriends");
				friendsList.appendChild(l);
				return;
	        }
		 
		 document.querySelector("#showAllFriends").onclick = function(){
			 new FullFriendsListLogic();	 
			 hideBlock.call(this,profilePage,fullFriendList);
		};
		
		printUserFriendsOnPage(userFriends);
	}
	
	function setEvents(events){
		
		const length = events.length;
		 
		 if(length == 0){
			 var eList = document.querySelector(".notifNfriendsList");
			 var l = document.createElement("label");
			 l.innerHTML= eList.getAttribute("data-noEvents");
			 eList.appendChild(l);
			 return;
		 }
		
		 document.querySelector("#showAllEvents").onclick = function(){
			 new FullEventListLogic();
			 hideBlock.call(this,profilePage,fullEventList);
		};
		 
		printEvents(events);
	}
	
	var loadAnimation = document.querySelector("#loadAnimation");
	var quickSendMessageArea = document.querySelector("#quickSendMessageArea");
	var quickSendMessageButton = document.querySelector("#quickSendMessageButton");
	var userInfoList = document.querySelector("#userInfo");
	var friendsList = document.querySelector("#friendsList");
	var counters = document.querySelectorAll(".count");
	var aboutArea = document.querySelector("#about");
	var fullFriendList = document.querySelector(".fullFriendList");
	var profilePage = document.querySelector(".profilePage");
	var fullEventList = document.querySelector(".fullEventList");
	var askForFriend = document.querySelector("#askForFriend");
	
	var isAnonymous = !principal || principal=="anonymousUser";
	var isSelfRequest = (principal) ? (principal.id == user.id) : false;
	
	var hideBlock = function(hide,show){
		hide.hidden = !hide.hidden;
	 	show.hidden = !show.hidden;
	};
	
	(function(){
		
		hideBlock(fullFriendList,fullEventList);
		setUserFriends(friends);
		setEvents(events);
		printUserOnPage(user);
		
		window.setTimeout(function(){
			if(!isSelfRequest){
				stomp.send("/app/messaging/increasePopularity/"+user.id,{},{});
			}
		},120000);
		
		if(!isSelfRequest){
			 document.querySelector("#writeALetterButton").onclick = function(){
				 
				 	if(isAnonymous) {
				 		showNotification(errNotification.getAttribute("data-accDenied"),errNotification);
				 		return;
				 	}
				 		
				 	quickSendMessageArea.hidden = !quickSendMessageArea.hidden;
					quickSendMessageButton.hidden = !quickSendMessageButton.hidden;
					this.style.color = "black";
			 }
			 
			document.querySelector("#askForFriend").onclick = function (){
				 
					if(isAnonymous) {
						showNotification(errNotification.getAttribute("data-accDenied"),errNotification);
						return;
					}
								 
					stomp.requestReply("/app/messaging/relationship/add/"+user.id, function (incoming){
							 var status = JSON.parse(incoming.body);
							 
							 switch(status){
								case "SUCCESS": askForFriend.innerHTML =
									askForFriend.getAttribute("data-success"); break;
								case "EXCEPTION": askForFriend.innerHTML =
									askForFriend.getAttribute("data-exception"); break;
								case "DUPLICATE": askForFriend.innerHTML =
									askForFriend.getAttribute("data-duplicate"); break;
							}
						
							 askForFriend.style.color = "black";
						});
			}
		}
		
	})();
	
	function printUserOnPage(user){
		  
		  var userInfo = document.querySelectorAll(".userInfo");
		  
		  userInfo[0].innerHTML = user.username;
		  userInfo[1].innerHTML = user.id;
		  userInfo[2].innerHTML = dateParser.parse(new Date(user.registrationDate));
			
		  (function (){
				
				if(user.blocked == "true"){
					userInfo[3].innerHTML = userInfo[3].getAttribute("data-true");
					userInfo[3].style.color = "red";
				}else{
					userInfo[3].innerHTML = userInfo[3].getAttribute("data-false");
					userInfo[3].style.color = "lawngreen";
				}
				
		  })();
			
		  (function (){
				
				if(user.role == "USER"){
					userInfo[4].innerHTML = userInfo[4].getAttribute("data-user");
				}else if(user.role == "ROOT"){
					userInfo[4].innerHTML = userInfo[4].getAttribute("data-root");
					userInfo[4].style.color = "red";
				}else if(user.role == "EMPLOYEE"){
					userInfo[4].innerHTML = userInfo[4].getAttribute("data-employee");
					userInfo[4].style.color = "blue";
				}
				
		  })();
			
		  aboutArea.innerHTML = (!user.about) ? aboutArea.getAttribute("data-empty") : 
			user.about;
			
		  userInfo[5].innerHTML = user.rating;
		  userInfo[6].innerHTML = user.popularity;
			
	}
	  
	  function printEvents(e){
		 var eList = document.querySelector(".notifNfriendsList");
		 var fragment = document.createDocumentFragment();
		 
		 var willBePrinted = (e.length >= 4) ? 4 : e.length;
		 
		 for(var i = 0; i<willBePrinted; i++){
			 fragment.appendChild(getEventDOM(e[i]));
		 }
		 
		 eList.appendChild(fragment);
	}
	 
	function getEventDOM(e){
		 
		 var li = document.createElement("li");
		 
		 var eventDiv = document.createElement("div");
		 eventDiv.className = "event";
		 
		 var listInside = document.createElement("ul");
		 
		 var listInsideFirst = document.createElement("li");
		 
		 var listInsideElemHref = document.createElement("a");
		 listInsideElemHref.href = e.by.username;
		 listInsideElemHref.innerHTML = 
			 e.by.username+" "+dateParser.parse(new Date(e.date),true); 
		 
		 var listInsideSecond = document.createElement("li");
		 
		 var smallContent = document.createElement("small");
		 smallContent.innerHTML = e.content;
		 
		 listInsideSecond.appendChild(smallContent);
		 listInsideFirst.appendChild(listInsideElemHref);
		 
		 listInside.appendChild(listInsideFirst);
		 listInside.appendChild(listInsideSecond);
		 
		 eventDiv.appendChild(listInside);
		 
		 li.appendChild(eventDiv);
		 
		 return li;
	 }
	 
	 function AJAXLoadMoreFriends(){
		 	
		 	var url = baseURL+"api/getUserFriends/"+user.id+"?count=4&accepted=false";
				
			var request = new XMLHttpRequest();
			
			request.open("GET", url, true);
			request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
			
	        var timeout = 10000;
	        var timer = window.setTimeout(function () { request.abort(); console.log("request is aborted");},timeout);
	         
	        request.onreadystatechange = function () {
	                if (request.readyState == 4 && request.status == 200) {
	                    window.clearTimeout(timer);
	                    
	                    var friends = JSON.parse(request.responseText);
	                    
	                    if(friends.length != 0){
	                    	removeUserFriendsFromPage();
	       				 	printUserFriendsOnPage(friends);
	                    }
	                    
	                }else if(request.readyState == 4 && !request.status == 200){
						console.log("request is not OK(200)");
					}
	            }
	         request.send();
	 }
	 
	 function checkAndRefreshFriendList(){
		 
		 var friendDecideElements = document.querySelectorAll(".friendDecide");
		 
		 const length = friendDecideElements.length;
		 
		 for(var i =0;(friendDecideElements[i] && friendDecideElements[i].hidden == true);i++);
		
		 if(length == i) {
			 window.setTimeout(function(){
				 console.log("going to request more friends");
				 AJAXLoadMoreFriends();
			},15000);
		}
	 }
	 
	function removeUserFriendsFromPage(){
		 while (friendsList.firstChild) {
			 friendsList.removeChild(friendsList.firstChild);
		 }
	 }
	 
	 function printUserFriendsOnPage(f){
        
		var fragment = document.createDocumentFragment();
		
		const length = f.length;
		
		var willBePrinted = (length >= 4) ? 4 : length;
			
		for(var i = 0;i<willBePrinted;i++){
			
			if(!isAnonymous && principal.id == f[i].friend.id){
				askForFriend.hidden = true;
			}
			
			if(f[i].since){
				fragment.appendChild(getAlreadyAcceptedDOM(f[i]));
			}else{
				fragment.appendChild(getHaveNotAcceptedYetDOM(f[i]));
			}
		}
			
		friendsList.appendChild(fragment);
	 }
	 
	 
	 function getAlreadyAcceptedDOM(friendObj){
			var fName = friendObj.friend.username;
			
			var newLine = document.createElement("li");
			var friend = document.createElement("div");
			friend.className = "friend";
		 
			var friendPic = document.createElement("img");
			friendPic.alt = fName;
			friendPic.src = friendObj.friend.userPicURL;
		 
			var list = document.createElement("ul");
		 
			var listName = document.createElement("li");
			var name = document.createElement("a");
			name.className = "friendName";
			name.href = fName;
			name.innerHTML = fName;
			listName.appendChild(name);
		 
			var listSend = document.createElement("li");
			var write = document.createElement("a");
			write.href = "";
			write.innerHTML = friendsList.getAttribute("data-write");
			listSend.appendChild(write);
		 
			var listSince = document.createElement("li");
			var since = document.createElement("small");
			since.innerHTML = friendsList.getAttribute("data-since")+" "+
			dateParser.parse(new Date(friendObj.since),true);
			listSince.appendChild(since);
		 
			list.appendChild(listName);
			list.appendChild(listSend);
			list.appendChild(listSince);
		 
			friend.appendChild(friendPic);
			friend.appendChild(list);
			
			newLine.appendChild(friend);
			
			return newLine;
		}
	 
	 function getHaveNotAcceptedYetDOM(friends){
			var fName = friends.friend.username;
			var fPic = friends.friend.userPicURL;
			var fID = friends.friend.id;
			
			var newLine = document.createElement("li");
			var friend = document.createElement("div");
			friend.className = "friend";
		 
			var friendPic = document.createElement("img");
			friendPic.alt = fName;
			friendPic.src = fPic;
		 
			var list = document.createElement("ul");
		 
			var listName = document.createElement("li");
			var name = document.createElement("a");
			name.className = "friendName";
			name.href = fName;
			name.innerHTML = fName;
			listName.appendChild(name);
		 
			var listDesc = document.createElement("li");
			var desc = document.createElement("a");
			desc.innerHTML = friendsList.getAttribute("data-ask");
			listDesc.appendChild(desc);
			
			list.appendChild(listName);
			list.appendChild(listDesc);
			
			var buttons = document.createElement("div");
			buttons.className = "friendDecide";
			
			var accept = document.createElement("a");
			accept.className = "accept";
			accept.onclick = function(){handleButton("/app/messaging/relationship/accept/"+fID, $WSHandleAccept)};
			accept.innerHTML = friendsList.getAttribute("data-accept");
			
			var decline = document.createElement("a");
			decline.className = "decline";
			decline.onclick = function(){handleButton("/app/messaging/relationship/delete/"+fID, $WSHandleDiscard)};
			decline.innerHTML = friendsList.getAttribute("data-decline");
			
			buttons.appendChild(accept);
			buttons.appendChild(decline);
		 
			friend.appendChild(friendPic);
			friend.appendChild(list);
			friend.appendChild(buttons);
			
			newLine.appendChild(friend);
			
			function handleButton(url,callback){
				buttons.hidden = true;
				friendPic.src = ajaxAnimURL;
				checkAndRefreshFriendList();
				stomp.requestReply(url,callback);
			}
			
			var $WSHandleAccept = function(incoming){
				 
				const status = JSON.parse(incoming.body);
				 
				switch(status){
					case "SUCCESS":{
						desc.innerHTML = friendsList.getAttribute("data-friendsFromNow")+fName;
						desc.style.color = "lawngreen";
						counters[1].innerHTML = +counters[1].innerHTML+1;
					} break;
					case "EXCEPTION": {
						desc.innerHTML = friendsList.getAttribute("data-friendsFail");
						desc.style.color = "red";
					} break;
				}
				friendPic.src = fPic;
			};
			
			var $WSHandleDiscard = function(incoming){
				 
				 var status = JSON.parse(incoming.body);
				 
				 desc.innerHTML = (status == "EXCEPTION") ? friendsList.getAttribute("data-friendsFail")
						 : friendsList.getAttribute("data-friendsDeslSuccess");
				 
				 desc.style.color = "red";
				 friendPic.src = fPic;
			 };
			
			return newLine;
		}
	 
	 function clearAll(dom){
			while (dom.firstChild) {
				dom.removeChild(dom.firstChild);
			 }
		}
	 
	 //full friends list logic goes here
	
		function FullFriendsListLogic(){
			
			if (typeof FullFriendsListLogic.instance === "object") {
				console.log("Instance of FullFriendsListLogic was returned.");
				return FullFriendsListLogic.instance;
		     }
			
			var ffl_list = document.querySelector("#ffl_list");
			var sortButtons = document.querySelectorAll(".sortButtons");
			var loadMore = document.querySelector(".ffl_footer button");
			var skip = 0;
			const AJAX_LOAD_ENTRIES = 18;
			
			document.querySelector("#ffl_backButton").onclick 
			= function(){ hideBlock.call(this,fullFriendList,profilePage); }
			
			/*function mainScrollEvent(){
				document.body.onscroll
				 if((window.innerHeight + window.pageYOffset) 
						 >= fullFriendList.offsetHeight){
					 //print(friends);
		          }
			          
		      }*/
			
			sortButtons[0].onclick = function(){
				
				var otherElements = [sortButtons[1],sortButtons[2],sortButtons[3]];
				
				var callbackUp = function(){
					AJAXLoadMoreFriends("ASC","NAME",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("ASC","NAME",skip);};
				};
				
				var callbackDown = function(){
					AJAXLoadMoreFriends("DESC","NAME",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("DESC","NAME",skip);};
				};
				
				sortButtonsLogic(this,otherElements,callbackUp,callbackDown);
				
			} 
			
			sortButtons[1].onclick = function(){
				
				var otherElements = [sortButtons[0],sortButtons[2],sortButtons[3]];
				
				var callbackUp = function(){
					AJAXLoadMoreFriends("ASC","FRIENDSHIP_DATE",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("ASC","FRIENDSHIP_DATE",skip);};
				};
				
				var callbackDown = function(){
					AJAXLoadMoreFriends("DESC","FRIENDSHIP_DATE",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("DESC","FRIENDSHIP_DATE",skip);};
				};
				
				sortButtonsLogic(this,otherElements,callbackUp,callbackDown);
				
			} 

			sortButtons[2].onclick = function(){
	
				var otherElements = [sortButtons[0],sortButtons[1],sortButtons[3]];
				
				var callbackUp = function(){
					AJAXLoadMoreFriends("ASC","RATING",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("ASC","RATING",skip);};
				};
				
				var callbackDown = function(){
					AJAXLoadMoreFriends("DESC","RATING",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("DESC","RATING",skip);};
				};
				
				sortButtonsLogic(this,otherElements,callbackUp,callbackDown);
	
			} 
			
			sortButtons[3].onclick = function(){
				
				var otherElements = [sortButtons[0],sortButtons[1],sortButtons[2]];
				
				var callbackUp = function(){
					AJAXLoadMoreFriends("ASC","POPULARITY",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("ASC","POPULARITY",skip);};
				};
				
				var callbackDown = function(){
					AJAXLoadMoreFriends("DESC","POPULARITY",skip);
					loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadMoreFriends("DESC","POPULARITY",skip);};
				};
				
				sortButtonsLogic(this,otherElements,callbackUp,callbackDown);
	
			} 
			
			function sortButtonsLogic(targetElement,otherElements,callbackUp,callbackDown){
				
				var attribute = targetElement.getAttribute("data-relay");
				skip = 0;
				
				switch(attribute){
				
					case "up": {
						
						targetElement.setAttribute("data-relay","down");
						callbackDown();
						targetElement.style.backgroundColor = "blueviolet";
						
					} break;
					case "down": {
						
						targetElement.setAttribute("data-relay","up");
						callbackUp();
						targetElement.style.backgroundColor = "royalblue";
						
					} break;
					case "disable": {
						
						disableOthers(otherElements);
						targetElement.setAttribute("data-relay","up");
						callbackUp();
						targetElement.style.backgroundColor = "royalblue";
						
					} break;
				}
				
				function disableOthers(e){
					for(var b = 0;b<e.length;b++){
							e[b].setAttribute("data-relay","disable");
							e[b].style.backgroundColor  = "crimson";
						}
				}
			}
			
			function _print(f){
				
				var fragment = document.createDocumentFragment();
				
				var length = (f.length < AJAX_LOAD_ENTRIES) ? f.length : AJAX_LOAD_ENTRIES;
				
				for(var i =0;i<length; i++){
					fragment.appendChild(getFriendDOM(f[i]));
				}
				
				ffl_list.appendChild(fragment);
			}
			
			function getFriendDOM(f){
				
				var fPic = f.friend.userPicURL;
				var fName = f.friend.username;
				var fID = f.friend.id;
				
				var ffl_list_entry = document.createElement("li");
				ffl_list_entry.className= "ffl_list_entry";
				
				var ffl_friend = document.createElement("div");
				ffl_friend.className = "ffl_friend";
				
				var ulInsde = document.createElement("ul");
				
				var ulInsdeFirst = document.createElement("li");
				var ulInsdeFirst_img = document.createElement("img");
				ulInsdeFirst_img.src = fPic;
				
				ulInsdeFirst.appendChild(ulInsdeFirst_img);
				ulInsde.appendChild(ulInsdeFirst);
				
				var ulInsdeSecond = document.createElement("li");
				var ulInsdeSecond_ul = document.createElement("ul");
				var ulInsdeSecond_ul_liFirst = document.createElement("li");
				var ulInsdeSecond_ul_liFirst_a = document.createElement("a");
				
				ulInsdeSecond_ul_liFirst_a.href = fName;
				ulInsdeSecond_ul_liFirst_a.innerHTML = fName+" "+f.friend.popularity;
				
				ulInsdeSecond_ul_liFirst.appendChild(ulInsdeSecond_ul_liFirst_a);
				ulInsdeSecond_ul.appendChild(ulInsdeSecond_ul_liFirst);
				
				var ulInsdeSecond_ul_liSecond = document.createElement("li");
				var ulInsdeSecond_ul_liSecond_label = document.createElement("label");
				ulInsdeSecond_ul_liSecond_label.innerHTML 
				= ffl_list.getAttribute("data-since") +" "+dateParser.parse(new Date(f.since));
				
				ulInsdeSecond_ul_liSecond.appendChild(ulInsdeSecond_ul_liSecond_label);
				ulInsdeSecond_ul.appendChild(ulInsdeSecond_ul_liSecond);
				ulInsdeSecond.appendChild(ulInsdeSecond_ul);
				ulInsde.appendChild(ulInsdeSecond);
				
				var ulInsdeSecond_ul_liThird = document.createElement("li");
				var ulInsdeSecond_ul_liThird_label = document.createElement("label");
				ulInsdeSecond_ul_liThird_label.innerHTML 
				= ffl_list.getAttribute("data-rating") +": "+ f.friend.rating;
				
				ulInsdeSecond_ul_liThird.appendChild(ulInsdeSecond_ul_liThird_label);
				ulInsdeSecond_ul.appendChild(ulInsdeSecond_ul_liThird);
				ulInsdeSecond.appendChild(ulInsdeSecond_ul);
				ulInsde.appendChild(ulInsdeSecond);
				
				if(isSelfRequest){
				
				var ulInsdeThird = document.createElement("li");
				var ulInsdeThird_button = document.createElement("button");
				
				ulInsdeThird_button.innerHTML = ffl_list.getAttribute("data-delete");
				ulInsdeThird_button.onclick = function(){ 
					stomp.requestReply("/app/messaging/relationship/delete/"+fID, $WSHandleDelete);
					ulInsdeFirst_img.src = ajaxAnimURL;
					ulInsdeThird_button.hidden = true;
				};
				ulInsdeThird.appendChild(ulInsdeThird_button);
				ulInsde.appendChild(ulInsdeThird);
				
				}
				
				ffl_friend.appendChild(ulInsde);
				
				ffl_list_entry.appendChild(ffl_friend);
				
				var $WSHandleDelete = function (incoming){
					 
					var status = JSON.parse(incoming.body);
					var b = document.createElement("b");
					
					if(status == "EXCEPTION"){
						b.style.color = "red";
						b.innerHTML = friendsList.getAttribute("data-friendsFail");
					}else{
						counters[1].innerHTML = +counters[1].innerHTML-1;
						b.innerHTML = friendsList.getAttribute("data-friendsDeleteSucc")+" "+fName;
					}
					
					ulInsdeThird.appendChild(b);
					ulInsdeFirst_img.src = fPic;
				 }
				
				return ffl_list_entry;
			}
			
			function AJAXLoadMoreFriends(order,sortBy,skip){
			 	
			 	var url = baseURL+"api/getUserFriends/"+user.id+"?count="+AJAX_LOAD_ENTRIES+
			 	"&accepted=true&sortBy="+sortBy+"&order="+order+"&skip="+skip;
					
				var request = new XMLHttpRequest();
				
				request.open("GET", url, true);
				request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
				
		        var timeout = 10000;
		        var timer = window.setTimeout(function () { request.abort(); console.log("request is aborted");},timeout);
		         
		        request.onreadystatechange = function () {
		                if (request.readyState == 4 && request.status == 200) {
		                    window.clearTimeout(timer);
		                    
		                    var friends = JSON.parse(request.responseText);
		                    
		                    if(friends.length == AJAX_LOAD_ENTRIES){
		                    	if(skip==0) clearAll(ffl_list);
		        				_print(friends);
		        				loadMore.hidden = false;
		                    }else if(friends.length < AJAX_LOAD_ENTRIES){
		                    	if(skip==0) clearAll(ffl_list);
		        				_print(friends);
		                    	loadMore.onclick = void(0);
		                    	loadMore.hidden = true;
		                    }
		                    
		                }else if(request.readyState == 4 && !request.status == 200){
							console.log("request is not OK(200)");
						}
		            }
		         request.send();
		 }
			
			FullFriendsListLogic.instance = this;
			sortButtons[3].onclick();
	};
	
	function FullEventListLogic(){
		
		if (typeof FullEventListLogic.instance === "object") {
			console.log("Instance of FullEventListLogic was returned.");
			return FullEventListLogic.instance;
	     }
		
		var skip = 0;
		const AJAX_LOAD_ENTRIES = 18;
		var loadMore = document.querySelector(".fel_footer button");
		var fel_list = document.querySelector("#fel_list");
		var fel_sortButton = document.querySelector("#fel_sortButton");
		
		document.querySelector("#fel_backButton").onclick 
		= function(){ hideBlock.call(this,fullEventList,profilePage); }
		
		fel_sortButton.onclick = function(){
			skip = 0;
			
			if(this.getAttribute("data-relay") == "up"){
				this.setAttribute("data-relay","down");
				AJAXLoadEvents("DESC",skip);
				loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadEvents("DESC",skip);};
				this.style.backgroundColor = "blueviolet";
			}else{
				this.setAttribute("data-relay","up");
				AJAXLoadEvents("ASC",skip);
				loadMore.onclick = function(){skip += AJAX_LOAD_ENTRIES; AJAXLoadEvents("ASC",skip);};
				this.style.backgroundColor = "royalblue";
			}
			
		}
		
		function print(list){
			
			var fragment = document.createDocumentFragment();
			
			var length = (list.length < AJAX_LOAD_ENTRIES) ? list.length : AJAX_LOAD_ENTRIES;
			
			for(var i =0;i<length; i++){
				fragment.appendChild(getDOM(list[i]));
			}
			
			fel_list.appendChild(fragment);
			
		}
		
		function getDOM(e){
			
			var fel_list_entry = document.createElement("li");
			fel_list_entry.className = "fel_list_entry";
			
			var fel_event = document.createElement("div");
			fel_event.className = "fel_event";
			
			var ul = document.createElement("ul");
			
			var firstLi = document.createElement("li");
			var firstLi_img = document.createElement("img");
			firstLi_img.src = e.by.userPicURL;
			firstLi.appendChild(firstLi_img);
			
			var secondLi = document.createElement("li");
			var secondLi_ul = document.createElement("ul");
			
			var secondLi_ul_firstLi = document.createElement("li");
			secondLi_ul_firstLi.innerHTML = fel_list.getAttribute("data-felFrom");
			var secondLi_ul_firstLi_a = document.createElement("a");
			secondLi_ul_firstLi_a.href = e.by.username;
			secondLi_ul_firstLi_a.innerHTML = e.by.username;
			secondLi_ul_firstLi.appendChild(secondLi_ul_firstLi_a);
			
			var secondLi_ul_secondLi = document.createElement("li");
			secondLi_ul_secondLi.innerHTML = fel_list.getAttribute("data-felSince");
			
			var secondLi_ul_thirdLi = document.createElement("li");
			secondLi_ul_thirdLi.innerHTML = dateParser.parse(new Date(e.date));
			
			secondLi_ul.appendChild(secondLi_ul_firstLi);
			secondLi_ul.appendChild(secondLi_ul_secondLi);
			secondLi_ul.appendChild(secondLi_ul_thirdLi);
			secondLi.appendChild(secondLi_ul);
			
			var thirdLi = document.createElement("li");
			var thirdLi_div = document.createElement("div");
			thirdLi_div.innerHTML = e.content;
			
			thirdLi.appendChild(thirdLi_div);
			
			ul.appendChild(firstLi);
			ul.appendChild(secondLi);
			ul.appendChild(thirdLi);
			
			fel_event.appendChild(ul);
			
			fel_list_entry.appendChild(fel_event);
			
			return fel_list_entry;
		}
		
		function AJAXLoadEvents(order,skip){
			
			var url = baseURL+"api/getUserNotifications/"+user.id+"?count="+AJAX_LOAD_ENTRIES+
		 	"&expired=true&order="+order+"&skip="+skip;
				
			var request = new XMLHttpRequest();
			
			request.open("GET", url, true);
			request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
			
	        var timeout = 10000;
	        var timer = window.setTimeout(function () { request.abort(); console.log("request is aborted");},timeout);
	         
	        request.onreadystatechange = function () {
	                if (request.readyState == 4 && request.status == 200) {
	                    window.clearTimeout(timer);
	                    
	                    var events = JSON.parse(request.responseText);
	                    
	                    if(events.length == AJAX_LOAD_ENTRIES){
	                    	if(skip==0) clearAll(fel_list);
	        				print(events);
	        				loadMore.hidden = false;
	                    }else if(events.length < AJAX_LOAD_ENTRIES){
	                    	if(skip==0) clearAll(fel_list);
	        				print(events);
	                    	loadMore.onclick = void(0);
	                    	loadMore.hidden = true;
	                    }
	                    
	                }else if(request.readyState == 4 && !request.status == 200){
						console.log("request is not OK(200)");
					}
	            }
	         request.send();
			
		}
		
		FullEventListLogic.instance = this;
		fel_sortButton.onclick();
	}

	
}

function _HeaderLogic(){
	
	stomp.addUrlAndCallback("/user/queue/notifications",$WSHandleNotification);
		
	var inboxLink = document.querySelector(".fa-inbox");
	var notifLink = document.querySelector(".fa-comment-o");
	var notificationDropList = document.querySelector(".notificationDropList");
	var notificationList = document.querySelector(".notificationList");
	var notifRef = document.querySelector(".notifRef");
	
	var avilable = true;
	const GLOBAL_INTERVAL = 5000;
	
	var refresh = function(notifications){
		
		var list = [];
		
		for(var i = 0; i<notifications.length; i++){
			list.push(notifications[i].id);
		}
		
		stomp.send("/app/messaging/expireNotification",
				{'Content-Type':'application/json; charset=UTF-8'},
				JSON.stringify(list));
		
		var waitAnim = window.setInterval(function(){
			notifRef.innerHTML += ".";
		},1000);
		
		window.setTimeout(function(){
			
			getMoreNotifications();
			window.clearInterval(waitAnim);
			notifRef.innerHTML = notifRef.getAttribute("data-label");
			
		},GLOBAL_INTERVAL);
		
	};
	
	function getMoreNotifications(){
		
			if(avilable){
				
				avilable = false;
				window.setTimeout(function(){avilable = true;},GLOBAL_INTERVAL);
				
			}else{
				console.log("blocked");
				return;
			}
		
		 	var url = baseURL+"api/getUserNotifications/"+principal.id+"?count=4&expired=false";
				
			var request = new XMLHttpRequest();
			
			request.open("GET", url, true);
			request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
			
	        var timeout = 10000;
	        var timer = window.setTimeout(function () { request.abort(); console.log("request is aborted");},timeout);
	         
	        request.onreadystatechange = function () {
	                if (request.readyState == 4 && request.status == 200) {
	                    window.clearTimeout(timer);
	                    var notifications = JSON.parse(request.responseText);
	                    
	                    if(notifications.length != 0){
	                    	refreshNotifications(notifications);
	                    	notifRef.onclick = function(){
	                    		refresh(notifications);
	                    	};
	                    }else{
	                    	console.log("no notifications has been received");
	                    	clearNotificationList();
	                    	notifRef.onclick = function(){
	                    		getMoreNotifications();
	                    	};
	                    }
	                    
	                }else if(request.readyState == 4 && !request.status == 200){
						console.log("request is not OK(200)");
					}
	            }
	         request.send();
	}

	
	function refreshNotifications(notifications){
		
		while (notificationList.firstChild) {
			notificationList.removeChild(notificationList.firstChild);
		 }
		
		printNotifications(notifications);
	}
	
	function $WSHandleNotification(incoming){
		var notification = JSON.parse(incoming.body);
		showNotification(notification.content,newNotification);
	}
	
	function clearNotificationList(){
		
		while (notificationList.firstChild) {
			notificationList.removeChild(notificationList.firstChild);
		 }
		
		var listEntry = document.createElement("li");
		var div = document.createElement("div");
		div.innerHTML = notificationList.getAttribute("data-empty");
		div.className = "emptyNotificationList";
		
		listEntry.appendChild(div);
		
		notificationList.appendChild(listEntry);
		
		notifLink.style = void(0);
		inboxLink.style =  void(0); 
	}
	
	function printNotifications(n){
		var fragment = document.createDocumentFragment();

		notifLink.style.color = "crimson";  
		
		for(var i = 0; i<n.length; i++){
			
			if(n.notificationType == "NEW_MESSAGE"){
				inboxLink.style.color = "crimson"; 
			}
			
			fragment.appendChild(getNotificationDOM(n[i]));
		}
		notificationList.appendChild(fragment);
	}
	
	function getNotificationDOM(n){
		
		const userPicURL = n.by.userPicURL;
		const username = n.by.username;
		const content = n.content;
		
		var liElement = document.createElement("li");
		
		var divElement = document.createElement("div");
		divElement.className = "notifContent";
		
		var ulInside = document.createElement("ul");
		
		var liInsideFirst = document.createElement("li");
		var userHref = document.createElement("a");
		userHref.href = baseURL+"users/"+username;
		var userImg = document.createElement("img");
		userImg.src = userPicURL;
		userHref.appendChild(userImg);
		
		liInsideFirst.appendChild(userHref);
		
		var liInsideSecond = document.createElement("li");
		var userLabel = document.createElement("label");
		userLabel.innerHTML = username +": "+content;
		liInsideSecond.appendChild(userLabel);
		
		ulInside.appendChild(liInsideFirst);
		ulInside.appendChild(liInsideSecond);
		
		divElement.appendChild(ulInside);
		
		liElement.appendChild(divElement);
		
		return liElement;
	}
	
	notifLink.onclick = function (){
		notificationDropList.hidden = !notificationDropList.hidden;
	};
	
	getMoreNotifications();
}

function _Messages(m,originType,originID){
	
	
	var messagesList = document.querySelector(".messagesList");
	var messageForm = document.querySelector(".messageForm");
	var quickSendArea = document.querySelector("#quickSendArea");
	var quickArea = document.querySelector(".qsBody li textarea");
	var quickPic = document.querySelector(".qsBody li img");
	var quickButton = document.querySelector(".qsBody li a");
	var newMessagesCounter = document.querySelector("#newMessagesCounter");
	var messagesCounter = document.querySelector("#messagesCounter");
	var sendMessageButton = document.querySelector("#sendMessageButton");
	var sendMessageArea = document.querySelector("#sendMessageArea");
	var sortingButtons = document.querySelectorAll(".messageSorting ul li a");
	var messageRequest = document.querySelector(".messageRequest a");
	var requestMore = document.querySelector(".messageRequest button");
	
	(function (){
		stomp.addUrlAndCallback("/topic/messageStream"+originType+originID,
				$WSHandleIncomingMessage);
		
		if(principal){
			document.querySelector(".messageForm ul li img").src = principal.userPicURL;
			document.querySelector(".qsBody li img").src = principal.userPicURL;
		}
		
		
	})();
	
	const REPLY_BORDER_STYLE = "1px solid crimson";
	const REPLY_PADDING_MULTIPLIER = 35;
	const REPLY_MAX_DEPTH = 3;
	const MAX_ALLOWED_CHARACTERS = 1024;
	const AJAX_LOAD_MESSAGES = 9;
	var dump = [];
	var skip = 0;
	
	function printMessages(msg, isNew){
		var fragment = document.createDocumentFragment();
		
		(function reply(array, border, padding, depth){
			
			//depth = (depth > REPLY_MAX_DEPTH) ? REPLY_MAX_DEPTH : depth;
			
			const length = array.length;
			for(var i = 0; i<length; i++){
				
				var msg = array[i];
				
				fragment.append(getMessageDOM(msg,padding,border)); 
				
				if(msg.replies && msg.replies.length != 0){
					
					var deep = depth+1;
					reply(msg.replies,
							REPLY_BORDER_STYLE,
							REPLY_PADDING_MULTIPLIER*deep,deep);
					
				}
				
			}
		})(msg,void(0),void(0),0);
		
		if(!isNew){
			messagesList.append(fragment);
		}else{
			messagesList.prepend(fragment);
		}
		
	}
	
	function getMessageDOM(message, padding, border){
		
		var name = message.author.username;
		var date = message.date;
		var rating = message.rating;
		var pic = message.author.userPicURL;
		var content = message.content;
		var userLink = baseURL+"/users/"+name;
		var msgID = message.id;
		var own = (principal && (message.author.id == principal.id));
		var changed = message.changed;
		var deleted = message.archived;
		
		var bodyLI = document.createElement("li");
		var bodyLI_div = document.createElement("div");
		bodyLI_div.className = "userMsg";
		bodyLI_div.innerHTML = (deleted) ? messagesList.getAttribute("data-deleted") : content;
		
		var msg_ul = document.createElement("li");
		var messageDiv = document.createElement("div");
		messageDiv.className = "message";
		
		var msgMenu = document.createElement("ul");
		msgMenu.className = "messageMenu";
		
		var usernameLI = document.createElement("li");
		var usernameLI_a = document.createElement("a");
		usernameLI_a.href = userLink;
		usernameLI_a.className = name;
		usernameLI_a.innerHTML = name;
		
		usernameLI.append(usernameLI_a);
		
		var dateLI = document.createElement("li");
		var dateLILabel = document.createElement("label");
		dateLILabel.innerHTML = dateParser.parse(new Date(date));
		
		dateLI.append(dateLILabel);
		
		var ratingLI = document.createElement("li");
		
		var ratingLI_value = document.createElement("a");
		ratingLI_value.className = "ratingVal";
		ratingLI_value.innerHTML = rating;
		
		if(!own && !deleted){
		
			var ratingLI_up = document.createElement("a");
			ratingLI_up.className = "up";
			ratingLI_up.innerHTML = messagesList.getAttribute("data-up");
			ratingLI_up.onclick = function (){
				$ButtonUp.call(this, msgID, ratingLI_value);
			};
			ratingLI.appendChild(ratingLI_up);
		}
		
		ratingLI.appendChild(ratingLI_value);
		
		if(!own && !deleted){
			var ratingLI_down = document.createElement("a");
			ratingLI_down.className = "down";
			ratingLI_down.innerHTML = messagesList.getAttribute("data-down");
			ratingLI_down.onclick = function (){
				$ButtonDown(msgID, ratingLI_value);
			};
			ratingLI.appendChild(ratingLI_down);
		}
		
		var editingLI = document.createElement("li");
		
		if(!deleted){
		
		var editingLI_label = document.createElement("label");
		editingLI_label.className = "reply";
		editingLI_label.innerHTML = messagesList.getAttribute("data-reply");
		editingLI_label.onclick = function (){
			
			if(padding && border) $ButtonReply.call(this, msg_ul, padding, border, msgID);
			else $ButtonReply.call(this, msg_ul, REPLY_PADDING_MULTIPLIER, REPLY_BORDER_STYLE, msgID);
		};
		
		editingLI.appendChild(editingLI_label);
		}
		
		if(own && !deleted){
			var correct_label = document.createElement("label");
			correct_label.className = "correct";
			correct_label.innerHTML = messagesList.getAttribute("data-correct");
			correct_label.onclick = function (){
				$ButtonUpdateMessage(bodyLI_div, correct_label, msgID);
			}
			
			var delete_label = document.createElement("label");
			delete_label.className = "delete";
			delete_label.innerHTML = messagesList.getAttribute("data-delete");
			delete_label.onclick = function (){
				$ButtonDeleteMessage(msgID, msg_ul);
			}
			
			editingLI.appendChild(correct_label);
			editingLI.appendChild(delete_label);
		}
		
		msgMenu.appendChild(usernameLI);
		msgMenu.appendChild(dateLI);
		msgMenu.appendChild(editingLI);
		msgMenu.appendChild(ratingLI);
		messageDiv.appendChild(msgMenu);
		
		var msgBody = document.createElement("ul");
		
		var userImageLI = document.createElement("li");
		var userImageLI_a = document.createElement("a");
		var userImageLI_img = document.createElement("img");
		
		userImageLI_a.href = userLink;
		userImageLI_img.src = pic;
		
		userImageLI_a.appendChild(userImageLI_img);
		userImageLI.appendChild(userImageLI_a);
		
		if(changed && !deleted){
			bodyLI_div.appendChild(document.createElement("br"));
			
			var changedInfo = document.createElement("small");
			changedInfo.innerHTML = 
				messagesList.getAttribute("data-changed")+" "+dateParser.parse(new Date(changed));
			
			bodyLI_div.appendChild(changedInfo);
		}
		
		bodyLI.appendChild(bodyLI_div);
		
		msgBody.appendChild(userImageLI);
		msgBody.appendChild(bodyLI);
		
		messageDiv.appendChild(msgBody);
		
		if(arguments[1] && arguments[2]){
			messageDiv.style.borderLeft = border;
			messageDiv.style.paddingLeft = padding+"px";
		}
		
		msg_ul.appendChild(messageDiv);
		
		return msg_ul;
	}
	
	function $ButtonUp(id,rating){
		
		if(!principal) {
	 		showNotification(errNotification.getAttribute("data-accDenied"),errNotification);
	 		return;
	 	}
		
		stomp.requestReply("/app/messaging/messageStream/like/"+id, function(inc){
			if(JSON.parse(inc.body) == "EXCEPTION"){
				showNotification(errNotification.getAttribute("data-requestError"),errNotification);
			}else{
				rating.innerHTML = +rating.innerHTML+1;
			}
		});
		
	}
	
	function $ButtonDown(id,rating){
		
		if(!principal) {
	 		showNotification(errNotification.getAttribute("data-accDenied"),errNotification);
	 		return;
	 	}
		
		stomp.requestReply("/app/messaging/messageStream/dislike/"+id, function(inc){
			if(JSON.parse(inc.body) == "EXCEPTION"){
				showNotification(errNotification.getAttribute("data-requestError"),errNotification);
			}else{
				rating.innerHTML = +rating.innerHTML-1;
			}
		});
		
	}
	
	function $ButtonReply(appendTo, padding, border, id){
		
		if(!principal) {
	 		showNotification(errNotification.getAttribute("data-accDenied"),errNotification);
	 		return;
	 	}
		
		quickSendArea.hidden = false;
		
		if(padding){
			quickSendArea.style.paddingLeft = (padding*2)+"px";
		}
		
		quickButton.onclick = function(){
			
			const content = quickArea.value;
			
			if(!isMessageValid(content)) return;
			
			sendNewMessage(content, id, appendTo, padding, border);
			
			quickSendArea.hidden = true;
			
		};
		
		appendTo.appendChild(quickSendArea);
	
	}
	
	function $ButtonUpdateMessage(area,button,msgID,original){

		var text = messagesList.getAttribute("data-done");
		
		if(button.innerHTML == text){
			
			const content = area.value;
			
			if(!isMessageValid(content)) return;
			
			area.hidden = true;
			button.hidden = true;
			original.innerHTML = content;
			
			var updateForm = {
					messageID:msgID,
					newContent:content
			};
			
			stomp.requestReply("/app/messaging/messageStream/update",function(incoming){
				
				if(JSON.parse(incoming.body) == "EXCEPTION"){
					showNotification(errNotification.getAttribute("data-requestError"),errNotification);
				}
				
			},
			{ messageUpdateFormPayload:JSON.stringify(updateForm) });
		}else{
			var textArea = document.createElement("textarea");
			textArea.className = "editable";
			area.appendChild(textArea);
			button.innerHTML = text;
			button.onclick = function(){
				$ButtonUpdateMessage(textArea,button,msgID,area);
			}
		}
	}
	
	function $ButtonDeleteMessage(msgID, body){
		
		body.hidden = true;
		
		stomp.requestReply("/app/messaging/messageStream/delete/"+msgID,function(incoming){
			
			if(JSON.parse(incoming.body) == "EXCEPTION"){
				
				body.hidden = false;
				showNotification(errNotification.getAttribute("data-requestError"),errNotification);
				
			}
			
		});
		
	}
	
	function sendNewMessage(msg,rep,appendTo, padding, border, prepend){
		
		var MessageForm = {
			origin_type:originType,
			origin_id:originID,
			content:msg,
			reply:rep
		};
		
		stomp.requestReply(
				"/app/messaging/messageStream/post",
				function(incoming){
					var message = JSON.parse(incoming.body);
					
					if(!message){
						showNotification(errNotification.getAttribute("data-requestError"),errNotification);
						return;
					} 
					
					var dom = (padding && border) ? 
							getMessageDOM(message, padding*2, border) : getMessageDOM(message);
					
					if(prepend) appendTo.prepend(dom);
					else appendTo.appendChild(dom);
				},
				{ messageFormPayloadJSON:JSON.stringify(MessageForm) });
	}
	
	function isMessageValid(msg){
		
		if(msg == "" || isThisStringContainsOnlyWhitespaces(msg)) return false;
		
		if(msg.length > MAX_ALLOWED_CHARACTERS){
			
			var alert = messageForm.getAttribute("messageSentError")+" "+MAX_ALLOWED_CHARACTERS;
			showNotification(alert,errNotification);
			
			return false;
		}
		
		function isThisStringContainsOnlyWhitespaces(s){
			
			if(!s.startsWith(" ")) return false;
			
			var array = s.splice();
			
			for(var i = 0; i<array.length; i++){
				
				if(array[i] != " ") return false;
				
			}
			
			return true;
		}
		
		return true;
	}
	
	function $WSHandleIncomingMessage(incoming){
		var msg = JSON.parse(incoming.body);
		
		dump.push(msg);
		
		newMessagesCounter.innerHTML = +newMessagesCounter.innerHTML+1;
		messagesCounter.innerHTML = +messagesCounter.innerHTML+1;
	}
	
	newMessagesCounter.onclick = function(){
		printMessages(dump, true);
		dump = [];
		newMessagesCounter.innerHTML = 0;
	};
	
	if(sendMessageButton) sendMessageButton.onclick = function(){
		
		const content = sendMessageArea.value;
		
		if(isMessageValid(content)) {
			sendNewMessage(content, void(0), messagesList, void(0), void(0), true);
		}
		
	};
	
	function $AJAXLoadMessages(skip,sort,order,notErase){
		var url = baseURL+"api/getMessages/"+originType+"/"+originID+
		"?count="+AJAX_LOAD_MESSAGES+"&archived=IGNORE&sortBy="+sort+"&order="+order+"&skip="+skip;
		
		var request = new XMLHttpRequest();
		
		request.open("GET", url, true);
		request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
		
        var timeout = 10000;
        var timer = window.setTimeout(function () { request.abort(); console.log("request is aborted");},timeout);
         
        request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200) {
                    window.clearTimeout(timer);
                    var messages = JSON.parse(request.responseText);
                    
                    const length = messages.length;
                    requestMore.disabled = false;
            		requestMore.style.backgroundColor = "crimson";
                    
                    if(length != 0){
                    	
                    	if(length<AJAX_LOAD_MESSAGES) {
                    		requestMore.disabled = true;
                    		requestMore.style.backgroundColor = "grey";
                    	}
                    	
                    	if(!notErase){
                    		while (messagesList.firstChild) {
                    		messagesList.removeChild(messagesList.firstChild);
                    		}
                    	}
                    	
                    	printMessages(messages);
                    	
                    }else{
                    	requestMore.disabled = true;
                    	requestMore.style.backgroundColor = "grey";
                    }
                    
                }else if(request.readyState == 4 && !request.status == 200){
					console.log("request is not OK(200)");
				}
            }
         request.send();
	}
	
	sortingButtons[0].onclick = function(){
		relay(sortingButtons[0], [sortingButtons[1]], "RATING");
	}
	
	sortingButtons[1].onclick = function (){
		relay(sortingButtons[1], [sortingButtons[0]], "DATE");
	};
	
	function relay(btn, other, sort){
		
		var relay = btn.getAttribute("data-relay");
		skip = 0;
		
		if(relay == "disable"){
			btn.setAttribute("data-relay", "up");
			$AJAXLoadMessages(skip,sort,"ASC");
			
			requestMore.onclick = function(){
				skip += AJAX_LOAD_MESSAGES;
				$AJAXLoadMessages(skip,sort,"ASC",true);
			};
			
			for(var i = 0; i<other.length; i++){
				other[i].setAttribute("data-relay", "disable");
				other[i].style.backgroundColor = "crimson";
			}
			
			btn.style.backgroundColor = "royalblue";
		}else if(relay == "up"){
			$AJAXLoadMessages(skip,sort,"DESC");
			
			requestMore.onclick = function(){
				skip += AJAX_LOAD_MESSAGES;
				$AJAXLoadMessages(skip,sort,"DESC",true);
			};
			
			btn.setAttribute("data-relay", "down");
			btn.style.backgroundColor = "lawngreen";
		}else{
			btn.setAttribute("data-relay", "up");
			
			$AJAXLoadMessages(skip,sort,"ASC");
			
			requestMore.onclick = function(){
				skip += AJAX_LOAD_MESSAGES;
				$AJAXLoadMessages(skip,sort,"ASC",true);
			};
			
			btn.style.backgroundColor = "royalblue";
		}
	};
	
	requestMore.onclick = function(){
		skip += AJAX_LOAD_MESSAGES;
		$AJAXLoadMessages(skip,"RATING","DESC", true);
	};
	
	printMessages(m);
}


















Application.instance = this;

return {
	UserProfileLogic:_UserProfileLogic,
	HeaderLogic:_HeaderLogic,
	Messages:_Messages
};

}