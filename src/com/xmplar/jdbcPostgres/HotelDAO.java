package com.xmplar.jdbcPostgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelDAO {
	static PreparedStatement pstm;
	static Statement stm;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);

	public static void addRoom(int roomId) {
		try {
			String query = "insert into \"HotelSchema\".\"Room\" values(?,?)";
			pstm = JDBCConnection.con.prepareStatement(query);
			pstm.setInt(1, roomId);
			pstm.setString(2, "Available");
			pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Room already exists");
		}

	}

	public static void user(int userID, String userName) throws SQLException {
		try {
			if (userID < 1) {
				System.out.println("User ID can't be less than 1");
			} else {
				Pattern pattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
				Matcher matcher = pattern.matcher(userName);
				if (Character.isDigit(userName.charAt(0)) || Character.isWhitespace(userName.charAt(0))
						|| matcher.find()) {
					System.out.println("Invalid Username");
				} else {
					String query = "insert into \"HotelSchema\".\"User\" values(?,?)";
					pstm = JDBCConnection.con.prepareStatement(query);
					pstm.setInt(1, userID);
					pstm.setString(2, userName);
					pstm.executeUpdate();
					System.out.println("User " + userID + " added");
				}
			}
		} catch (SQLException e) {
			System.out.println("User already exists");
		}

	}

	public static void allocateRoom() throws SQLException {
		if (HotelDAO.displayAvailableRooms() == false) {
			return;
		} else {
			System.out.println("Enter the number of rooms to assign:");
			int numberOfRoomsToAllocate = sc.nextInt();
			if (numberOfRoomsToAllocate < 1) {
				System.out.println("Enter 1 or more rooms to allocate");
			} else {
				for (int i = 0; i < numberOfRoomsToAllocate; i++) {
					System.out.println("Enter the room ID to allocate:");
					int roomID = sc.nextInt();
					if (checkForRoomID(roomID) == false) {
						System.out.println("Entered Room ID does not exist");
					} else {
						System.out.println("Enter the user ID");
						int userID = sc.nextInt();
						if (checkForUserID(userID) == false) {
							System.out.println("Entered User ID does not exist");
						} else {
							System.out.println("Enter Username:");
							String userName = sc.next();
							userName += sc.nextLine();

							String allocate = "insert into \"HotelSchema\".\"AllocateRoom\" values(?,?)";
							pstm = JDBCConnection.con.prepareStatement(allocate);
							pstm.setInt(1, roomID);
							pstm.setInt(2, userID);
							pstm.executeUpdate();

							String unavail = "update \"HotelSchema\".\"Room\" set \"HotelSchema\".\"Room\".\"Status\" = 'Unavailable' where \"RoomID\"= ?";
							pstm = JDBCConnection.con.prepareStatement(unavail);
							pstm.setInt(1, roomID);
							pstm.executeUpdate();
							user(userID, userName);
							System.out.println("Room " + roomID + " is allocated to " + userID);
						}
					}
				}
			}
		}
	}

	public static void unallocateRoom() throws SQLException {
		if (HotelDAO.displayAllocatedRooms() == false) {
			return;
		} else {
			System.out.println("Enter the room ID to be unallocated");
			int unAllocateRoomID = sc.nextInt();
			if (checkForRoomID(unAllocateRoomID) == false) {
				return;
			} else {
				String alloc = "delete from \"HotelSchema\".\"AllocateRoom\" where \"HotelSchema\".\"AllocateRoom\".\"RoomID\"= ?";
				pstm = JDBCConnection.con.prepareStatement(alloc);
				pstm.setInt(1, unAllocateRoomID);
				pstm.executeUpdate();
				String stat = "update \"HotelSchema\".\"Room\" set \"Status\" = 'Available' where \"RoomID\"= ?";
				pstm = JDBCConnection.con.prepareStatement(stat);
				pstm.setInt(1, unAllocateRoomID);
				pstm.executeUpdate();
				System.out.println("Room " + unAllocateRoomID + " has been unallocated");
			}
		}
	}

	public static boolean displayAvailableRooms() throws SQLException {
		String avail = "select * from \"HotelSchema\".\"Room\" where \"HotelSchema\".\"Room\".\"Status\" ='Available'";
		stm = JDBCConnection.con.createStatement();
		ResultSet rs = stm.executeQuery(avail);
		if (!rs.isBeforeFirst()) {
			System.out.println("No rooms are available");
			return false;
		} else {
			System.out.print("Unoccupied Rooms: ");
			while (rs.next()) {

				int roomID = rs.getInt("RoomID");

				System.out.print(roomID + " ");
			}
			System.out.println();
			return true;
		}
	}

	public static boolean displayAllocatedRooms() throws SQLException {
		String query = "select * from \"HotelSchema\".\"Room\" where \"HotelSchema\".\"Room\".\"Status\"= 'Unavailable'";
		stm = JDBCConnection.con.createStatement();
		ResultSet rs = stm.executeQuery(query);
		if (!rs.isBeforeFirst()) {
			System.out.println("No rooms are allocated");
			return false;
		} else {
			System.out.print("Occupied Rooms: ");
			while (rs.next()) {

				int roomID = rs.getInt("RoomID");

				System.out.println(roomID + " ");
			}
			System.out.println();
			return true;
		}
	}

	public static boolean checkForRoomID(int roomID) throws SQLException {
		String checkForRoomIDQuery = "SELECT * FROM \"HotelSchema\".\"Room\" WHERE EXISTS (SELECT 1 FROM \"HotelSchema\".\"Room\" WHERE \"RoomID\" = ?)";
		pstm.setInt(1, roomID);
		pstm = JDBCConnection.con.prepareStatement(checkForRoomIDQuery);
		rs = pstm.executeQuery();
		if (!rs.isBeforeFirst()) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkForUserID(int userID) throws SQLException {
		String checkForRoomIDQuery = "SELECT * FROM \"HotelSchema\".\"User\" WHERE EXISTS (SELECT 1 FROM \"HotelSchema\".\"User\" WHERE \"UserID\" = ?)";
		pstm.setInt(1, userID);
		pstm = JDBCConnection.con.prepareStatement(checkForRoomIDQuery);
		rs = pstm.executeQuery();
		if (!rs.isBeforeFirst()) {
			return false;
		} else {
			return true;
		}
	}
}