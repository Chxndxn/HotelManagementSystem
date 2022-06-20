package com.xmplar.jdbcPostgres;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws Exception {
		JDBCConnection.Connection();
		int ch = 0;
		try {
			do {
				System.out.println("1: Add room");
				System.out.println("2: Allocate room");
				System.out.println("3: Unallocate room");
				System.out.println("4: List occupied and unoccupied rooms");
				System.out.println("5: Exit");
				System.out.println("Enter choice: ");
				String choice = "";
				Scanner sc = new Scanner(System.in);
				choice = sc.next();
				sc.nextLine();
				try {
					ch = Integer.parseInt(choice);
					if (ch > 0 && ch <= 5) {
						switch (ch) {
						case 1:
							try {
								System.out.println("Number of Rooms: ");
								int numberOfRooms = sc.nextInt();
								if (numberOfRooms > 0) {
									System.out.println("Add Room ID:");
									for (int i = 0; i < numberOfRooms; i++) {
										int newRoomNumber = sc.nextInt();
										if (newRoomNumber > 0) {
											HotelDAO.addRoom(newRoomNumber);
										} else {
											System.out.println("Room ID can't be less than 1");
										}
									}
									System.out.println("Rooms are added successfully\n");
								}
							} catch (Exception e) {
								System.out.println("Room already exsits");
							}
							break;
						case 2:
							try {
								HotelDAO.allocateRoom();
							} catch (InputMismatchException e) {
								System.out.println("Enter a number more than 1");
							}
							break;
						case 3:
							try {
								HotelDAO.unallocateRoom();
							} catch (InputMismatchException e) {
								System.out.println("Enter a number more than 1");
							}
							break;
						case 4:
							HotelDAO.displayAllocatedRooms();
							HotelDAO.displayAvailableRooms();
							break;
						}
					} else {
						System.out.println("Invalid input. Please choose between 1 to 5");
					}
				} catch (NumberFormatException e) {
					System.out.println("Enter a choice between 1 and 5");
				}
			} while (ch != 5);
		} catch (

		InputMismatchException e) {
			System.out.println("Enter a number between 1 to 5");
		}
	}
}
