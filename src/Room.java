
public class Room {
	private int roomNumber;

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Override
	public String toString() {
		return "" + roomNumber;
	}

	public Room(int roomNumber) {
		super();
		this.roomNumber = roomNumber;
	}

}
