import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Inventory.*;
import Map.*;
import Sim.*;

public class Load {
    private FileReader fr;
    private Sim sim;
    private World world;
    private ArrayList<Sim> simList;
    private ArrayList<House> houseList;

    public Load(String filename) throws FileNotFoundException {
        File file = new File(filename);
        fr = new FileReader(file);
        world = new World();
        simList = new ArrayList<>();
        houseList = new ArrayList<>();        
    }

    public Sim getSim() {
        return sim;
    }
    public World getWorld() {
        return world;
    }

    public void loadGame() throws IOException {
        BufferedReader br = new BufferedReader(fr);
        String line;

        /* SIMS */
        line = br.readLine();
        int simsLength = Integer.parseInt(line.substring(5));

        for (int i = 0; i < simsLength; i++) {
            // nama lengkap
            line = br.readLine();
            sim = new Sim(line);

            // pekerjaan
            line = br.readLine();
            Job[] jobs = Job.values();
            for (Job job : jobs) {
                if (job.getName().equals(line)) {
                    sim.setPekerjaan(job);
                }
            }

            // uang
            line = br.readLine();
            sim.setUang(Integer.parseInt(line));

            // bahan makanan
            line = br.readLine();
            int bmLength = Integer.parseInt(line.substring(14));
            BahanMakanan[] bms = BahanMakanan.values();
            Inventory<BahanMakanan> invBM = new Inventory();
            for (int j = 0; j < bmLength; j++) {
                line = br.readLine();
                int stock = Integer.parseInt(br.readLine());
                for (BahanMakanan bm : bms) {
                    if (line.equals(bm.getName())) {
                        if (stock > 0) {
                            invBM.tambahStock(bm, stock);
                        }
                    }
                }
            }
            sim.setInventoryBahanMakanan(invBM);

            // furniture
            line = br.readLine();
            int flength = Integer.parseInt(line.substring(10));
            Furniture[] fs = Furniture.values();
            Inventory<Furniture> invF = new Inventory();
            for (int j = 0; j < flength; j++) {
                line = br.readLine();
                int stock = Integer.parseInt(br.readLine());
                for (Furniture f : fs) {
                    if (line.equals(f.getName())) {
                        if (stock > 0) {
                            invF.tambahStock(f, stock-1);
                        }
                    }
                }
            }
            sim.setInventoryFurniture(invBM);

            // masakan
            line = br.readLine();
            int mlength = Integer.parseInt(line.substring(9));
            Masakan[] ms = Masakan.values();
            Inventory<Masakan> invM = new Inventory();
            for (int j = 0; j < mlength; j++) {
                line = br.readLine();
                int stock = Integer.parseInt(br.readLine());
                for (Masakan m : ms) {
                    if (line.equals(m.getName())) {
                        if (stock > 0) {
                            invM.tambahStock(m, stock);
                        }
                    }
                }
            }
            sim.setInventoryMasakan(invM);

            // kekenyangan, mood, kesehatan, status
            line = br.readLine();
            sim.setKekenyangan(Integer.parseInt(line));
            line = br.readLine();
            sim.setMood(Integer.parseInt(line));
            line = br.readLine();
            sim.setKesehatan(Integer.parseInt(line));
            line = br.readLine();
            sim.setStatus(line);

            // rumah sim
            line = br.readLine();
            House house = new House(line);

            // room di rumah sim
            int roomQ = Integer.parseInt(br.readLine());
            Room[] rooms = new Room[roomQ];
            for (int j = 0; j < roomQ; j++) {
                line = br.readLine();
                Room room = new Room(line, null, null, null, null);

                // objek (furniture) di room
                int objekQ = Integer.parseInt(br.readLine());
                for (int k = 0; k < objekQ; k++) {
                    String furnitureName = br.readLine();
                    String furnitureInitial = br.readLine();
                    String furnitureDir = br.readLine();
                    String coordinate[] = br.readLine().split(",");
                    String dimension[] = br.readLine().split(",");
                    Dimension furDim = new Dimension(Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]));
                    room.addFurnitureData(true, Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]), furDim, furnitureName, furnitureInitial, furnitureDir);
                }
                rooms[j] = room;
            }

            // set roomUp roomDown roomLeft roomRight
            int count = 0;
            for (int j = 0; j < roomQ; j++) {
                String[] sideRooms = br.readLine().split(",");
                for (String el : sideRooms) {
                    for (int k = 0; k < roomQ; k++){
                        Room temp;
                        if (el.equals("null")) {
                            switch (count) {
                                case 0 : rooms[j].setRoomUp(null);
                                case 1 : rooms[j].setRoomDown(null);
                                case 2 : rooms[j].setRoomLeft(null);
                                case 3 : rooms[j].setRoomRight(null);
                            }
                        }
                        else if (el.equals(rooms[k].getRoomName())) {
                            switch (count) {
                                case 0 : rooms[j].setRoomUp(rooms[k]);
                                case 1 : rooms[j].setRoomDown(rooms[k]);
                                case 2 : rooms[j].setRoomLeft(rooms[k]);
                                case 3 : rooms[j].setRoomRight(rooms[k]);
                            }
                        }
                        
                    }
                    count++;
                }
            }

            // set rooms milik house
            for (int j = 0; j < roomQ; j++) {
                house.addRoom(rooms[j]);
            }
            // set house milik sim
            sim.setHouse(house);

            simList.add(sim);
            houseList.add(house);
        }


        /* WORLD */
        line = br.readLine();
        int housesQ = Integer.parseInt(line.substring(6));
        String[] houses = new String[housesQ];
        String[] coordinates = new String[2];

        // houses di world dan coordinate
        for (int i = 0; i < housesQ; i++) {
            line = br.readLine();
            coordinates = br.readLine().split(",");
            for (House house : houseList) {
                if (line.equals(house.getHouseName())) {
                    world.setHouse(house, Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
                }
            }
        }

        // set waktu dunia, hari dunia, waktu sim
        line = br.readLine();
        world.setWaktuDunia(Integer.parseInt(line));
        line = br.readLine();
        world.setHariDunia(Integer.parseInt(line));
        line = br.readLine();
        world.setWaktuSim(Integer.parseInt(line));
        
        // set simList milik world
        for (Sim sim : simList) {
            world.addSim(sim);
        }

        br.close();
    }
}
