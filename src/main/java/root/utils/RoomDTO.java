package root.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import root.entity.Message;
import root.entity.Room;
import root.entity_service.ClientService;
import root.entity_service.EmployeeService;
import root.entity_service.MessageService;

public class RoomDTO {
	private Long id;
	private String clientRoomName;
	private String employeeRoomName;
	private int isClosed;
	private String starFeedback;
	private String textFeedback;
	private List<MessageDTO> messages = new ArrayList<>();
	private ClientDTO clientDTO;
	private EmployeeDTO employeeDTO;
	
	public RoomDTO(Room room, MessageService messageService, ClientService clientService, EmployeeService employeeService) {
		this.id = room.getId();
		this.clientRoomName = "Chat with " + room.getEmployee().getFullName();
		this.employeeRoomName = room.isClosed() == false ? room.getClient().getFullName() : room.getClient().getFullName() + " (Closed)";
		this.isClosed = room.isClosed() == true ? 1 : 0;
		this.starFeedback = room.getStarFeedback();
		this.textFeedback = room.getTextFeedback();
		this.clientDTO = clientService.convertClientToClientDTO(room.getClient());
		this.employeeDTO = employeeService.convertEmployeeToEmployeeDTO(room.getEmployee());
		this.performConvert(room, messageService);
	}
	
	public RoomDTO(Room room, ClientService clientService, EmployeeService employeeService) {
		this.id = room.getId();
		this.clientRoomName = "Chat with " + room.getEmployee().getFullName();
		this.employeeRoomName = room.isClosed() == false ? room.getClient().getFullName() : room.getClient().getFullName() + " (Closed)";
		this.isClosed = room.isClosed() == true ? 1 : 0;
		this.starFeedback = room.getStarFeedback();
		this.textFeedback = room.getTextFeedback();
		this.clientDTO = clientService.convertClientToClientDTO(room.getClient());
		this.employeeDTO = employeeService.convertEmployeeToEmployeeDTO(room.getEmployee());
	}
	
	private void performConvert(Room room, MessageService messageService) {
		Set<Message> messages = room.getMessages();
		for(Message element : messages) {
			this.messages.add(messageService.convertMessageToMessageDTO(element));
		}
		Comparator<MessageDTO> comparator = Comparator.comparing(MessageDTO::getId);
		Collections.sort(this.messages, comparator);
	}

	public String getStarFeedback() {
		return starFeedback;
	}

	public void setStarFeedback(String starFeedback) {
		this.starFeedback = starFeedback;
	}

	public String getTextFeedback() {
		return textFeedback;
	}

	public void setTextFeedback(String textFeedback) {
		this.textFeedback = textFeedback;
	}

	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientRoomName() {
		return clientRoomName;
	}

	public void setClientRoomName(String clientRoomName) {
		this.clientRoomName = clientRoomName;
	}

	public String getEmployeeRoomName() {
		return employeeRoomName;
	}

	public void setEmployeeRoomName(String employeeRoomName) {
		this.employeeRoomName = employeeRoomName;
	}

	public ClientDTO getClientDTO() {
		return clientDTO;
	}

	public void setClientDTO(ClientDTO clientDTO) {
		this.clientDTO = clientDTO;
	}

	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}

	

	
	
	

}
