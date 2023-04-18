package Map;
import java.util.ArrayList;

public class House {
    private int x;
    private int y;
    private ArrayList<Room> rooms;

    public House(int x, int y) {
        this.x = x;
        this.y = y;
        rooms = new ArrayList<>();
        rooms.add(new Room("Room 1"));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
