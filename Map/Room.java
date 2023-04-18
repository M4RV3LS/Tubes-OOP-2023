package Map;
import java.util.ArrayList;

public class Room {
    private int width = 6;
    private int height = 6;
    private String roomName;
    private ArrayList<MyObject> objects = new ArrayList<>();
    private Room roomUp;
    private Room roomDown;
    private Room roomLeft;
    private Room roomRight;

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public boolean addObject(MyObject obj) {
        if (objects.size() < width * height) {
            objects.add(obj);
            return true;
        }
        return false;
    }

    public boolean removeObject(Object obj) {
        return objects.remove(obj);
    }

    public ArrayList<MyObject> getObjects() {
        return objects;
    }

    public Room getRoomUp() {
        return roomUp;
    }

    public void setRoomUp(Room roomUp) {
        this.roomUp = roomUp;
    }

    public Room getRoomDown() {
        return roomDown;
    }

    public void setRoomDown(Room roomDown) {
        this.roomDown = roomDown;
    }

    public Room getRoomLeft() {
        return roomLeft;
    }

    public void setRoomLeft(Room roomLeft) {
        this.roomLeft = roomLeft;
    }

    public Room getRoomRight() {
        return roomRight;
    }

    public void setRoomRight(Room roomRight) {
        this.roomRight = roomRight;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isConnected(Room room) {
        if (this.roomUp == room || this.roomDown == room || this.roomLeft == room || this.roomRight == room) {
            return true;
        }
        return false;
    }
}
