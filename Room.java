import java.util.*;

public class Room {
    private int width = 6;
    private int height = 6;
    private String roomName;
    private Room roomUp;
    private Room roomDown;
    private Room roomLeft;
    private Room roomRight;
    private char[][] layout;


    public Room(String roomName) {
        this.roomName = roomName;
        layout = new char[width][height];
        for (int i = 0; i < layout.length; i++) {
            Arrays.fill(layout[i], '-');
        }
    }

    /* public Room(String roomName, Room roomUp, Room roomDown, Room roomLeft, Room roomRight) {
        this.roomName = roomName;
        layout = new char[width][length];
        for (int i = 0; i < layout.length; i++) {
            Arrays.fill(layout[i], '-');
        }

        this.roomUp = roomUp;
        this.roomDown = roomDown;
        this.roomLeft = roomLeft;
        this.roomRigth = roomRigth;
    } */

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
}