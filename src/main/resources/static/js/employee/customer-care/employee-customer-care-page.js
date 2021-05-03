const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
const employeeEmail = document.querySelector("#employee-email").value;

let stompClient = null;
let myDropzone = null;

let isSure = false;

let statusMap = new Map();
let messageCoverQueueMap = new Map();
let messageNotificationQueue;

let isChattingWith = "";
let isInRoom = 0;

let numberOfImages = 0;

$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});

function connect() {
	const webSocketHandshakeUrl = "/employee/web-socket-handshake";
	const sockJSClient = new SockJS(webSocketHandshakeUrl);
	stompClient = Stomp.over(sockJSClient);
	stompClient.connect({}, function() {
		setStatusMap();
		connectToJoinedRooms();
		subscribeToMessageNotificationQueue();
		subscribeToNotificationQueue();
	});
}

function subscribeToNotificationQueue() {
	stompClient.subscribe("/queue/notification-of-" + employeeEmail, (response) => {
		const notification = JSON.parse(response.body);
		
		if(notification.type === "Message Notification") {
			if(isInRoom === notification.room.id) {
				updateMessageNotificationIsSeenOfANotification(notification.id);
			}
		}
	});
}

function updateMessageNotificationIsSeenOfANotification(id) {
	$.ajax({
		url: "/employee/update-message-notification-is-seen-of-a-notification/" + id,
		type: "PUT",
		error: () => {
			alert("Something went wrong!!!");
		}
	});
}

function setStatusMap() {
	const temp = document.querySelectorAll(".room-element");
	const statuses = Array.from(temp);
	for(const status of statuses) {
		statusMap.set(parseInt(status.value), parseInt(status.getAttribute("data-status")));
	}
}

function connectToJoinedRooms() {
	const joinedRooms = document.querySelectorAll(".joined-room");
	if(joinedRooms !== null) {
		const joinedRoomsArr = Array.from(joinedRooms);
		for(const element of joinedRoomsArr) {
			subscribeToMessageCoverQueue(element.value);
		}
	}
}

function subscribeToMessageNotificationQueue() {
	messageNotificationQueue = stompClient.subscribe("/queue/message-notification-of-" + employeeEmail, function(response) {
		const notification = JSON.parse(response.body);
		if(notification.type === "Connecting") {
			const room = notification.content;
			if(!$("#conversation-" + room.id).length) {
				addNewConversation(".column-container", room);
				subscribeToMessageCoverQueue(room.id);
			}
		}
		else {
			handleClosingNotification(notification.content);
		}
	});
}

function handleClosingNotification(roomId) {
	unsubscribeToMessageCoverQueue(roomId);
	setStatusOfRoom(roomId);
	renameConversation(roomId);
}

function setStatusOfRoom(roomId) {
	statusMap.set(parseInt(roomId), 1);
}

function renameConversation(roomId) {
	const conversationName = $("#conversation-" + roomId).find("b")[0];
	conversationName.innerText += " (Closed)";
}

function unsubscribeToMessageNotificationQueue() {
	messageNotificationQueue.unsubscribe();
}

function unsubscribeToMessageCoverQueues() {
	for(const messageCoverQueue of messageCoverQueueMap.values()) {
		messageCoverQueue.unsubscribe();
	}
}

function unsubscribeToMessageCoverQueue(roomId) {
	const messageCoverQueue = messageCoverQueueMap.get(parseInt(roomId));
	messageCoverQueue.unsubscribe();
}

function addNewConversation(selector, room) {
	let latestMessage = "";
	if(room.messages.length > 0) {
		const message = room.messages[room.messages.length - 1];
		latestMessage = message.senderFullName + ": " + message.content;
	}
	$(selector).prepend("<div class='notification-element' id='conversation-" + room.id + "'></div>");
	$("#conversation-" + room.id).append("<div class='conversation-element conversation-image-element'><img src='" + room.clientDTO.avatarLink + "' class='image-element'/></div>");
	$("#conversation-" + room.id).append("<div class='conversation-element conversation-content-element'><b style='font-size: 20px;'>" + room.employeeRoomName + "</b><p style='text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>" + latestMessage + "</p></div>");
	$("#conversation-" + room.id).on("click", {roomId: room.id, receiverEmail: room.clientDTO.email}, getMessages);
}

function getMessages(options) {
	let roomId = typeof options === "number" ? options : options.data.roomId;
	isChattingWith = typeof options === "object" ? options.data.receiverEmail : $("#conversation-" + roomId).attr("data-receiverEmail");
	isInRoom = roomId;
	$.ajax({
		url: "/employee/get-messages/" + roomId,
		type: "GET",
		success: function(messages) {
			
			addChatRoom(roomId);
			
			for(const message of messages) {
				showMessage(message);
			}
			
			updateMessageNotificationIsSeenOfARoom(roomId);
			
			setMessageSeen(roomId);
			
			updateMessagesIsSeenOfRoom(roomId);
			
		},
		error: function() {
			alert("Something went wrong");
		}
	});
}

function updateMessageNotificationIsSeenOfARoom(roomId) {
	$.ajax({
		url: "/employee/update-message-notification-is-seen-of-a-room/" + roomId,
		type: "PUT",
		error: (errors) => {
			alert("Something went wrong");
		}
	});
}

function setMessageSeen(roomId) {
	let latestMessage = $("#conversation-" + roomId).find("p")[0];
	
	latestMessage.classList.remove("new-message");
}

function updateMessagesIsSeenOfRoom(roomId) {
	$.ajax({
		url: "/employee/update-message-is-seen-of-room/" + roomId,
		type: "PUT",
		error: (errors) => {
			alert("Something went wrong");
		}
	});
}

function addChatRoom(roomId) {
	const bElement = $("#conversation-" + roomId).find("b").get(0);
	const roomName = bElement.innerText;
	$(".column-element-2").empty();
	$(".column-element-2").append("<h2>" + roomName + "</h2>");
	$(".column-element-2").append("<div id='container-element'></div");
	$("#container-element").append("<div id='content-element'></div>");
	$(".column-element-2").append("<div id='answer-element'></div");
	$("#answer-element").append("<div id='byte-element'></div>");
	$("#answer-element").append("<div><textarea id='text-element'></textarea></div>");
	if(myDropzone === null) {
		initializeDropzone();
	}
}

function initializeDropzone() {
	myDropzone = new Dropzone("div#byte-element",
            {url: "/file/post",
             thumbnailWidth: "80",
             thumbnailHeight: "50",
             addRemoveLinks: true,
             autoProcessQueue: false
            });
	
	myDropzone.on("addedfile", function(file) {
		const fileExtension = getExtension(file.name);
		let fileLink = "";
		if (videoExtension.includes(fileExtension)) {
			fileLink = "../../../images/video-thumbnail.png";
		}
		else if (!file.type.match(/image.*/)) {
			fileLink = "../../../images/text-thumbnail.png";
		}
		myDropzone.emit("thumbnail", file, fileLink);
		this.element.querySelectorAll(".dz-preview")[numberOfImages++].title = file.name;
	});
	
	myDropzone.on("removedfile", function() {
		--numberOfImages;
	});
}

function getExtension(fileName) {
	return fileName.split(".").pop();
}

function subscribeToMessageCoverQueue(roomId) {
	if(messageCoverQueueMap.get(parseInt(roomId)) === undefined) {
		const messageCoverQueueInstance = stompClient.subscribe("/topic/message-cover-of-" + roomId, function(response) {
			const messages = JSON.parse(response.body);
			showLatestMessage(messages[messages.length - 1], messages[0].roomId);
			if(messages[0].senderEmail !== employeeEmail) {
				moveConversationElement(messages[0].roomId);
			}
			if($("#content-element").length) {
				if(messages[0].roomId === isInRoom) {
					for(const message of messages) {
						if(!$("#message-id-" + message.id).length) {
							showMessage(message);
						}
					}
				}
			}
		}, 
		{
			"id": "queue-of-" + employeeEmail + "-with-room-" + roomId,
			"auto-delete": false,
			"durable": true,
		});
		
		messageCoverQueueMap.set(parseInt(roomId), messageCoverQueueInstance);
	}
	
	if(statusMap.get(parseInt(roomId)) === undefined) {
		statusMap.set(parseInt(roomId), 0);
	}
	
}

function showLatestMessage(message, roomId) {
	let latestMessage = $("#conversation-" + roomId).find("p")[0];
	let senderFullName = message.senderEmail === employeeEmail ? "You" : message.senderFullName;
	if(message.isBinary === 1) {
		latestMessage.innerText = senderFullName + " has sent you a file"; 
	}
	else latestMessage.innerHTML = senderFullName + ": " + message.content;
	
	if(message.senderEmail !== employeeEmail) {
		if(message.roomId === isInRoom) {
			latestMessage.classList.remove("new-message");
			updateMessageIsSeen(message.id);
		}
		else {
			latestMessage.classList.add("new-message");
		}
	}
}

function updateMessageIsSeen(messageId) {
	$.ajax({
		url: "/employee/update-message-is-seen/" + messageId,
		type: "PUT",
		error: (errors) => {
			alert("Something went wrong");
		}
	});
}

function moveConversationElement(roomId) {
	const conversation = $("#conversation-" + roomId).clone(true);
	$("#conversation-" + roomId).remove();
	$(".column-container").prepend(conversation);
}

function showMessage(message) {
	if(message.senderEmail === employeeEmail) {
		$("#content-element").append("<div class='content-element-child-me' title='" + message.sentAt + "' id='message-id-" + message.id + "'><div class='content-element-child-me-image'><img src='" + message.senderAvatarLink + "' class='symbol' title='" + message.senderFullName + "'/></div><div class='me-content-element'>" + message.content + "</div></div>");
	}
	else {
		$("#content-element").append("<div class='content-element-child-others' title='" + message.sentAt + "' id='message-id-" + message.id + "'><div class='content-element-child-others-image'><img src='" + message.senderAvatarLink + "' class='symbol' title='" + message.senderFullName + "'/></div><div class='others-content-element'>" + message.content + "</div></div>");
	}
}

document.onkeydown = function(event) {
	if(event.code === "Enter") {
		event.preventDefault();
		if($("#content-element").length) {
			if(statusMap.get(parseInt(isInRoom)) === 0) {
				const temp = $("#text-element").val();
				const textContent = temp.trim();
				const files = myDropzone.getAcceptedFiles();
				if(textContent !== "" || files.length !== 0) {
					let size = 0;
					for(const file of files) {
						size += file.size;
					}
					if(size > 104857600) {
						myDropzone.removeAllFiles();
			            alert("The total files' size can not exceed 100MB, please reduce the number of files");
					}
					else {
						const data = new FormData();
						for(const file of files) {
							data.append("files", file);
						}
						data.append("textContent", textContent);
						$.ajax({
							url: "/employee/send-messages-to/" + isChattingWith + "/" + isInRoom,
							type: "POST",
							enctype: "multipart/form-data",
							data: data,
							processData: false,
					        contentType: false,
							error: function() {
								alert("Something went wrong");
							}
						});
						$("#text-element").val("");
						myDropzone.removeAllFiles();
					}
				}
			}
			else alert("The client has closed this conversation");
		}
	}
};

//const links = $(".topnav").find(".link");
//
//for(const link of links) {
//	link.onclick = function(event) {
//		if(!isSure) {
//            event.preventDefault();
//            isSure = confirm("Are you sure to leave?");
//            if(isSure) {
//            	unsubscribeToMessageNotificationQueue();
//            	unsubscribeToMessageCoverQueues();
//            	go(event.target);
//            }
//        }
//	};
//}
//
//function go(target) {
//	target.click();
//}

connect();





















































