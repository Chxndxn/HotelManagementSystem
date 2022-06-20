import java.util.InputMismatchException;
import java.util.Scanner;

import com.xmplar.jdbcPostgres.JDBCConnection;

public class App {

	public static void main(String[] args) throws Exception {
		JDBCConnection.Connection();
		Scanner sc = new Scanner(System.in);
		RoomOperations room = new RoomOperations();
		int ch = 0;
		do {
			System.out.println("1: Add room");
			System.out.println("2: Allocate room");
			System.out.println("3: Unallocate room");
			System.out.println("4: List occupied and unoccupied rooms");
			System.out.println("5: Exit");
			System.out.println("Enter choice: ");
			ch = sc.nextInt();
			switch (ch) {
				case 1:
					try {
						System.out.println("Number of Rooms: ");
						int numberOfRooms = sc.nextInt();
						if (numberOfRooms > 0) {
							System.out.println("Add Rooms:");
							for (int i = 0; i < numberOfRooms; i++) {
								int newRoomNumber = sc.nextInt();
								if (newRoomNumber > 0) {
									room.addRoom(newRoomNumber);
								} else {
									System.out.println("Invalid entry");
								}
							}
							System.out.println("Rooms added successfully " + RoomOperations.unoccupiedRooms);
						} else {
							System.out.println("No rooms added");
						}
					} catch (InputMismatchException e) {
						System.out.println("Enter valid number!");
					}
					sc.nextLine();
					break;
				case 2:
					try {
						System.out.println("Choose room numbers: ");
						System.out.println(RoomOperations.unoccupiedRooms);
						int allocateRoomNumber = sc.nextInt();
						System.out.println("Enter UserID: ");
						int userID = sc.nextInt();
						System.out.println("Enter username: ");
						String userName = sc.next();
						sc.nextLine();
						room.allocateRoom(allocateRoomNumber, userID, userName);
					} catch (Exception e) {

						System.out.println("Invalid Room number or User ID or Username");
					}
					break;
				case 3:
					try {
						System.out.println(room.occupiedRooms);
						System.out.println("Enter the room number to be unallocated");
						int unallocateRoomNumber = sc.nextInt();
						room.unallocateRoom(unallocateRoomNumber);
					} catch (Exception e) {
						System.out.println("No rooms to be unallocated");
					}

					break;
				case 4:
					room.displayRooms();
					break;
				default:
					break;
			}
		} while (ch != 5);
	}
}
