const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

const clientEmail = document.querySelector("#client-email").value;
const videoExtension = ["mp4"];

let roomInstance = null;
let messageNotificationInstance;

let isChattingWith = "";
let isInRoom = 0;

let numberOfImages = 0;

let isSure = false;

let flag = 0;
let isNotRated = true;
let score = 0;

let stompClient = null;
let myDropzone = null;


$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});

function connect() {
	const webSocketHandshakeUrl = "/web-socket-handshake";
	const sockJSClient = new SockJS(webSocketHandshakeUrl);
	stompClient = Stomp.over(sockJSClient);
	stompClient.connect({}, function() {
		subscribeToMessageNotificationQueue();
	});
}

function subscribeToMessageNotificationQueue() {
	messageNotificationInstance = stompClient.subscribe("/queue/message-notification-of-" + clientEmail, function(response) {
		const room = JSON.parse(response.body);
		
		isChattingWith = room.employeeDTO.email;
		isInRoom = room.id;
		
		createChatRoom(room);
		
		subscribeToMessageCoverQueue(room.id);
		
		initializeDropzone();

			
	});
}

function createChatRoom(room) {
	
	$("#cover-element-1").empty();
	$("#cover-element-1").append("<h2>" + room.clientRoomName + "</h2>");
	$("#cover-element-1").append("<div id='container-element'></div>");
	$("#container-element").append("<div id='content-element'></div>");
	$("#cover-element-1").append("<div id='answer-element'></div>");
	$("#answer-element").append("<div id='byte-element'></div>");
	$("#answer-element").append("<div><textarea id='text-element'></textarea></div>");
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
	roomInstance = stompClient.subscribe("/topic/message-cover-of-" + roomId, function(response) {
			const messages = JSON.parse(response.body);
			for(const message of messages) {
				if(message.senderEmail === clientEmail) {
					$("#content-element").append("<div class='content-element-child-me' title='" + message.sentAt + "' id='message-id-" + message.id + "'><div class='content-element-child-me-image'><img src='" + message.senderAvatarLink + "' class='symbol' title='" + message.senderFullName + "'/></div><div class='me-content-element'>" + message.content + "</div></div>");
				}
				else {
					$("#content-element").append("<div class='content-element-child-others' title='" + message.sentAt + "' id='message-id-" + message.id + "'><div class='content-element-child-others-image'><img src='" + message.senderAvatarLink + "' class='symbol' title='" + message.senderFullName + "'/></div><div class='others-content-element'>" + message.content + "</div></div>");
				}
			}
			
		}, 
		{
			"id": "queue-of-" + clientEmail + "-with-room-" + roomId,
			"auto-delete": false,
			"durable": true
		});
}


const counsellorConnectingElement = document.querySelector("#counsellor-connecting-element");

counsellorConnectingElement.onclick = function(event) {
	$("#left-conversation-element").css("display", "inline");
	$("#give-feedback-element").css("display", "inline");
	counsellorConnectingElement.style.display = "none";
	
	$.ajax({
		url: "/find-counsellor",
		type: "GET",
		error: function() {
			alert("Something went wrong");
		}
	});
}

const giveFeedbackElement = document.querySelector("#give-feedback-element");

giveFeedbackElement.onclick = function(event) {
	isNotRated = true;
	score = 0;
	if(flag++ % 2 === 0) {
        $(".row").append('<div class="column column-element-2"><h2>Feedback</h2><span id="feedback-error-message"></span><div id="star-rating-element"><span class="fa fa-star" id="star-1" onclick="addColor(1)" ondblclick="handleDbclick()"></span><span class="fa fa-star" id="star-2" onclick="addColor(2)" ondblclick="handleDbclick()"></span><span class="fa fa-star" id="star-3" onclick="addColor(3)" ondblclick="handleDbclick()"></span><span class="fa fa-star" id="star-4" onclick="addColor(4)" ondblclick="handleDbclick()"></span><span class="fa fa-star" id="star-5" onclick="addColor(5)" ondblclick="handleDbclick()"></span></div><div id="text-rating-element"><textarea id="text-rating-content-element"></textarea></div><div id="rating-button-element"><button id="rating-button-child-element" onclick="sendFeedback()">Send</button></div></div>');
        $(".column-element-1").css("width", "70%");
        $(".content-element-child-me").css("margin-left", "585px");
    }
    else {
        $(".column-element-2").remove();
        $(".column-element-1").css("width", "100%");
        $(".content-element-child-me").css("margin-left", "1040px");
    }
}

function addColor(order) {
    isNotRated = false;
    score = order;
    for(let i = 0; i < order; ++i) {
        $("#star-" + (i + 1)).css("color", "orange");
    }
    for(let i = 5; i > order; --i) {
        $("#star-" + i).css("color", "black");
    }
}

function handleDbclick() {
	isNotRated = true;
    score = 0;
    $(".fa-star").css("color", "black");
}

function sendFeedback() {
	const textRating = $("#text-rating-content-element").val().trim(); 
	if(score === 0 && textRating === "") {
		$("#feedback-error-message").text("Please leave feedback by one of these ways: star-rating, text-rating or both");
	}
	else {
		const feedback = {
			textFeedback: textRating,
			starFeedback: score === 0 ? "6" : score
		};
		$.ajax({
			url: "/give-feedback/" + isInRoom,
			type: "PUT",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(feedback),
			success: function() {
				alert("Thanks for your feedback ^-^");
			},
			error: function() {
				alert("Something went wrong");
			}
		});
		
		$(".column-element-2").remove();
        $(".column-element-1").css("width", "100%");
        $(".content-element-child-me").css("margin-left", "1040px");
        
		giveFeedbackElement.style.display = "none";
		isNonRated = true;
		score = 0;
		
	}
}

document.onkeydown = function(event) {
	if(event.code === "Enter") {
		event.preventDefault();
		if($("#content-element").length) {
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
						url: "/send-messages-to/" + isChattingWith + "/" + isInRoom,
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
	}
}

let leftConversationElement = document.querySelector("#left-conversation-element");

leftConversationElement.onclick = function(event) {
	let message = "Are u sure to left this conversation?";
	if($("#give-feedback-element").css("display") !== "none") {
		message += " (please leave your feedback before leaving if you can ^-^)";
	}
	const isConfirmed = confirm(message);
	if(isConfirmed) {
		roomInstance.unsubscribe();
    	$.ajax({
    		url: "/close-room/" + isInRoom,
    		type: "PUT",
    		success: function() {
    			$("#counsellor-connecting-element").css("display", "inline");
    			$("#left-conversation-element").css("display", "none");
    			$("#give-feedback-element").css("display", "none");
    			reset();
    			isInRoom = 0;
    			isChattingWith = "";
    			roomInstance = null;
    		},
    		error: function() {
    			alert("Something went wrong");
    		}
    	});
	}
}

function reset() {
	$("#cover-element-1").empty();
	$("#cover-element-1").append("<p>Please click the \"Connect to a counsellor\" button to get support from our counsellors</p>");
}

const links = $(".topnav").find(".link");

for(const link of links) {
	link.onclick = function(event) {
        if(!isSure && roomInstance !== null) {
            event.preventDefault();
            isSure = confirm("Changes you made may not be saved");
            if(isSure) {
            	roomInstance.unsubscribe();
            	messageNotificationInstance.unsubscribe();
            	$.ajax({
            		url: "/close-room/" + isInRoom,
            		type: "PUT",
            		success: function() {
            			go(event.target);
            		},
            		error: function() {
            			alert("Something went wrong");
            		}
            	});
            }
        }
    }
}

function go(target) {
	if(target.id === "sign-out-button") {
		const signOutButton = document.querySelector("#sign-out-button");
		signOutButton.parentElement.submit();
	}
	else target.click();
}

connect();

































