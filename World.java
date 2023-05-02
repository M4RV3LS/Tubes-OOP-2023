package Map;
import java.util.*;
import Sim.*;
import Inventory.*;
import Fitur.*;


public class World {
    private static World instance = null;
    private House[][] grid = new House[64][64];
    private HashMap<House, int[]> houseLocations = new HashMap<>();
    private static List<Sim> simList = new ArrayList<>();
    private static ArrayList<UpgradeHouse> daftarUpgradeRumah = new ArrayList<>();
    private static HashMap<Sim , Integer> waktuTidakTidur = new HashMap<>();
    private static HashMap<Sim , Integer> waktuTidakBuangAir = new HashMap<>();
    private static int waktuDunia = 0;
    private static int hariDunia = (waktuDunia / 720) + 1;
    private static int waktuSim = 720 - (waktuDunia % 720);

    private World() {}

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    //getter house
    public House getHouse(int x, int y) {
        return grid[x][y];
    }

    //setter house
    public void setHouse(int x, int y, House house) {
        grid[x][y] = house;
        houseLocations.put(house, new int[]{x, y});
    }

    //printWorld
    public void printWorld() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                if (grid[i][j] != null) {
                    System.out.print(this.getHouse(i,j).getHouseName());
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }

        // int maxWordLength = 3; // maksimum panjang kata 3

        // int boxWidth = maxWordLength + 2; // lebar kotak disesuaikan dengan panjang kata maksimum
        

        // // print baris pertama
        // for (int allMap = 0 ; allMap < 64 ; allMap++){
        //     for (int i = 0; i < 64; i++) {
        //         System.out.print("+");
        //         for (int j = 0; j < boxWidth - 1; j++) {
        //             System.out.print("-");
        //         }
        //     }
        //     System.out.println("+");

        //     // print baris kedua sampai satu sebelum terakhir
            
        //         for (int i = 0; i < 64; i++) {
        //             System.out.print("|");
        //             // Coordinate coordinate = new Coordinate(allMap, i);
        //             // String value = this.mapData.getOrDefault(coordinate, "");
        //             // int valueLength = value.length();
        //             int spaces = (4 - 3) / 2;
        //             for (int j = 0; j < spaces; j++) {
        //                 System.out.print(" ");
        //             }
        //             if(this.getHouse(allMap , i) != null){
        //                 System.out.print(this.getHouse(allMap , i).getHouseName());
        //             }
        //             else{
        //                 System.out.print("   ");

        //             }
                    
                    
        //             for (int j = 0; j < 4 - spaces - 3; j++) {
        //                 System.out.print(" ");
        //             }
                    
        //         }
        //         System.out.print("|");
        //         System.out.println();
                
            
        // }
        // // print baris terakhir
        // for (int i = 0; i < 64; i++) {
        //     System.out.print("+");
        //     for (int j = 0; j < boxWidth - 1; j++) {
        //         System.out.print("-");
        //     }
        // }
        // System.out.println("+");
    }

    //getter house location
    public int[] getHouseLocation(House house) {
        return houseLocations.get(house);
    }

    //getter house
    public Set<House> getHouses() {
        return houseLocations.keySet();
    }

    //getter houselist
    public HashMap<House, int[]> getHouseList() {
        return houseLocations;
    }
    
    //Mencari sebuah objek Sim didalam simList
    public Boolean findSim(String name){
        
        for (Sim sim : simList){
            if (sim.getNamaLengkap().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    //getter simList
    public List<Sim> getSimList(){
        return simList;
    }

    //Menambahkan Sim ke dalam simList
    public void addSim(Sim sim){
        simList.add(sim);
    }

    //Mendapatkan atribut Sim berdasarkan parameter string nama sim
    public Sim getSimByName(String simName) {
        for (Sim sim : this.getSimList()) {
            if (sim.getNamaLengkap().equals(simName)) {
                return sim;
            }
        }
        return null; // jika tidak ditemukan, return null
    }
    
    //Menghapus Sim dari simList
    public void removeSim(String simName) {
        Sim simToRemove = this.getSimByName(simName);
        if (simToRemove != null) {
            simList.remove(simToRemove);
            daftarUpgradeRumah.remove(simToRemove);
            waktuTidakTidur.remove(simToRemove);
            waktuTidakBuangAir.remove(simToRemove);
        }
    }
    

    //getter waktuUpgrade
    // public HashMap<Sim , Integer> getWaktuUpgrade(){
    //     return waktuUpgrade;
    // }

    //getter waktuTidakTidur
    public HashMap<Sim , Integer> getWaktuTidakTidur(){
        return waktuTidakTidur;
    }

    //getter waktuTidakBuangAir
    public HashMap<Sim , Integer> getWaktuTidakBuangAir(){
        return waktuTidakBuangAir;
    }

    // //Menambahkan Sim dan Integer ke dalam HashMap waktu upgrade
    // public void addWaktuUpgrade(Sim sim , int waktu){
    //     waktuUpgrade.put(sim , waktu);
    // }

    //Menambahkan Sim dan Integer ke dalam HashMap waktu tidak tidur
    public void addWaktuTidakTidur(Sim sim , int waktu){
        waktuTidakTidur.put(sim , waktu);
    }

    //Menambahkan Sim dan Integer ke dalam HashMap waktu tidak buang air
    public void addWaktuTidakBuangAir(String nama, int waktu){
        waktuTidakBuangAir.remove(getSimByName(nama));
        waktuTidakBuangAir.put(getSimByName(nama) , waktu);
    }

    //getter waktu upgrade dengan parameter string sim name
    // public int getWaktuUpgrade(String name){
    //     for (Sim sim : waktuUpgrade.keySet()){
    //         if (sim.getNamaLengkap().equals(name)){
    //             return waktuUpgrade.get(sim);
    //         }
    //     }
    //     return 0;
    // }

    //getter waktu tidak tidur dengan parameter string sim name
    public int getWaktuTidakTidur(String name){
        for (Sim sim : waktuTidakTidur.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                return waktuTidakTidur.get(sim);
            }
        }
        return 0;
    }

    //getter waktu tidak buang air dengan parameter string sim name
    public int getWaktuTidakBuangAir(String name){
        for (Sim sim : waktuTidakBuangAir.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                return waktuTidakBuangAir.get(sim);
            }
        }
        return 0;
    }

    // //Mengupdate Nilai integer berdasarkan paramater String simname pada hashmap waktuUpgrade
    // public void updateWaktuUpgrade(String name , int waktu){
    //     for (Sim sim : waktuUpgrade.keySet()){
    //         if (sim.getNamaLengkap().equals(name)){
    //             waktuUpgrade.remove(sim);
    //             waktuUpgrade.put(sim , waktu);
    //         }
    //     }
    // }

    //Mengupdate Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakTidur
    public void updateWaktuTidakTidur(String name , int waktu){
        for (Sim sim : waktuTidakTidur.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuTidakTidur.remove(sim);
                waktuTidakTidur.put(sim , waktu);
            }
        }
    }

    //Mengupdate Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakBuangAir
    public void updateWaktuTidakBuangAir(String name , int waktu){
        for (Sim sim : waktuTidakBuangAir.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuTidakBuangAir.remove(sim);
                waktuTidakBuangAir.put(sim , waktu);
            }
        }
    }

    //Mengurangi Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakBuangAir
    public void reduceWaktuTidakBuangAir(String name , int waktu){
        for (Sim sim : waktuTidakBuangAir.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuTidakBuangAir.remove(sim);
                waktuTidakBuangAir.put(sim , waktuTidakBuangAir.get(sim) - waktu);
            }
        }
    }

    //Mengurangi Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakTidur
    public void reduceWaktuTidakTidur(String name , int waktu){
        for (Sim sim : waktuTidakTidur.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuTidakTidur.remove(sim);
                waktuTidakTidur.put(sim , waktuTidakTidur.get(sim) - waktu);
            }
        }
    }

    //Menambah Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakBuangAir
    public void increaseWaktuTidakBuangAir(String name , int waktu){
        for (Sim sim : waktuTidakBuangAir.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuTidakBuangAir.remove(sim);
                waktuTidakBuangAir.put(sim , waktuTidakBuangAir.get(sim) + waktu);
            }
        }
    }

    //Menambah Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakTidur
    public void increaseWaktuTidakTidur(String name , int waktu){
        for (Sim sim : waktuTidakTidur.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuTidakTidur.remove(sim);
                waktuTidakTidur.put(sim , waktuTidakTidur.get(sim) + waktu);
            }
        }
    }


    //Menambah waktu Dunia
    public void addWaktuDunia(int waktu){
        waktuDunia += waktu;
    }

    //mendapatkan waktu dunia
    public int getWaktuDunia(){
        return waktuDunia;
    }

    //mendapatkan hari dunia
    public int getHariDunia(){
        return hariDunia;
    }

    //mendapatkan waktu SIM
    public int getWaktuSim(){
        return waktuSim;
    }

    // Ganti hari
    public void gantiHari(){
        int temp = 1;
        if(getHariDunia() > temp)
        {
            // Update waktu tidak buang air dan waktu tidak tidur untuk seluruh sim di simlist
            for (Sim sim : simList){
                updateWaktuTidakBuangAir(sim.getNamaLengkap() , 240);
                updateWaktuTidakTidur(sim.getNamaLengkap() , 240);
            }

            temp = getHariDunia();
        }
        
    }

    public void checkWaktuSetelahAksi(String nama,int waktuAksi)
    {
        addWaktuDunia(waktuAksi);
        reduceWaktuTidakBuangAir(nama, waktuAksi);
        reduceWaktuTidakTidur(nama, waktuAksi);
        kurangiWaktuUpgrade(waktuAksi);
        checkUpgradeRoom();  
        checkWaktuTidakBuangAir();
        checkWaktuTidakTidur();
    }
    

//     ArrayList<UpgradeHouse> daftarUpgradeHouse;
// For(UpgradeHouse up : daftarUpgradeHouse){
//     up.setWaktuUpgrade(up.getWaktuUpgrade() - durasiAksi);
//     if(up.getWaktuUpgrade() < 0){
//         Room 
//     }

    //getter daftarUpgradeHouse
    public ArrayList<UpgradeHouse> getDaftarUpgradeHouse(){
        return daftarUpgradeRumah;
    }

    //getter daftar upgrade House berdasarkan parameter nama Sim
    public UpgradeHouse getDaftarUpgradeHouseUsingName(String name){
        for (UpgradeHouse up : daftarUpgradeRumah){
            if (up.getSim().getNamaLengkap().equals(name)){
                return up;
            }
        }
        return null;
    }

    //mengurangi waktuUgrade setiap Sim dengan parameter int waktuUpgrade
    public void kurangiWaktuUpgrade(int waktuUpgrade){
        for (UpgradeHouse up : daftarUpgradeRumah){
            up.setWaktuUpgrade(up.getWaktuUpgrade() - waktuUpgrade);
            if(up.getWaktuUpgrade() <= 0){
                up.setIsUpgraded(true);
            }
        }
        
    }

    public void upgradeHouse(Sim sim , String newrRoom , Boolean isInHouse) {

        Boolean isStillUpgrade = false;
        for (UpgradeHouse up : daftarUpgradeRumah){
            if (up.getSim().getNamaLengkap().equals(sim.getNamaLengkap())){
                isStillUpgrade = true;
            }
        }

        if(isInHouse && !(isStillUpgrade)){
            Scanner scanner = new Scanner(System.in);
            Boolean inputValid = false;
            while(!inputValid){
                System.out.println("Tolong pilih ruangan mana yang ingin dipakai sebagai acuan upgrade rumah: ");
                for (int i = 0; i < sim.getHouse().getRooms().size(); i++) {
                    System.out.println((i+1) + ". " + sim.getHouse().getRooms().get(i).getRoomName());
                }
                System.out.print("Masukkan angka ruangan yang anda inginkan : ");
                int selectedRoomIndex = scanner.nextInt()-1;

                if(selectedRoomIndex < sim.getHouse().getRooms().size()){
                    inputValid = true;
                    Boolean inputValid2 = false;
                    Room selectedRoom = sim.getHouse().getRooms().get(selectedRoomIndex);
                    while(!inputValid2){
                        System.out.println("Please select the position to add the room:");
                        System.out.println("1. Top");
                        System.out.println("2. Right");
                        System.out.println("3. Bottom");
                        System.out.println("4. Left");
                        System.out.print("Masukkan  posisi yang anda inginkan : ");
                        scanner.nextLine();
                        String selectedPosition = scanner.nextLine();
                        //mengecek apakah selectedposition merupakan "1" atau "Top"
                        if(selectedPosition.equals("1") || selectedPosition.equalsIgnoreCase("Top")){
                            if(selectedRoom.getRoomUp() == null){
                                inputValid2 = true;
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 1080 , sim.getHouse() , selectedRoom,  newrRoom , false , "Top");
                                sim.setUpgradeHouse(upgradeHouse);
                            } 
                            else{
                                System.out.println("Ruangan tidak bisa diupgrade karena sudah ada ruangan di atasnya");
                            }
                        }

                        //mengecek apakah selectedposition merupakan "2" atau "Right"
                        else if(selectedPosition.equals("2") || selectedPosition.equalsIgnoreCase("Right")){
                            if(selectedRoom.getRoomRight() == null){
                                inputValid2 = true;
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 1080 , sim.getHouse() , selectedRoom,  newrRoom , false , "Right");
                                sim.setUpgradeHouse(upgradeHouse);
                            }
                            else{
                                System.out.println("Ruangan tidak bisa diupgrade karena sudah ada ruangan di kanannya");
                            }
                        }

                        //mengecek apakah selectedposition merupakan "3" atau "Bottom"
                        else if(selectedPosition.equals("3") || selectedPosition.equalsIgnoreCase("Bottom")){
                            if(selectedRoom.getRoomDown() == null){
                                inputValid2 = true;
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 1080 , sim.getHouse() , selectedRoom,  newrRoom , false , "Bottom");
                                sim.setUpgradeHouse(upgradeHouse);
                            }
                            else{
                                System.out.println("Ruangan tidak bisa diupgrade karena sudah ada ruangan di bawahnya");
                            }
                            
                        }

                        //mengecek apakah selectedposition merupakan "4" atau "Left"
                        else if(selectedPosition.equals("4") || selectedPosition.equalsIgnoreCase("Left")){
                            if(selectedRoom.getRoomLeft() == null){
                                inputValid2 = true;
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 1080 , sim.getHouse() , selectedRoom,  newrRoom , false , "Left");
                                sim.setUpgradeHouse(upgradeHouse);
                            }
                            else{
                                System.out.println("Ruangan tidak bisa diupgrade karena sudah ada ruangan di kirinya");
                            }
                        }

                        else{
                            System.out.println("Input tidak valid");
                        }


                    }
                
                    
                }
            }
            


            
        }
        else if(isInHouse && isStillUpgrade){
            System.out.println("Sim masih dalam proses upgrade");
        }
        else if(!(isInHouse)){
            System.out.println("Sim tidak berada di dalam rumah");
        }
    }

    public void checkUpgradeRoom() {
        for (Sim sim : simList) {
            UpgradeHouse upgradeHouse = sim.getUpgradeHouse();
            if (upgradeHouse != null && upgradeHouse.getWaktuUpgrade() <= 0) {
                Room newRoom = new Room(upgradeHouse.getNewRoom(), null, null, null, null);
                if (upgradeHouse.getDirection().equalsIgnoreCase("Top")) {
                    upgradeHouse.getRoomAcuan().setRoomUp(newRoom);
                    newRoom.setRoomDown(upgradeHouse.getRoomAcuan());
                    sim.getHouse().getRooms().add(newRoom);
                    System.out.println("Ruangan " + newRoom.getRoomName() + " berhasil ditambahkan.");
                    System.out.println("");
                } else if (upgradeHouse.getDirection().equalsIgnoreCase("Left")) {
                    upgradeHouse.getRoomAcuan().setRoomLeft(newRoom);
                    newRoom.setRoomRight(upgradeHouse.getRoomAcuan());
                    sim.getHouse().getRooms().add(newRoom);
                    System.out.println("Ruangan " + newRoom.getRoomName() + " berhasil ditambahkan.");
                    System.out.println("");
                } else if (upgradeHouse.getDirection().equalsIgnoreCase("Right")) {
                    upgradeHouse.getRoomAcuan().setRoomRight(newRoom);
                    newRoom.setRoomLeft(upgradeHouse.getRoomAcuan());
                    sim.getHouse().getRooms().add(newRoom);
                    System.out.println("Ruangan " + newRoom.getRoomName() + " berhasil ditambahkan.");
                    System.out.println("");
                } else if (upgradeHouse.getDirection().equalsIgnoreCase("Down")) {
                    upgradeHouse.getRoomAcuan().setRoomDown(newRoom);
                    newRoom.setRoomUp(upgradeHouse.getRoomAcuan());
                    sim.getHouse().getRooms().add(newRoom);
                    System.out.println("Ruangan " + newRoom.getRoomName() + " berhasil ditambahkan.");
                    System.out.println("");
                }
                sim.setUpgradeHouse(null);
            }
        }
    }
    
    //print All Upgrade House List
    public void printAllUpgradeHouse() {
        if (daftarUpgradeRumah.isEmpty()) {
            System.out.println("Tidak ada upgrade rumah yang sedang berlangsung.");
            return;
        }

        System.out.println("Daftar upgrade rumah yang sedang berlangsung:");
        for (UpgradeHouse upgrade : daftarUpgradeRumah) {
            System.out.println("Sim: " + upgrade.getSim().getNamaLengkap());
            System.out.println("Waktu upgrade: " + upgrade.getWaktuUpgrade());
            System.out.println("Room baru: " + upgrade.getNewRoom());
            System.out.println();
        }
    }

    // Delete house if SIM isDead from the list
    public void deleteHouse() {
        for (Sim sim : simList) {
            if (sim.getOwnHouse() != null && sim.isDead() == true) {
                // houseLocations.remove(sim.getOwnHouse());
                sim.setOwnHouse(null);
            }
        }
    }

    public void checkWaktuTidakBuangAir() {
        for (Sim sim : simList) {
            if (getWaktuTidakBuangAir(sim.getNamaLengkap()) <= 0) {
                sim.efekTidakBuangAir();
            }
        }
    }

    public void checkWaktuTidakTidur() {
        for (Sim sim : simList) {
            if (getWaktuTidakTidur(sim.getNamaLengkap()) <= 0) {
                sim.efekTidakTidur();
            }
        }
    }

    
}



