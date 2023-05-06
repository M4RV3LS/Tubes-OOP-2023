import java.util.*;

import Map.Room;
import Map.House;
import Map.MyObject;

public class RoomDriver {
    public static void main(String[] args) {
        Room room1 = new Room("Living Room" , null , null , null , null);
        Room room2 = new Room("Kitchen" , null , null , null , null);

        MyObject sofa = new MyObject("Sofa");
        MyObject tv = new MyObject("TV");
        MyObject table = new MyObject("Table");
        MyObject chair = new MyObject("Chair");

        room1.addObject(sofa);
        room1.addObject(tv);
        room1.addObject(table);

        room2.addObject(table);
        room2.addObject(chair);

        HashMap<MyObject , Integer> objectsInRoom1 = room1.getObjectCounts();
        System.out.println("Objects in Room 1:");
        room1.printObjectCounts();

        HashMap<MyObject , Integer> objectsInRoom2 = room2.getObjectCounts();
        System.out.println("Objects in Room 2:");
        room2.printObjectCounts();
    }
}
