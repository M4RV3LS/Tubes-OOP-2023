package Map;
import java.util.*;

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

    

    //Melakukan Print ArrayList<Room> rooms menggunakan looping 
    public void printRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i+1) + ". " + rooms.get(i).getRoomName());
        }
    }

    //Mencari apakah input ada didalam list rooms dengan parameter String Room 
    public boolean isRoomExist(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                return true;
            }
        }
        return false;
    }
}
