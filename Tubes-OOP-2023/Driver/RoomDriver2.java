import Inventory.*;
import Map.*;

public class RoomDriver2 {
    public static void main(String[] args) {
        Room room = new Room("Living Room" , null , null , null , null);
        
        MyObject sofa = new MyObject("Sofa");
        room.addObject(sofa);
        
        
        MyObject table = new MyObject("Table");
        room.addObject(table);
        
        
        // boolean isFurniturePlaced = room.placeFurniture(2, 1, new Dimension(1, 2), "Tak", false);
        // System.out.println("Is furniture placed successfully? " + isFurniturePlaced);
        
        System.out.println("Room Name: " + room.getRoomName());
        System.out.println("Room Objects: ");
        room.printObjectCounts();
        // room.printRoom();
    }
}
