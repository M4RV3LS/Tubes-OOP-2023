import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Inventory.*;
import Map.*;
import Sim.*;

/* 
Isi/urutan save file:
Sims: nama lengkap, nama pekerjaan, uang, (list inventory -> nama item, stok), kekenyangan, mood, kesehatan, status, nama rumah, jumlah ruangan,
      (list ruangan di rumah -> nama ruangan, jumlah objek, (list objek di ruangan -> nama objek, inisial, direction, koordinat, dimensinya), ruangan di sisinya)

World: jumlah house di world, (list house -> nama house, koordinat), waktuDunia, hariDunia, waktuSim 

* Format penulisan inventory :
line 1 -> nama item
line 2 -> stok item

* Format penulisan ruangan + details :
line 1 -> nama rumah
line 2 -> jumlah ruangan (n)
line 3 -> nama ruangan 1
line 4 -> jumlah objek di ruangan 1
line 5 -> nama objek 1
line 6 -> nama inisial objek 1
line 7 -> direction objek 1
line 8 -> koordinat objek 1 (x,y)
line 9 -> dimensi objek 1 (panjang,lebar)
dst.
line -> nama ruangan di keempat sisi (ex : Room 2,Room 4,null,null)

* Format penulisan world : (X = jumlah houses)
line 1 -> World X
line 2 -> nama house 1
line 3 -> koordinat house 1
dst.
line -> waktu dunia
line -> hari dunia
line -> waktu sim

*/

public class Save {
    private FileWriter file;
    private ArrayList<Sim> simList;
    private World world;

    public Save(ArrayList<Sim> simList, World world, String filename) {
        this.simList = simList;
        this.world = world;
        file = new FileWriter(filename);
    }

    public void saveGame() {
        try {
            HashMap<BahanMakanan, Integer> stockBM;
            HashMap<Furniture, Integer> stockF;
            HashMap<Masakan, Integer> stockM;
            
            file.write("Sims " + simList.size() + "\n");
            /* save sims */
            for (Sim sim : simList) {
                // nama lengkap, pekerjaan, uang
                file.write(sim.getNameLengkap() + "\n");
                file.write(sim.getPekerjaan().getName() + "\n");
                file.write(sim.getUang() + "\n");
                
                // inventory
                // bahan makanan
                stockBM = sim.getInventoryBahanMakanan().getStock();
                file.write("Bahan Makanan " + stockBM.size() + "\n");
                for (Map.Entry<BahanMakanan, Integer> entry : stockBM.entrySet()) {
                    file.write(entry.getKey().getName() + "\n");
                    file.write(entry.getValue() + "\n");
                }

                // furniture
                stockF = sim.getInventoryFurniture().getStock();
                file.write("Furniture " + stockF.size() + "\n");
                for (Map.Entry<Furniture, Integer> entry : stockF.entrySet()) {
                    file.write(entry.getKey().getName() + "\n");
                    file.write(entry.getValue() + "\n");
                }
                
                // masakan
                stockM = sim.getInventoryMasakan().getStock();
                file.write("Masakan " + stockM.size() + "\n");
                for (Map.Entry<Masakan, Integer> entry : stockM.entrySet()) {
                    file.write(entry.getKey().getNama() + "\n");
                    file.write(entry.getValue() + "\n");
                }

                // kekenyangan, mood, kesehatan, status
                file.write(sim.getKekenyangan() + "\n");
                file.write(sim.getMood() + "\n");
                file.write(sim.getKesehatan() + "\n");
                file.write(sim.getStatus() + "\n");

                // nama rumah Sim
                file.write(sim.getHouse().getHouseName() + "\n");

                // detail setiap room
                // jumlah room dalam house
                ArrayList<Room> rooms = sim.getHouse().getRooms();
                file.write(rooms.size() + "\n");
                for (Room elmt : rooms) {
                    // nama room
                    file.write(elmt.getRoomName() + "\n");

                    // nama dan jumlah objek di room + koordinat
                    ArrayList<FurnitureData> roomFurnitures = elmt.getFurnitureDataList();
                    file.write(roomFurnitures.size() + "\n");
                    for (FurnitureData elmt : roomFurnitures) {
                        file.write(elmt.getFurnitureName() + "\n");
                        file.write(elmt.getInitialName() + "\n");
                        file.write(elmt.getDirection() + "\n");
                        file.write(elmt.getStartX() + "," + elmt.getStartY() + "\n");
                        file.write(elmt.getDimension().getLength() + "," + elmt.getDimension().getWidth() + "\n");
                    }

                    // nama ruangan di sisi-sisi room
                    Room[] sideRooms = {elmt.getRoomUp(), elmt.getRoomDown(), elmt.getRoomLeft(), elmt.getRoomRight()};
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 0; i < 4; i++) {
                        if (sideRooms[i] == null) sb.append("null");
                        else sb.append(sideRooms[i].getRoomName());
                        if (i != 3) sb.append(",");
                    }
                    file.write(sb.toString() + "\n");
                }
            }
            

            /* save world */
            HashMap<House, int[]> houses = world.getHouseList();

            // jumlah houses
            file.write("World " + houses.size() + "\n");

            // nama house dan koordinat
            for (Map.Entry<House, int[]> entry : houses.entrySet()) {
                file.write(entry.getKey().getHouseName() + "\n");
                int[] loc = entry.getValue();
                file.write(loc[0] + "," + loc[1] + "\n");
            }
            
            // waktu dunia, hari dunia, waktu sim
            file.write(world.getWaktuDunia() + "\n");
            file.write(world.getHariDunia() + "\n");
            file.write(world.getWaktuSim() + "\n");

            System.out.println("File berhasil disimpan.");
            file.flush();
            file.close();

        } catch (IOException e) {
            System.out.println("File gagal disimpan.");
            e.printStackTrace();
        }
    }
}
