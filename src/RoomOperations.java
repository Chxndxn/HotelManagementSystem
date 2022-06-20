
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xmplar.jdbcPostgres.HotelDAO;

public class RoomOperations {
	public static ArrayList<Room> unoccupiedRooms = new ArrayList<Room>();
	public static ArrayList<Room> occupiedRooms = new ArrayList<Room>();
	public static HashMap<Room, User> allocatedRoom = new HashMap<>();
	private String s;

	public String addRoom(int newRoomNumber) {
		try {
			if (newRoomNumber > 0) {
				Room room = new Room(newRoomNumber);
				unoccupiedRooms.add(room);

				return "Successfully added!";
			} else {
				return "Invalid entry";
			}
		} catch (InputMismatchException e) {
			System.out.println(e);
			return "Invalid entry. Enter correct room number";
		}
	}

	public String allocateRoom(int roomNumber, int userID, String userName) {
		try {
			Scanner sc = new Scanner(System.in);
			Pattern pattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
			Matcher matcher = pattern.matcher(userName);
			if (roomNumber < 1) {
				return "Invalid Room number";
			}
			if (userID < 1 || Character.isDigit(userName.charAt(0)) || Character.isWhitespace(userName.charAt(0))
					|| matcher.find() || userName.length() < 5 || userName.length() > 18) {
				return "Invalid User";
			} else {
				for (int i = 0; i < unoccupiedRooms.size(); i++) {
					Room availableRoom = unoccupiedRooms.get(i);
					if (roomNumber == availableRoom.getRoomNumber()) {
						Room room = new Room(roomNumber);
						if (roomNumber == room.getRoomNumber()) {
							User user = new User(userID, userName);
							allocatedRoom.put(room, user);
							occupiedRooms.add(room);
							System.out.println("Room " + room + " has been allocated to " + user);
						} else {
							System.out.println("Room does not exist!");
						}
						for (int j = 0; j < unoccupiedRooms.size(); j++) {
							Room allocateRoom = unoccupiedRooms.get(j);
							if (roomNumber == allocateRoom.getRoomNumber()) {
								unoccupiedRooms.remove(j);
							}
						}
					}
				}
				return "Allocated successfully!";
			}
		} catch (Exception e) {
			return "Could not be allocated";
		}
	}

	public String unallocateRoom(int roomNumber) {
		try {
			if (roomNumber < 1) {
				return "Invalid Room number";
			} else if (allocatedRoom.isEmpty()) {
				System.out.println("Allocate Rooms please");
			} else {
				Room room = new Room(roomNumber);
				for (int i = 0; i < occupiedRooms.size(); i++) {
					Room unallocateRoom = occupiedRooms.get(i);
					if (roomNumber == unallocateRoom.getRoomNumber()) {
						unoccupiedRooms.add(unallocateRoom);
						occupiedRooms.remove(i);
						System.out.println("Room " + unallocateRoom + " has been unallocated");
					}
				}
			}
			return "Unallocated the room";
		} catch (Exception e) {
			return "Could not unallocate";
		}
	}

	public void displayRooms() {
		System.out.println("Unoccupied Rooms: " + unoccupiedRooms);
		System.out.println("Occupied Rooms: " + occupiedRooms);
	}
}
