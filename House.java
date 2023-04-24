import java.util.*;

public class House {
    private int x;
    private int y;
    private ArrayList<Room> rooms;

    public House(int x, int y) {
        this.x = x;
        this.y = y;
        rooms = new ArrayList<Room>();
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

    public String getNamaRuangan(int index) {
        return rooms.get(index).getRoomName();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    // print arraylist rooms
    public void printRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println(rooms.get(i).getRoomName());
        }
    }

    // test method
    public void printHouse() {
        System.out.println("House Coordinate: X " + x + ", Y " + y);
        System.out.println("Rooms: ");
        printRooms();
    }

    public void ugradeRumah(String namaRuangan)
    {   
        Room newRoom = new Room(namaRuangan);
        rooms.add(newRoom);

        // Kurangi uang Sim dan tampilkan pesan
        System.out.println("Sim telah menambahkan ruangan baru.");

        // Tunggu selama 18 menit
        // try {
        //     Thread.sleep(18 * 60 * 1000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // Tampilkan pesan setelah selesai menunggu
        System.out.println("Ruangan baru sudah siap.");
    }

    // Test method
    public static void main(String[] args) {
        House house = new House(10, 12);
        house.getNamaRuangan(0);

        house.addRoom(new Room("Room 2"));
        System.out.println(house.getNamaRuangan(1)); 

        house.printHouse();
    }
    
}