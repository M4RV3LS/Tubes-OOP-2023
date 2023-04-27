import java.util.ArrayList;

public class House {
    private int x;
    private int y;
    private ArrayList<Room> rooms;

    public House(int x, int y) {
        this.x = x;
        this.y = y;
        rooms = new ArrayList<>();
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

    public void addRoom(Room room, String location) {
        rooms.add(room);
    }

    public void upgradeHouse(String roomToAdd, Room simLocation, String direction) {
        Room newRoom = new Room(roomToAdd);
        boolean found = true;

        for (int i = 0; i < rooms.size(); i++) {
            if ((rooms.get(i).getRoomName()).equals(simLocation.getRoomName())) {

                switch (direction) {
                    case "up" :
                        if (simLocation.getRoomUp() == null) {
                            simLocation.setRoomUp(newRoom);
                        } break;
                    case "down" :
                        if (simLocation.getRoomDown() == null) {
                            simLocation.setRoomDown(newRoom);
                        } break;
                    case "left" :
                        if (simLocation.getRoomLeft() == null) {
                            simLocation.setRoomLeft(newRoom);
                        } break;
                    case "right" :
                        if (simLocation.getRoomRight() == null) {
                            simLocation.setRoomRight(newRoom);
                        } break;
                    default :
                        System.out.println("Gagal menambahkan ruangan baru.");
                        found = false;
                }

                if (found) {
                    rooms.add(newRoom);
                    rooms.set(i, simLocation);
                    System.out.println("Berhasil menambahkan ruangan baru.");
                }
            }
        }
    }
}