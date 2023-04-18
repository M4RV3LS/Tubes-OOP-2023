import java.util.*;

import Map.MyObject;
import Map.Room;
import Map.House;

public class RoomDriver {
    public static void main(String[] args) {
        Room room1 = new Room("Living Room");
        Room room2 = new Room("Kitchen");

        MyObject sofa = new MyObject("Sofa");
        MyObject tv = new MyObject("TV");
        MyObject table = new MyObject("Table");
        MyObject chair = new MyObject("Chair");

        room1.addObject(sofa);
        room1.addObject(tv);
        room1.addObject(table);

        room2.addObject(table);
        room2.addObject(chair);

        ArrayList<MyObject> objectsInRoom1 = room1.getObjects();
        System.out.println("Objects in Room 1:");
        for (MyObject obj : objectsInRoom1) {
            System.out.println("- " + obj.getName());
        }

        ArrayList<MyObject> objectsInRoom2 = room2.getObjects();
        System.out.println("Objects in Room 2:");
        for (MyObject obj : objectsInRoom2) {
            System.out.println("- " + obj.getName());
        }
    }
}
