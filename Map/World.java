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
    private static HashMap<Sim , Integer> waktuUpgrade = new HashMap<>();
    private static HashMap<Sim , Integer> waktuTidakTidur = new HashMap<>();
    private static HashMap<Sim , Integer> waktuTidakBuangAir = new HashMap<>();
    private static int hariDunia = (waktuDunia / 720) + 1;
    private static int waktuDunia;
    private static int waktuSim = 720 - (waktuDunia % 720);

    private World() {}

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public House getHouse(int x, int y) {
        return grid[x][y];
    }

    public void setHouse(int x, int y, House house) {
        grid[x][y] = house;
        houseLocations.put(house, new int[]{x, y});
    }

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

    public int[] getHouseLocation(House house) {
        return houseLocations.get(house);
    }

    public Set<House> getHouses() {
        return houseLocations.keySet();
    }

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
            waktuUpgrade.remove(simToRemove);
            waktuTidakTidur.remove(simToRemove);
            waktuTidakBuangAir.remove(simToRemove);
        }
    }
    

    //getter waktuUpgrade
    public HashMap<Sim , Integer> getWaktuUpgrade(){
        return waktuUpgrade;
    }

    //getter waktuTidakTidur
    public HashMap<Sim , Integer> getWaktuTidakTidur(){
        return waktuTidakTidur;
    }

    //getter waktuTidakBuangAir
    public HashMap<Sim , Integer> getWaktuTidakBuangAir(){
        return waktuTidakBuangAir;
    }

    //Menambahkan Sim dan Integer ke dalam HashMap waktu upgrade
    public void addWaktuUpgrade(Sim sim , int waktu){
        waktuUpgrade.put(sim , waktu);
    }

    //Menambahkan Sim dan Integer ke dalam HashMap waktu tidak tidur
    public void addWaktuTidakTidur(Sim sim , int waktu){
        waktuTidakTidur.put(sim , waktu);
    }

    //Menambahkan Sim dan Integer ke dalam HashMap waktu tidak buang air
    public void addWaktuTidakBuangAir(Sim sim , int waktu){
        waktuTidakBuangAir.put(sim , waktu);
    }

    //getter waktu upgrade dengan parameter string sim name
    public int getWaktuUpgrade(String name){
        for (Sim sim : waktuUpgrade.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                return waktuUpgrade.get(sim);
            }
        }
        return 0;
    }

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

    //Mengupdate Nilai integer berdasarkan paramater String simname pada hashmap waktuUpgrade
    public void updateWaktuUpgrade(String name , int waktu){
        for (Sim sim : waktuUpgrade.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                waktuUpgrade.remove(sim);
                waktuUpgrade.put(sim , waktu);
            }
        }
    }

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
    

}
