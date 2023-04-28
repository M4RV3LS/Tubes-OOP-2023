import java.util.*;

import Map.House;
import Map.Room;
public class HouseDriver {
    public static void main(String[] args) {
        // membuat objek rumah dengan koordinat (0, 0)
        House house = new House("H01");
        
        
        // membuat objek kamar tidur dengan nama "Bedroom"
        Room bedroom = new Room("Bedroom" , null , null , null , null);
        // menambahkan objek kamar tidur ke dalam rumah di sebelah kiri ruang tamu
        house.addRoom(bedroom);
        
        // membuat objek kamar mandi dengan nama "Bathroom"
        Room bathroom = new Room("Bathroom" , null , null , null , null);
        // menambahkan objek kamar mandi ke dalam rumah di sebelah atas kamar tidur
        house.addRoom(bathroom);
        
        // membuat objek dapur dengan nama "Kitchen"
        Room kitchen = new Room("Kitchen" , null , null , null , null);
        // menambahkan objek dapur ke dalam rumah di sebelah kanan ruang tamu
        house.addRoom(kitchen);
       
        // menampilkan informasi setiap ruangan dalam rumah
        ArrayList<Room> rooms = house.getRooms();

        System.out.println("Berikut ini adalah daftar ruangan yang ada rumah");
        //Melakukan looping terhadap semua room yang ada didalam rumah 
        for(Room ruangan : rooms){
            System.out.println(ruangan.getRoomName());
        }

        // for (int i = 0; i < rooms.size(); i++) {
        //     Room room = rooms.get(i);
        //     System.out.println("Room name: " + room.getRoomName());
        //     System.out.println("Connected to: ");
        //     if (room.getRoomUp() != null) {
        //         System.out.println("- " + room.getRoomUp().getRoomName() + " (up)");
        //     }
        //     if (room.getRoomDown() != null) {
        //         System.out.println("- " + room.getRoomDown().getRoomName() + " (down)");
        //     }
        //     if (room.getRoomLeft() != null) {
        //         System.out.println("- " + room.getRoomLeft().getRoomName() + " (left)");
        //     }
        //     if (room.getRoomRight() != null) {
        //         System.out.println("- " + room.getRoomRight().getRoomName() + " (right)");
        //     }
        //     System.out.println("");
        // }
    }
}
