package Map;
import java.util.*;

public class House {
    private ArrayList<Room> rooms;
    private String houseName;

    public House(String houseName) {
        this.houseName = houseName;
        rooms = new ArrayList<>();
        rooms.add(new Room("Living Room" , null , null , null , null));
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName){
        this.houseName = houseName;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoom(String roomName){
        for(Room simRoom : this.rooms) {
            if(simRoom.getRoomName().equals(roomName)) {
                return simRoom;
            }
        }
        return null;
    }

    public void upgradeHouse(Room newrRoom) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tolong pilih ruangan mana yang ingin dipakai sebagai acuan upgrade rumah: ");
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i+1) + ". " + rooms.get(i).getRoomName());
        }

        int selectedRoomIndex = scanner.nextInt()-1;
        Room selectedRoom = rooms.get(selectedRoomIndex);
        System.out.println("Please select the position to add the room:");
        System.out.println("1. Top");
        System.out.println("2. Right");
        System.out.println("3. Bottom");
        System.out.println("4. Left");
        int selectedPosition = scanner.nextInt();
        switch (selectedPosition) 
        {
            case 1:
                if(selectedRoom.getRoomUp() == null) 
                {
                    selectedRoom.setRoomUp(newrRoom);
                    newrRoom.setRoomDown(selectedRoom);
                    //menambahkan Room ke dalam ArrayList<Room> rooms
                    rooms.add(newrRoom);
                    System.out.println("Ruangan " + newrRoom.getRoomName() + " Berhasil Ditambahkan");
                    System.out.println("");
                } 
                
                else {
                    System.out.println("There is already a room there.");
                }
                break;
            
            case 2:
                if(selectedRoom.getRoomRight() == null) 
                {
                    selectedRoom.setRoomRight(newrRoom);
                    newrRoom.setRoomLeft(selectedRoom);
                    //menambahkan Room ke dalam ArrayList<Room> rooms
                    rooms.add(newrRoom);
                    System.out.println("Ruangan " + newrRoom.getRoomName() + " Berhasil Ditambahkan");
                    System.out.println("");
                } 
                
                else {
                    System.out.println("There is already a room there.");
                }
            break;
            
            case 3:
                if(selectedRoom.getRoomDown() == null) 
                {
                    selectedRoom.setRoomDown(newrRoom);
                    newrRoom.setRoomUp(selectedRoom);
                    //menambahkan Room ke dalam ArrayList<Room> rooms
                    rooms.add(newrRoom);
                    System.out.println("Ruangan " + newrRoom.getRoomName() + " Berhasil Ditambahkan");
                    System.out.println("");
                } 
                
                else {
                    System.out.println("There is already a room there.");
                }
            break;
            
            case 4:
                if(selectedRoom.getRoomLeft() == null) 
                {
                    selectedRoom.setRoomLeft(newrRoom);
                    newrRoom.setRoomRight(selectedRoom);
                    //menambahkan Room ke dalam ArrayList<Room> rooms
                    rooms.add(newrRoom);
                    System.out.println("Ruangan " + newrRoom.getRoomName() + " Berhasil Ditambahkan");
                    System.out.println("");
                } 
                
                else {
                    System.out.println("There is already a room there.");
                }
                break;
            
            default:
                System.out.println("Invalid input.");
        }
    }

    //Melakukan Print ArrayList<Room> rooms menggunakan looping 
    public void printRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i+1) + ". " + rooms.get(i).getRoomName());
        }
    }
}
