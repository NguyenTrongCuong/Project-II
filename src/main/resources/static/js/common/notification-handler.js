const employeeEmail = $("#employee-email").val();

let stompClient = null;

let arr = [];

async function findUnseenMessageNotifications() {
	const init = {
		method: "GET"
	};
	
	const response = await fetch("/employee/find-unseen-message-notifications-of-an-employee?employeeEmail=" + employeeEmail, init);
	return response.json();
}

findUnseenMessageNotifications()
	.then(notifications => {
		if(notifications.length > 0) {
			const totalUnseenMessages = notifications.reduce((init, notification) => {
				if(!arr.includes(notification.room.id)) {
					arr.push(notification.room.id);
					++init;
				}
				return init;
			}, 0);
			
			setNumberOfNotifications(totalUnseenMessages);
		}
		connect();
	})
	.catch(errors => {
		console.log(errors);
	});

function setNumberOfNotifications(numberOfNotifications) {
	if(numberOfNotifications !== 0) {
		$(".badge").text(numberOfNotifications);
		$(".badge").css("visibility", "visible");
	}
}

function connect() {
	const webSocketHandshakeUrl = "/employee/web-socket-handshake";
	const sockJSClient = new SockJS(webSocketHandshakeUrl);
	stompClient = Stomp.over(sockJSClient);
	stompClient.connect({}, function() {
		subscribeToNotificationQueue();
	});
}

function subscribeToNotificationQueue() {
	stompClient.subscribe("/queue/notification-of-" + employeeEmail, (response) => {
		const notification = JSON.parse(response.body);
		if(notification.type === "Message Notification") {
			if(!arr.includes(notification.room.id)) {
				arr.push(notification.room.id);
				const numberOfNotifications = parseInt($(".badge").text()) + 1;
				setNumberOfNotifications(numberOfNotifications);
			}
		}
	});
}
























































