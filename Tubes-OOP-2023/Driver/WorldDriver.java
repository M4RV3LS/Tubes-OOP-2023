import java.util.*;

import Map.House;
import Map.Room;
import Map.World;

public class WorldDriver {
    public static void main(String[] args) {
        // Mendapatkan instance dari class World
        World world = World.getInstance();

        //menerima inputan dari user 
        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan jumlah rumah yang ingin dibuat: ");
        int jumlahRumah = input.nextInt();

        for (int i = 0; i < jumlahRumah; i++) {
            System.out.println("Masukkan koordinat rumah ke-" + (i + 1) + ": ");
            int x = input.nextInt();
            int y = input.nextInt();
            String namaRumah = "H" + String.format("%02d", i);
            House house = new House(namaRumah);
            world.setHouse(x, y, house);
        }

        // Membuat objek House dan menempatkannya pada koordinat (10, 10) pada grid
        // House house1 = new House(10, 10);
        // world.setHouse(10, 10, house1);

        // Mencetak grid dunia pada konsol
        world.printWorld();

        // Mendapatkan semua koordinat rumah dari hashmap houseLocations
        for (House house : world.getHouses()) {
            int[] houseLocation = world.getHouseLocation(house);
            System.out.println("Koordinat rumah: (" + houseLocation[0] + ", " + houseLocation[1] + ")");

            // Menambahkan ruangan baru pada rumah
            Room room = new Room("Living Room" , null , null , null , null);
        }
        
        // Mendapatkan koordinat rumah pertama dari hashmap houseLocations
        // int[] house1Location = world.getHouseLocation(house1);
        // System.out.println("Koordinat rumah 1: (" + house1Location[0] + ", " + house1Location[1] + ")");
    }
}
