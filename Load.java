import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Load {
    private FileReader file;
    private ArrayList<Sim> simList;
    private World world;

    public Load(ArrayList<Sim> simList, World world, String filename) {
        this.simList = simList;
        this.world = world;
        try {
            file = new FileReader(filename);
        } catch (IOException e) {
            System.out.println("File tidak ditemukan.");
            e.printStackTrace();
        }
    }

    public void loadGame() {
        BufferedReader reader = new BufferedReader(file);

        try {
            /* load sims */
            String line = reader.readLine();
            while (line != null) {
                Sim sim = new Sim();
                sim.setNameLengkap(line);
                sim.setPekerjaan(new Pekerjaan(reader.readLine()));
                sim.setUang(Integer.parseInt(reader.readLine()));
                
                // read bahan makanan
                // read furniture
                // read masakan

                sim.setKekenyangan(Integer.parseInt(reader.readLine()));
                sim.setMood(Integer.parseInt(reader.readLine()));
                sim.setKesehatan(Integer.parseInt(reader.readLine()));
                sim.setStatus(reader.readLine());

                String houseName = reader.readLine();
                House house = world.getHouseByName(houseName);
                if (house == null) {
                    house = new House(houseName);
                    world.addHouse(house);
                }
                sim.setHouse(house);

                ArrayList<Room> rooms = house.getRooms();
                for (Room elmt : rooms) {
                    String roomName = reader.readLine();
                    if (roomName.equals(elmt.getRoomName())) {
                        sim.setCurrentRoom(elmt);
                        break;
                    }
                }

                // read detail room (lokasi objek dsb)

                simList.add(sim);
                line = reader.readLine();
            }
            
            /* load world */
            line = reader.readLine();
            while (line != null) {
                String houseName = line;
                int[] loc = parseLocationString(reader.readLine());
                House house = world.getHouseByName(houseName);
                if (house == null) {
                    house = new House(houseName);
                    world.addHouse(house);
                }
                world.setHouseLocation(house, loc[0], loc[1]);
                line = reader.readLine();
            }

            System.out.println("File berhasil diload.");
            reader.close();
            file.close();

        } catch (IOException e) {
            System.out.println("File gagal diload.");
            e.printStackTrace();
        }
    }

    private int[] parseLocationString(String locationString) {
        String[] tokens = locationString.split(",");
        int[] loc = new int[2];
        loc[0] = Integer.parseInt(tokens[0].trim());
        loc[1] = Integer.parseInt(tokens[1].trim());
        return loc;
    }
}
