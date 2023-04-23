package Map;
import java.util.ArrayList;
import java.util.*;
import Inventory.*;

public class Room {
    private int width = 6;
    private int height = 6;
    private String roomName;
    private ArrayList<MyObject> objects = new ArrayList<>();
    private Room roomUp;
    private Room roomDown;
    private Room roomLeft;
    private Room roomRight;
    private char[][] layout;


    public Room(String roomName) {
        this.roomName = roomName;
        layout = new char[6][6];
        for (int i = 0; i < layout.length; i++) {
            Arrays.fill(layout[i], '-');
        }
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

    public void printRoom() {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] != '-') {
                    System.out.print(layout[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public boolean placeFurniture(int startX, int startY, Dimension dimension, char furniture , Boolean horizontal) {
        if (startX < 0 || startX >= layout.length || startY < 0 || startY >= layout[0].length) {
            return false;
        }
        if(horizontal){
            if (startX + dimension.getLength() > layout.length || startY + dimension.getWidth() > layout[0].length) {
                return false;
            }
        }
        
        for (int i = startX; i < startX + lengthX; i++) {
            for (int j = startY; j < startY + lengthY; j++) {
                if (layout[i][j] != '-') {
                    return false;
                }
            }
        }
        for (int i = startX; i < startX + lengthX; i++) {
            for (int j = startY; j < startY + lengthY; j++) {
                layout[i][j] = furniture;
            }
        }
        return true;
    }
}
