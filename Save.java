import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Save {
    private FileWriter file;
    private ArrayList<Sim> simList;
    private World world;

    public Save(ArrayList<Sim> simList, World world, String filename) {
        this.simList = simList;
        this.world = world;
        try {
            //cek file sudah ada atau belum
            File f = new File(filename);
            if(f.exists()) {
                f.delete();
            }
            file = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("File gagal disimpan.");
            e.printStackTrace();
        }
    }

    public void saveGame() {
        try {
            //save sims
            for (Sim sim : simList) {
                file.write(sim.getNameLengkap() + "\n");
                file.write(sim.getPekerjaan().getName() + "\n");
                file.write(sim.getUang() + "\n");
                
                // write bahan makanan
                // write furniture
                // write masakan

                file.write(sim.getKekenyangan() + "\n");
                file.write(sim.getMood() + "\n");
                file.write(sim.getKesehatan() + "\n");
                file.write(sim.getStatus() + "\n");

                file.write(sim.getHouse().getHouseName() + "\n");
                ArrayList<Room> rooms = sim.getHouse().getRooms();
                for (Room elmt : rooms) {
                    file.write(elmt.getRoomName() + "\n");
                }

                // write detail room (lokasi objek dsb)
            }
            
            /* save world */
            for (Map.Entry<House, int[]> entry : world.getHouseList().entrySet()) {
                file.write(entry.getKey().getHouseName() + "\n");
                int[] loc = entry.getValue();
                file.write(loc[0] + ", " + loc[1] + "\n");
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
