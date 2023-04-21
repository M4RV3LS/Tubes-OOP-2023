import java.util.*;
import Map.*;

public class RoomDriver2 {
    public static void main(String[] args) {
        Room room1 = new Room("Living Room");
        Room room2 = new Room("Kitchen");
        Room room3 = new Room("Bedroom");

        room1.setRoomRight(room2);
        room2.setRoomLeft(room1);
        room2.setRoomRight(room3);
        room3.setRoomLeft(room2);

        MyObject sofa = new MyObject("Sofa");
        MyObject table = new MyObject("Table");
        MyObject chair = new MyObject("Chair");

        room1.addObject(sofa);
        room1.addObject(table);
        room1.addObject(chair);

        room1.placeFurniture(0, 0, 2, 2, 'S');
        room1.placeFurniture(0, 2, 2, 1, 'T');
        room1.placeFurniture(1, 3, 1, 1, 'C');

        room1.printRoom();
        System.out.println();

        System.out.println("Objects in " + room1.getRoomName() + ":");
        for (MyObject obj : room1.getObjects()) {
            System.out.println(obj.getName());
        }

        System.out.println("Connected rooms:");
        if (room1.getRoomUp() != null) {
            System.out.println(room1.getRoomUp().getRoomName() + " (up)");
        }
        if (room1.getRoomDown() != null) {
            System.out.println(room1.getRoomDown().getRoomName() + " (down)");
        }
        if (room1.getRoomLeft() != null) {
            System.out.println(room1.getRoomLeft().getRoomName() + " (left)");
        }
        if (room1.getRoomRight() != null) {
            System.out.println(room1.getRoomRight().getRoomName() + " (right)");
        }
    }
}
