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
Sims: nama lengkap, nama pekerjaan, uang, list semua nama item di inventory + stok, kekenyangan, mood, kesehatan, status,
      nama rumah, list semua nama ruangan + ruangan di sisinya, list semua objek di setiap ruangan + koordinat + dimensinya

World: list semua nama house + koordinatnya

* Format penulisan inventory :
line pertama -> nama item
line kedua -> stok item

* Format penulisan ruangan + details :
line 1 -> nama ruangan
line 2 -> nama ruangan di keempat sisi (ex : (Room 2,Room 4,null,null))
line 3 -> nama objek 1
line 4 -> koordinat objek 1 (x,y)
line 5 -> dimensi objek 1 (panjang,lebar)
line 6 -> nama objek 2
line 7 -> koordinat objek 2
line 8 -> dimensi objek 3
dst.

* Format penulisan nama house + koordinat sama kayak objek tapi tanpa dimensi
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

            /* save sims */
            for (Sim sim : simList) {
                // nama lengkap, pekerjaan, uang
                file.write(sim.getNameLengkap() + "\n");
                file.write(sim.getPekerjaan().getName() + "\n");
                file.write(sim.getUang() + "\n");
                
                // inventory
                // bahan makanan
                file.write("Bahan Makanan\n");
                stockBM = sim.getInventoryBahanMakanan().getStock();
                for (Map.Entry<BahanMakanan, Integer> entry : stockBM.entrySet()) {
                    file.write(entry.getKey().toString() + "\n");
                    file.write(entry.getValue() + "\n");
                }

                // furniture
                file.write("Furniture\n");
                stockF = sim.getInventoryFurniture().getStock();
                for (Map.Entry<Furniture, Integer> entry : stockF.entrySet()) {
                    file.write(entry.getKey().toString() + "\n");
                    file.write(entry.getValue() + "\n");
                }
                
                // masakan
                file.write("Masakan\n");
                stockM = sim.getInventoryMasakan().getStock();
                for (Map.Entry<Masakan, Integer> entry : stockM.entrySet()) {
                    file.write(entry.getKey().toString() + "\n");
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
                ArrayList<Room> rooms = sim.getHouse().getRooms();
                for (Room elmt : rooms) {
                    // nama room
                    file.write(elmt.getRoomName() + "\n");

                    // nama ruangan di sisi-sisi room
                    Room[] sideRooms = {elmt.getRoomUp(), elmt.getRoomDown(), elmt.getRoomLeft(), elmt.getRoomRight()};
                    StringBuilder sb = new StringBuilder("(");
                    for (int i = 0; i < 4; i++) {
                        if (sideRooms[i] == null) sb.append("null");
                        else sb.append(sideRooms[i].getRoomName());
                        if (i != 3) sb.append(",");
                    }
                    sb.append(")");
                    file.write(sb.toString() + "\n");

                    // nama objek di room + koordinat
                    ArrayList<FurnitureData> roomFurnitures = elmt.getFurnitureDataList();
                    for (FurnitureData elmt : roomFurnitures) {
                        file.write(elmt.getFurnitureName() + "\n");
                        file.write(elmt.getStartX() + "," + elmt.getStartY() + "\n");
                        file.write(elmt.getDimension().getLength() + "," + elmt.getDimension().getWidth() + "\n");
                    }
                }
            }
            

            /* save world */
            for (Map.Entry<House, int[]> entry : world.getHouseList().entrySet()) {
                file.write(entry.getKey().getHouseName() + "\n");
                int[] loc = entry.getValue();
                file.write(loc[0] + "," + loc[1] + "\n");
            }

            System.out.println("File berhasil disimpan.");
            file.flush();
            file.close();

        } catch (IOException e) {
            System.out.println("File gagal disimpan.");
            e.printStackTrace();
        }
    }
}
