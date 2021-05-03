package root.entity_service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import root.entity.Message;
import root.entity.Room;
import root.entity_repository.RoomRepository;
import root.utils.JoinedInRoomsRequest;
import root.utils.RoomDTO;
import root.utils.StatisticRequest;
import root.utils.StatisticResponse;
import root.utils.TopNStatisticRequest;

import static java.util.stream.Collectors.toSet;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private EmployeeService employeeService;
	
	public Page<Room> findLatestRoomOfClient(String clientEmail, Pageable pageable) {
		return this.roomRepository.findLatestRoomOfClient(clientEmail, pageable);
	}
	
	public Room saveRoom(Room room) {
		return this.roomRepository.save(room);
	}
	
	public RoomDTO convertRoomToRoomDTO(Room room) {
		return new RoomDTO(room, messageService, clientService, employeeService);
	}
	
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedIn(String employeeEmail) {
		return this.roomRepository.findAllRoomsThatEmployeeJoinedIn(employeeEmail);
	}
	
	public Optional<Room> findRoomWithMessagesLoadedEagerly(long roomId) {
		return this.roomRepository.findRoomById(roomId);
	}
	
	public Room updateRoom(Room room) {
		return this.roomRepository.save(room);
	}
	
	public Optional<Room> findRoomById(long roomId) {
		return this.roomRepository.findById(roomId);
	}
	
	public Optional<Room> findRoomByIdWithMessagesLoadedEagerly(long roomId) {
		return this.roomRepository.findRoomById(roomId);
	}
	
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedInByTime(JoinedInRoomsRequest request) {
		return this.performFindAllJoinedInRoomsOfACounsellor(request);
	}
	
	public Set<Message> findMessagesBySenderEmailOfRoom(Room room, String receiverEmail) {
		Set<Message> messages = room.getMessages();
		return messages.stream()
					   .filter(message -> !message.getSenderEmail().equals(receiverEmail))
					   .collect(toSet());
	}
	
	public StatisticResponse<Integer, String> getTotalNumberOfRoomsCategorizedByStarFeedback(StatisticRequest request) {
		Optional<List<Object[]>> result = Optional.empty();
		
		result = this.performFindTotalNumberOfConversationsCategorizedByStarFeedback(request);
		
		StatisticResponse<Integer, String> response = new StatisticResponse<>();
		
		if(!result.isEmpty()) {
			List<Object[]> roomsWithStarFeedback = result.get();
			if(roomsWithStarFeedback.size() != 0) {
				response.setIsEmpty(0);
			}
			for(int i = 1; i <= 6; ++i) {
				if(i == 6) {
					response.getLabels().add("Blank");
				}
				else response.getLabels().add(i + " Star");
				response.getData().add(0);
			}
			response.getColors().addAll(this.getColors(response.getData().size()));
			roomsWithStarFeedback.stream()
								 .forEach(roomWithStarFeedback -> response.getData().set(Integer.parseInt((String) roomWithStarFeedback[0]) - 1, ((BigInteger) roomWithStarFeedback[1]).intValue()));
			
			response.setHeader("Consultations statistics on " + this.getTime(request.getTime()) + " categorized by star feedback");
			response.setxAxisName("Rate levels");
			response.setyAxisName("Number of consultations");
		}
		
		return response;
	}
	
	public StatisticResponse<Integer, String> getTopNCounsellorsHavingTheMostNStarFeedbackConversations(TopNStatisticRequest request) {
		Optional<List<Object[]>> result = Optional.empty();
		
		result = this.performFindTopNCounsellorsHavingTheMostNStarFeedbackConversations(request);
		
//		String upOrDown = request.getUpOrDown().equals("ASC") ? "least" : "most";
		
		StatisticResponse<Integer, String> response = new StatisticResponse<>();
		
		if(!result.isEmpty()) {
			List<Object[]> counsellorsWithStarFeedback = result.get();
			if(counsellorsWithStarFeedback.size() != 0) {
				response.setIsEmpty(0);
			}
			counsellorsWithStarFeedback.stream().forEach(counsellorWithStarFeedback -> {
				response.getLabels().add((String) counsellorWithStarFeedback[0]);
				response.getData().add(((BigInteger) counsellorWithStarFeedback[1]).intValue());
			});
			response.getColors().addAll(this.getColors(counsellorsWithStarFeedback.size()));
			response.setHeader("Top " + request.getTopNCounsellor() + " counsellor(s) having the most " + request.getStar() + " star(s) feedback consultations on " + this.getTime(request.getTime()));
			response.setxAxisName("Counsellors' name");
			response.setyAxisName("Number of consultations rated " + request.getStar() + " star(s)");
		}
		
		return response;
	}
	
	private List<String> getColors(int offset) {
		offset = offset == 0 ? 10 : offset;
		List<String> colors = Arrays.asList("rgb(173, 255, 47)",
											"rgb(255, 250, 205)",
											"rgb(144, 238, 144)",
											"rgb(147, 112, 219)",
											"rgb(255, 127, 80)",
											"rgb(220, 20, 60)",
											"rgb(0, 255, 255)",
											"rgb(0, 128, 128)",
											"rgb(139, 0, 139)",
											"rgb(139, 0, 0)");
		return colors.subList(colors.size() - offset, 9);
	}
	
	private String getTime(String time) {
		List<String> months = Arrays.asList("January",
											"February",
											"March",
											"April",
											"May",
											"June",
											"July",
											"August",
											"September",
											"October",
											"November",
											"December");
		
		String[] timeComponents = time.split("-");
		
		if(timeComponents.length == 3) {
			String suffix = timeComponents[2].endsWith("1") ? "st" : timeComponents[2].endsWith("2") ? "nd" : timeComponents[2].endsWith("3") ? "rd" : "th";
			return months.get(Integer.parseInt(timeComponents[1]) - 1) + " " + timeComponents[2] + suffix + " " + timeComponents[0];
		}
		else return months.get(Integer.parseInt(timeComponents[1]) - 1) + " " + timeComponents[0];
	}
	
	private Optional<List<Object[]>> performFindTopNCounsellorsHavingTheMostNStarFeedbackConversations(TopNStatisticRequest request) {
		String[] dateComponents = request.getTime().split("-");
		
		Optional<List<Object[]>> result = Optional.empty();
		
		if(request.getType().equals("day")) {
			result = this.roomRepository.findTopNCounsellorsHavingTheMostNStarFeedbackConversationsByDay(Integer.parseInt(dateComponents[2]),
																										 Integer.parseInt(dateComponents[1]),
																										 Integer.parseInt(dateComponents[0]),
																										 request.getStar(),
																										 request.getTopNCounsellor());
		}
		else if(request.getType().equals("month")) {
			result = this.roomRepository.findTopNCounsellorsHavingTheMostNStarFeedbackConversationsByMonth(Integer.parseInt(dateComponents[1]),
																									       Integer.parseInt(dateComponents[0]),
																									       request.getStar(),
																									       request.getTopNCounsellor());
		}
		return result;
	}
	
	
	private Optional<List<Object[]>> performFindTotalNumberOfConversationsCategorizedByStarFeedback(StatisticRequest request) {
		String[] dateComponents = request.getTime().split("-");
		
		Optional<List<Object[]>> result = Optional.empty();
		
		if(request.getType().equals("day")) {
			result = this.roomRepository.findTotalNumberOfConversationsCategorizedByStarFeedbackAndDay(Integer.parseInt(dateComponents[2]),
																									   Integer.parseInt(dateComponents[1]),
																									   Integer.parseInt(dateComponents[0]));
		}
		else if(request.getType().equals("month")) {
			result = this.roomRepository.findTotalNumberOfConversationsCategorizedByStarFeedbackAndMonth(Integer.parseInt(dateComponents[1]),
																									     Integer.parseInt(dateComponents[0]));
		}
		
		return result;
	}
	
	private Optional<List<Room>> performFindAllJoinedInRoomsOfACounsellor(JoinedInRoomsRequest request) {
		String[] dateComponents = request.getTime().split("-");
		
		Optional<List<Room>> result = Optional.empty();
		
		if(request.getType().equals("day")) {
			result = this.roomRepository.findAllRoomsThatEmployeeJoinedInByDay(request.getEmployeeEmail(),
																			   Integer.parseInt(dateComponents[2]),
																			   Integer.parseInt(dateComponents[1]),
																			   Integer.parseInt(dateComponents[0]));
		}
		else if(request.getType().equals("month")) {
			result = this.roomRepository.findAllRoomsThatEmployeeJoinedByMonth(request.getEmployeeEmail(),
																			   Integer.parseInt(dateComponents[1]),
																			   Integer.parseInt(dateComponents[0]));
		}
		
		return result;
	}
	
	
	
	
	
}

























