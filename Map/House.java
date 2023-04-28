package Map;
import java.util.ArrayList;

public class House {
    private ArrayList<Room> rooms;
    private String houseName;

    public House(String houseName) {
        this.houseName = houseName;
        rooms = new ArrayList<>();
        rooms.add(new Room("Living Room" , null , null , null , null));
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName){
        this.houseName = houseName;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoom(String roomName){
        for(Room simRoom : this.rooms) {
            if(simRoom.getRoomName().equals(roomName)) {
                return simRoom;
            }
        }
        return null;
    }
}
