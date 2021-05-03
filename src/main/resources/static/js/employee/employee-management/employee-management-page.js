class Stack { 
  
    constructor() { 
        this.items = []; 
    } 

    push(element) { 
        this.items.push(element); 
    }
    
    pop() { 
        if (this.items.length == 0) 
            return "Underflow"; 
        return this.items.pop(); 
    } 

    peek() { 
        return this.items[this.items.length - 1]; 
    } 

    printStack() { 
        var str = ""; 
        for (var i = 0; i < this.items.length; i++) 
            str += this.items[i] + " "; 
        return str; 
    } 
  
}

let rootChartConfig;
let tempChartConfig;

const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

let statisticStack = new Stack();
let managementStack = new Stack();

let availableSource = [];

let ctx;
let myChart;

let isValid;

$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});

const managementIconHandler = () => {
    $(".column2").empty();
    $(".column2").append("<h2>Counsellors management</h2>");
    $(".column2").append("<div id='management-container'></div>");
    $("#management-container").append("<form id='consultations-management-form'></form>");
    $("#consultations-management-form").append("<label for='time-type'>View statistic by: </label>");
    $("#consultations-management-form").append("<select name='time' id='time-type'><option value='month'>Month</option><option value='day'>Day</option></select><br/><br/>");
    $("#consultations-management-form").append("<div id='time-content'></div>");
    $("#time-type").change((event) => {
        handleTimeTypeChangeOfConsultationsManagement(event.target.value);
    });
};

const chartIconHandler = () => {
    $(".column2").empty();
    $(".column2").append("<h2>View statistic</h2>");
    $(".column2").append("<div id='statistic-button-container'></div>");
    $("#statistic-button-container").append("<div class='statistic-button'><button id='star-feedback-consultations-statistic-button'>Statistics of consultations categorized by star rating</button></div>");
    $("#statistic-button-container").append("<div class='statistic-button'><button id='top-n-counsellors-statistic-button'>Statistics of counsellors' working efficiency</button></div>");
    $("#star-feedback-consultations-statistic-button").click((event) => {
        statisticStack.push(chartIconHandler);
        viewStarFeedbackConsultationsStatistic();
    });
    $("#top-n-counsellors-statistic-button").click((event) => {
        statisticStack.push(chartIconHandler);
        viewTopNCounsellors();
    });
};

const viewTopNCounsellors = () => {
    $(".column2").empty();
    $(".column2").append("<h2>Form</h2>");
    $(".column2").append("<div id='exit-button-container'><button id='exit-button'>X</button></div>");
    $(".column2").append("<div class='statistic-form' id='top-n-statistic-form-container'></div>");
    $("#top-n-statistic-form-container").append("<form id='top-n-statistic-form'></form>");
    $("#top-n-statistic-form").append("<label for='time-type'>View statistic by: </label>");
    $("#top-n-statistic-form").append("<select name='time' id='time-type'><option value='month'>Month</option><option value='day'>Day</option></select><br/><br/>");
    $("#top-n-statistic-form").append("<div id='time-content'></div>");
    $("#time-type").change((event) => {
        handleTimeTypeChangeOfViewTopNCounsellors(event.target.value);
    });
    $("#exit-button").click((event) => {
        handleViewStatisticExit();
    });
};

const viewStarFeedbackConsultationsStatistic = () => {
    $(".column2").empty();
    $(".column2").append("<h2>Form</h2>");
    $(".column2").append("<div id='exit-button-container'><button id='exit-button'>X</button></div>");
    $(".column2").append("<div class='statistic-form' id='consultations-statistic-form-container'></div>");
    $("#consultations-statistic-form-container").append("<form id='consultations-statistic-form'></form>");
    $("#consultations-statistic-form").append("<label for='time-type'>View statistic by: </label>");
    $("#consultations-statistic-form").append("<select name='time' id='time-type'><option value='month'>Month</option><option value='day'>Day</option></select><br/><br/>");
    $("#consultations-statistic-form").append("<div id='time-content'></div>");
    $("#time-type").change((event) => {
        handleTimeTypeChangeOfViewStarFeedbackConsultationsStatistic(event.target.value);
    });
    $("#exit-button").click((event) => {
        handleViewStatisticExit();
    });
};

const handleTimeTypeChangeOfViewStarFeedbackConsultationsStatistic = (time) => {
    $("#time-content").empty();
    if(time === "day") {
        $("#time-content").append("<label for='time-content'>Date: </label>");
        $("#time-content").append("<input type='date' id='day-time-content'/> <i class='fas fa-times-circle date-error'></i><br/><br/>");///1
    }
    else if(time === "month") {
        $("#time-content").append("<label for='time-content'>Month-Year: </label>");
        $("#time-content").append("<input type='number' min='1' max='12' id='month-time-content' required/> - ");
        $("#time-content").append("<input type='number' id='year-time-content' required/> <i class='fas fa-times-circle month-year-error'></i><br/><br/>");
    }
    $("#time-content").append("<input type='submit' id='view-star-feedback-consultations-statistic' value='View'/>");
    
    $("#day-time-content").blur(event => {
    	checkDayTimeContent();
    });
    
    $("#month-time-content").blur(event => {
    	checkMonth();
    });
    
    $("#year-time-content").blur(event => {
    	checkYear();
    });
    
    $("#view-star-feedback-consultations-statistic").click((event) => {
    	event.preventDefault();
    	performViewStarFeedbackConsultationsStatistic(time);
    });
    
};

const handleTimeTypeChangeOfConsultationsManagement = (time) => {
    $("#time-content").empty();
    $("#time-content").append("<label for='counsellor-id'>Name: </label>");
    $("#time-content").append("<input type='text' id='counsellor-id' required/> ");
    $("#time-content").append("<i class='fas fa-times-circle id-error'></i><br/><br/>");
    if(time === "day") {
        $("#time-content").append("<label for='time-content'>Date: </label>");
        $("#time-content").append("<input type='date' id='day-time-content'/> <i class='fas fa-times-circle date-error'></i><br/><br/>");///2
    }
    else if(time === "month") {
        $("#time-content").append("<label for='time-content'>Month-Year: </label>");
        $("#time-content").append("<input type='number' min='1' max='12' id='month-time-content' required/> - ");
        $("#time-content").append("<input type='number' id='year-time-content' required/> <i class='fas fa-times-circle month-year-error'></i><br/><br/>");
    }
    $("#time-content").append("<input type='submit' id='view-consultations-button' value='View'/>");
    
    $("#day-time-content").blur(event => {
    	checkDayTimeContent();
    });
    
    $("#month-time-content").blur(event => {
    	checkMonth();
    });
    
    $("#year-time-content").blur(event => {
    	checkYear();
    });
    
    $("#view-consultations-button").click((event) => {
    	event.preventDefault();
    	managementStack.push(managementIconHandler);
    	performViewConsultations(time);
    });
    
    $("#counsellor-id").on("input", (event) => {
    	getSuggestion(event.target.value);
    });

    $("#counsellor-id").blur((event) => {
        checkCounsellorId();
    });
};

const handleTimeTypeChangeOfViewTopNCounsellors = (time) => {
    $("#time-content").empty();
    $("#time-content").append("<label for='star'>N star consultations: </label>");
    $("#time-content").append("<select id='star'><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select><br/><br/>");
    $("#time-content").append("<label for='top-n-counsellors'>Top N counsellors: </label>");
    $("#time-content").append("<select id='top-n-counsellors'><option value='5'>5</option><option value='6'>6</option><option value='7'>7</option><option value='8'>8</option><option value='9'>9</option><option value='10'>10</option></select><br/><br/>");
    if(time === "day") {
        $("#time-content").append("<label for='time-content'>Date: </label>");
        $("#time-content").append("<input type='date' id='day-time-content'/> <i class='fas fa-times-circle date-error'></i><br/><br/>");///3
    }
    else if(time === "month") {
        $("#time-content").append("<label for='time-content'>Month-Year: </label>");
        $("#time-content").append("<input type='number' min='1' max='12' id='month-time-content' required/> - ");
        $("#time-content").append("<input type='number' id='year-time-content' required/> <i class='fas fa-times-circle month-year-error'></i><br/><br/>");
    }
    $("#time-content").append("<input type='submit' id='view-top-n-counsellors-button' value='View'/>");
    
    $("#day-time-content").blur(event => {
    	checkDayTimeContent();
    });
    
    $("#month-time-content").blur(event => {
    	checkMonth();
    });
    
    $("#year-time-content").blur(event => {
    	checkYear();
    });
    
    $("#view-top-n-counsellors-button").click((event) => {
    	event.preventDefault();
    	performViewTopNCounsellors(time);
    });
};

const handleViewStatisticExit = () => {
	$(".column3").empty();
	$(".column3").append("<h2>Header</h2>");
    const prev = statisticStack.pop();
    prev();
};

function checkDayTimeContent() {
	isValid = false;
	if($("#day-time-content").val() === "") {
		$(".date-error").css("visibility", "visible");
	}
	else {
		isValid = true;
		$(".date-error").css("visibility", "hidden");
	}
	return isValid;
}

function getSuggestion(regexp) {
	const searchRequest = {
		regexp: regexp	
	};
	
	$.ajax({
		url: "/employee/search-by-regexp",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(searchRequest),
		success: (employees) => {
			if(employees.result.length > 0) {
				availableSource = employees.result.map((employee) => employee.fullName + " (" + employee.email + ")");
				$("#counsellor-id").autocomplete({
			        source: availableSource
			    });
			}
		},
		error: (errors) => {
			alert("Something went wrong");
		}
	});
	
}

function performViewConsultations(time) {
	let flag = time === "day" ? checkDayTimeContent() : (checkMonth() && checkYear());
	if(checkCounsellorId() && flag) {
		const counsellorId = $("#counsellor-id").val();
		const employeeEmailComponents = counsellorId.split(" ");
		const lastComponent = employeeEmailComponents[employeeEmailComponents.length - 1];
		const employeeEmail = lastComponent.slice(1, lastComponent.length - 1);
		
		let joinedInRoomsRequest = createStatisticRequest(time);
		
		
		joinedInRoomsRequest.employeeEmail = employeeEmail;
		
		$.ajax({
			url: "/employee/get-joined-in-rooms",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(joinedInRoomsRequest),
			success: (rooms) => {
				renderRooms(rooms);
			},
			error: (errors) => {
				alert("Something went wrong");
			}
		});
	}
	
}

function performViewTopNCounsellors(time) {
	let flag = time === "day" ? checkDayTimeContent() : (checkMonth() && checkYear());
	if(flag) {
		const star = $("#star").val();
		const topNCounsellors = $("#top-n-counsellors").val();
		
		let statisticRequest = createStatisticRequest(time);
		
		statisticRequest.star = star;
		statisticRequest.topNCounsellor = topNCounsellors;
		
		$.ajax({
			url: "/employee/get-top-n-counsellors",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(statisticRequest),
			success: (statisticResponse) => {
				createChart(statisticResponse);
			},
			error: (errors) => {
				alert("Something went wrong");
			}
		});
	}
}

function performViewStarFeedbackConsultationsStatistic(time) {
	let flag = time === "day" ? checkDayTimeContent() : (checkMonth() && checkYear());
	if(flag) {
		let statisticRequest = createStatisticRequest(time);
		
		$.ajax({
			url: "/employee/get-consultations-categorized-by-star-feedback",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(statisticRequest),
			success: (statisticResponse) => {
				createChart(statisticResponse);
			},
			error: (errors) => {
				alert("Something went wrong");
			}
		});
	}
}

function renderRooms(rooms) {
	$(".column2").empty();
	$(".column2").append("<h2>Consultations</h2>");
	$(".column2").append("<div id='exit-button-container'><button id='management-exit-button'>X</button></div>");
	$(".column2").append("<div id='consultations-container'></div>");
	if(rooms.length > 0) {
		rooms.forEach(room => {
			const messages = room.messages;
			const lastMessage = messages.length > 0 ? messages[messages.length - 1].senderFullName + ": " + messages[messages.length - 1].content : "";
			$("#consultations-container").append(`<div class='consultations-child' id='consultations-child-${room.id}'></div>`);
			$("#consultations-child-" + room.id).append(`<div class='conversation-element conversation-image-element'><img src='${room.clientDTO.avatarLink}' class='image-element'/></div>`);
			$("#consultations-child-" + room.id).append(`<div class='conversation-element conversation-content-element'><b style='font-size: 20px;'>${room.employeeRoomName}</b><p title='${lastMessage}' style='text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>${lastMessage}</p><div>`);
			$("#consultations-child-" + room.id).click((event) => {
				renderMessages(room.messages, room.employeeRoomName, room.employeeDTO.email, room.starFeedback, room.textFeedback);
			});
		});
		
	}
	else $("#consultations-container").append("Empty");
	
	$("#management-exit-button").click((event) => {
		handleManagementExit();
	});
}

//function getMessages(room) {
//	if(!$("#content-element").length) {
//		$.ajax({
//			url: "/employee/get-messages/" + room.id,
//			type: "GET",
//			success: (messages) => {
//				renderMessages(room.messages, room.employeeName, room.employeeDTO.email, room.starFeedback, room.textFeedback);
//			},
//			error: (error) => {
//				alert("Something went wrong");
//			}
//		});
//	}
//}

function renderMessages(messages, roomName, employeeEmail, starFeedback, textFeedback) {
	$(".column3").empty();
	$(".column3").append(`<h2>${roomName}</h2>`);
	$(".column3").append("<span>Star rating: </span>");
	$(".column3").append("<div id='star-rating-container'></div><br/><br/>");
	
	for(let i = 1; i <= 5; ++i) {
		$("#star-rating-container").append('<span class="fa fa-star" id="star-' + i + '"></span>');
		if(starFeedback !== 6 && i <= starFeedback) {
			$("#star-" + i).css("color", "orange");
		}
	}
	
	$(".column3").append("<span>Feedback: </span>");
	$(".column3").append(`<div id='text-feedback-container'>${textFeedback}</div><br/><br/>`);
	
	$(".column3").append("<div id='content-element'></div>");
	messages.forEach(message => {
		if(message.senderEmail === employeeEmail) {
			$("#content-element").append("<div class='content-element-child-me' title='" + message.sentAt + "' id='message-id-" + message.id + "'><div class='content-element-child-me-image'><img src='" + message.senderAvatarLink + "' class='symbol' title='" + message.senderFullName + "'/></div><div class='me-content-element'>" + message.content + "</div></div>");
		}
		else {
			$("#content-element").append("<div class='content-element-child-others' title='" + message.sentAt + "' id='message-id-" + message.id + "'><div class='content-element-child-others-image'><img src='" + message.senderAvatarLink + "' class='symbol' title='" + message.senderFullName + "'/></div><div class='others-content-element'>" + message.content + "</div></div>");
		}
	});
}

function handleManagementExit() {
	$(".column3").empty();
	$(".column3").append("<h2>Header</h2>");
    const prev = managementStack.pop();
    prev();
}

function createStatisticRequest(time) {
	let statisticRequest;
	if(time === "day") {
		const date = $("#day-time-content").val();
		statisticRequest = {
			type: time,
			time: date
		};
	}
	else if(time === "month") {
		const month = $("#month-time-content").val();
		const year = $("#year-time-content").val();
		statisticRequest = {
			type: time,
			time: year + "-" + month
		};
	}
	return statisticRequest;
}

function createChart(statisticResponse) {
	$(".column3").empty();
	$(".column3").append("<h2>Statistic</h2>");
	if(statisticResponse.isEmpty === 1) {
		$(".column3").append("No statistic available");
	}
	else {
		$(".column3").append("<select id='chart-type'><option value='bar'>Bar</option><option value='pie'>Pie</option><option value='line'>Line</option></select>");
		$(".column3").append("<canvas id='myChart'></canvas>");
		ctx = document.getElementById('myChart').getContext('2d');
		
		setRootChartConfig(statisticResponse);
		
		changeChartType("bar");
		
		$("#chart-type").change((event) => {
			changeChartType(event.target.value);
		});
	}
}

function setRootChartConfig(statisticResponse) {
	rootChartConfig = {
			data: {
				labels: [...statisticResponse.labels],
			    datasets: [{
			    	data: [...statisticResponse.data],
			        backgroundColor: [...statisticResponse.colors],
			        borderColor: [...statisticResponse.colors],
			        borderWidth: 1
			    }]
			},
			options: {
			    scales: {
			        yAxes: [{
			        	ticks: {
			        		min: 0
			            },
			            scaleLabel: {
		                    display: true,
		                    labelString: statisticResponse.yAxisName,
		                    fontSize: 17
		                }
			        }],
			        xAxes: [{
			        	scaleLabel: {
		                    display: false,
		                    labelString: statisticResponse.xAxisName,
		                    fontSize: 17
		                }
			        }]
			    },
			    legend: {
			    	display: false
			    },
			    title: {
			        display: true,
			        text: statisticResponse.header,
			        fontSize: 25
			    }
			}
		};
}

function changeChartType(chartType) {
	tempChartConfig = JSON.parse(JSON.stringify(rootChartConfig));
	tempChartConfig.type = chartType;
	
	if(chartType === "pie") {
		tempChartConfig.options.legend.display = true;
		tempChartConfig.options.legend.position = "top";
	}
	else if(chartType === "line" || chartType === "bar") {
		tempChartConfig.options.scales.xAxes[0].scaleLabel.display = true;
	}
	
	if(myChart) {
		myChart.destroy();
	}

	myChart = new Chart(ctx, tempChartConfig);
}

function resetManagementStack() {
    managementStack = new Stack();
}

function resetStatisticStack() {
    statisticStack = new Stack();
}

function checkCounsellorId() {
	isValid = false;
	const id = $("#counsellor-id").val();
    if(!availableSource.includes(id)) {
        $(".id-error").css("visibility", "visible");
    }
    else {
    	isValid = true;
    	$(".id-error").css("visibility", "hidden");
    }
    
    return isValid;
}

function checkMonth() {
	isValid = false;
	const month = $("#month-time-content").val();
	if(month === "") {
		$(".month-year-error").css("visibility", "visible");
	}
	else {
		isValid = true;
		$(".month-year-error").css("visibility", "hidden");
	}
	
	return isValid;
}

function checkYear() {
	isValid = false;
	const year = $("#year-time-content").val();
	if(year === "") {
		$(".month-year-error").css("visibility", "visible");
	}
	else {
		isValid = true;
		$(".month-year-error").css("visibility", "hidden");
	}
	
	return isValid;
}

function resetColumn3() {
	$(".column3").empty();
	$(".column3").append("<h2>Header</h2>");
}

$("#chart-icon").click((event) => {
	resetColumn3();
    chartIconHandler();
    resetManagementStack(); 
    resetStatisticStack();
});

$("#management-icon").click((event) => {
	resetColumn3();
    managementIconHandler();
    resetStatisticStack();
    resetManagementStack();
});






































