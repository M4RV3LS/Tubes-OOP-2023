import java.util.*;
import Map.*;
import Inventory.*;
import Fitur.*;

public class RoomDriver3 {

    public static void main(String[] args) {
        Room livingRoom = new Room("Living Room", null, null, null, null);
        MyObject kasurSingle = new MyObject("Kasur Single");
        MyObject toilet = new MyObject("Toilet");
        MyObject jam = new MyObject("Jam");

        livingRoom.addObject(kasurSingle);
        livingRoom.addObject(toilet);

        System.out.println("Living Room objects: ");
        livingRoom.printObjectCounts();

        boolean removed = livingRoom.removeObject(kasurSingle);
        System.out.println("TV removed from Living Room: " + removed);
        System.out.println("Living Room objects: " );
        livingRoom.printObjectCounts();

        Room kitchen = new Room("Kitchen", null, null, null, null);
        livingRoom.setRoomRight(kitchen);
        kitchen.setRoomLeft(livingRoom);
        System.out.println("Is Kitchen connected to Living Room? " + livingRoom.isConnected(kitchen));

        // livingRoom.placeFurniture(2, 2, new Dimension(2, 1), "KSS", true);
        // livingRoom.placeFurniture(4, 2, new Dimension(1, 1), "TLT", false);

        System.out.println("Map data of Living Room:");
        // livingRoom.updateMapData();
        livingRoom.printRoom();

       

        // for (int i = 0; i < livingRoom.getLayout().length; i++) {
        //     for (int j = 0; j < livingRoom.getLayoutRow(i).length; j++) {
        //         System.out.print(livingRoom.getLayoutContent(i , j) + " ");
        //     }
        //     System.out.println();
        // }

        // for (Map.Entry<Coordinate, String> entry : livingRoom.getMapData().entrySet()) {
        //     Coordinate key = entry.getKey();
        //     String value = entry.getValue();
        //     System.out.println("Key: " + key + ", Value: " + value);
        // }
       
        
   

    }

}
