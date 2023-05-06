package Map;
import java.util.*;
import Sim.*;
import Inventory.*;
import Fitur.*;


public class World{
    private static World instance = null;
    private House[][] grid = new House[64][64];
    private HashMap<House, int[]> houseLocations = new HashMap<>();
    private static List<Sim> simList = new ArrayList<>();
    private static ArrayList<UpgradeHouse> daftarUpgradeRumah = new ArrayList<>();
    private static HashMap<Sim , Integer> waktuTidakTidur = new HashMap<>();
    private static HashMap<Sim , Integer> waktuTidakBuangAir = new HashMap<>();
    private ArrayList<DeliveryItem<Furniture>> deliveryItemsFurniture = new ArrayList<DeliveryItem<Furniture>>();
    private ArrayList<DeliveryItem<BahanMakanan>> deliveryItemsBahanMakanan = new ArrayList<DeliveryItem<BahanMakanan>>();
    private static int waktuDunia = 0;
    private static int hariDunia = 0;
    private static int waktuSim = 0;
    private static Boolean isGantiHari = false;
    private static Boolean isAddSim = true;

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

    //make a house null 
    public void removeHouse(House house){
        house = null;
    }
    
    // Mendapatkan pemilik rumah dengan memanfaatkan atribut ownHouse pada setiap sim dan simList 
    public Sim getHouseOwner(House house) {
        for (Sim s : getSimList()) {
            if (s.getOwnHouse() != null && s.getOwnHouse().getHouseName().equalsIgnoreCase(house.getHouseName()) ) {
                return s;
            }
        }
        return null;
    }
    
    
    //Mencari sebuah objek Sim didalam simList
    public Boolean findSim(String name){
        if(!(simList.isEmpty())){
            for (Sim sim : simList){
                if (sim.getNamaLengkap().equalsIgnoreCase(name)){
                    return true;
                }
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
            if (sim.getNamaLengkap().equalsIgnoreCase(simName)) {
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
    public void addWaktuTidakBuangAir(Sim sim , int waktu){
        waktuTidakBuangAir.put(sim , waktu);
    }

    //remove sim dari hashmap waktuTidakTidur
    public void removeWaktuTidakTidur(Sim sim){
        waktuTidakTidur.remove(sim);
    }

    //remove sim dari hashmap waktuTidakBuangAir
    public void removeWaktuTidakBuangAir(Sim sim){
        waktuTidakBuangAir.remove(sim);
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
        if(!(waktuTidakBuangAir.isEmpty())){
            for (Sim sim : waktuTidakBuangAir.keySet()){
                if (sim.getNamaLengkap().equals(name)){
                    return waktuTidakBuangAir.get(sim);
                }
            }
        }
        return 0;
    }

    //getter isGantiHari
    public boolean getIsGantiHari(){
        return isGantiHari;
    }

    //setter isGantiHari
    public void setIsGantiHari(boolean isGantiHari){
        this.isGantiHari = isGantiHari;
    }

    //check isGantiHari()
    public void checkIsGantiHari(int waktuAksi){
        int waktuSim = getWaktuSim() - waktuAksi;
        if(waktuSim < 0){
            resetHari();
        }
        else{
            this.setIsGantiHari(false);
        }
    }

    //getter isAddSim
    public boolean getIsAddSim(){
        return isAddSim;
    }

    //setter isAddSim
    public void setIsAddSim(boolean isAddSim){
        this.isAddSim = isAddSim;
    }

    //reset hari
    public void resetHari(){
        this.setIsAddSim(false);
        this.setIsGantiHari(true);
        for (Sim sim : simList){
            sim.setIsGantiKerja(false);
        }

        //meremove semua sim dari HashMap<Sim , Integer> waktuTidakBuangAir
        if(!(waktuTidakBuangAir.isEmpty())){
            for (Sim sim : waktuTidakBuangAir.keySet()){
                waktuTidakBuangAir.remove(sim);
            }
        }

        //mereset semua waktu Tidak Tidur sim pada hashmap waktuTidakTidur menjadi 600
        if(!(waktuTidakTidur.isEmpty())){
            for (Sim sim : waktuTidakTidur.keySet()){
                waktuTidakTidur.put(sim , 60);
            }
        }
        
    }

    //getter daftarUpgradeRumah
    public ArrayList<UpgradeHouse> getDaftarUpgradeRumah(){
        return daftarUpgradeRumah;
    }

    // //Menambahkan Sim dan House ke dalam HashMap daftarUpgradeRumah
    // public void addDaftarUpgradeRumah(Sim sim , House house){
    //     daftarUpgradeRumah.put(sim , house);
    // }
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
        if(!(waktuTidakTidur.isEmpty())){
            for (Sim sim : waktuTidakTidur.keySet()){
                if (sim.getNamaLengkap().equals(name)){
                    // waktuTidakTidur.remove(sim);
                    waktuTidakTidur.put(sim , waktu);
                }
            }
        }
    }

    //Mengupdate Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakBuangAir
    public void updateWaktuTidakBuangAir(String name , int waktu){
        //melakukan cek apakah HashMap<Sim , Integer> waktuTidakBuangAir kosong atau tidak 
        if (!(waktuTidakBuangAir.isEmpty())){
            for (Sim sim : waktuTidakBuangAir.keySet()){
                if (sim.getNamaLengkap().equals(name)){
                    // waktuTidakBuangAir.remove(sim);
                    waktuTidakBuangAir.put(sim , waktu);
                }
            }
        }
        
    }

    //Menambah waktu Dunia
    public void addWaktuDunia(int waktu){
        this.waktuDunia += waktu;
    }

   

    //getter waktu dunia
    public int getWaktuDunia() {
        return waktuDunia;
    }

    //setter waktu dunia
    public void setWaktuDunia(int waktuDunia) {
        this.waktuDunia = waktuDunia;
    }

    //getter hari dunia
    public int getHariDunia() {
        return ((waktuDunia / 90) + 1);
    }

    //setter hari dunia
    public void setHariDunia(int hariDunia) {
        this.hariDunia = hariDunia;
    }

    //getter waktu sim
    public int getWaktuSim() {
        return (90 - (waktuDunia % 91));
    }

    //setter waktu sim
    public void setWaktuSim(int waktuSim) {
        this.waktuSim = waktuSim;
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
        if(!(daftarUpgradeRumah.isEmpty())){
            for (UpgradeHouse up : daftarUpgradeRumah){
                if (up.getSim().getNamaLengkap().equals(sim.getNamaLengkap())){
                    isStillUpgrade = true;
                    break;
                }
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
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 60 , sim.getHouse() , selectedRoom,  newrRoom , false , "Top");
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
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 60 , sim.getHouse() , selectedRoom,  newrRoom , false , "Right");
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
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 60 , sim.getHouse() , selectedRoom,  newrRoom , false , "Bottom");
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
                                UpgradeHouse upgradeHouse = new UpgradeHouse(sim , 60 , sim.getHouse() , selectedRoom,  newrRoom , false , "Left");
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
            // System.out.println(sim.getUpgradeHouse().getWaktuUpgrade());
            // System.out.println("Check upgrade Room 1");
            UpgradeHouse upgradeHouse = sim.getUpgradeHouse();
            if (upgradeHouse != null && upgradeHouse.getWaktuUpgrade() <= 0) {
                // System.out.println("Check Upgrade Room 2");
                Room newRoom = new Room(upgradeHouse.getNewRoom(), null, null, null, null);
                if (upgradeHouse.getDirection().equalsIgnoreCase("Top")) {
                    // System.out.println("Check Upgrade Room 3");
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
                //hapus sim dari arrayList daftarUpgradeHouse
                daftarUpgradeRumah.remove(upgradeHouse);
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

    //getter deliveryItemsFurniture
    public ArrayList<DeliveryItem<Furniture>> getDeliveryItemsFurniture() {
        return deliveryItemsFurniture;
    }

    //getter deliveryItemsBahanMakanan
    public ArrayList<DeliveryItem<BahanMakanan>> getDeliveryItemsBahanMakanan() {
        return deliveryItemsBahanMakanan;
    }
    

    //add deliveryItem to deliveryItemsFurniture
    public void addDeliveryItemFurniture(DeliveryItem<Furniture> deliveryItem) {
        deliveryItemsFurniture.add(deliveryItem);
    }

    //add deliveryItem to deliveryItemsBahanMakanan
    public void addDeliveryItemBahanMakanan(DeliveryItem<BahanMakanan> deliveryItem) {
        deliveryItemsBahanMakanan.add(deliveryItem);
    }

    //remove deliveryItem from deliveryItemsFurniture
    public void removeDeliveryItemFurniture(DeliveryItem<Furniture> deliveryItem) {
        deliveryItemsFurniture.remove(deliveryItem);
    }

    //remove deliveryItem from deliveryItemsBahanMakanan
    public void removeDeliveryItemBahanMakanan(DeliveryItem<BahanMakanan> deliveryItem) {
        deliveryItemsBahanMakanan.remove(deliveryItem);
    }
    //mengurangi semua waktu deliveryItem pada deliveryItemsFurniture dengan parameter waktu 
    public void kurangiWaktuDeliveryItemFurniture(int waktu) {
        for (DeliveryItem<Furniture> deliveryItem : deliveryItemsFurniture) {
            deliveryItem.kurangiWaktu(waktu);
        }
    }

    //mengurangi semua waktu deliveryItem pada deliveryItemsBahanMakanan dengan parameter waktu
    public void kurangiWaktuDeliveryItemBahanMakanan(int waktu) {
        for (DeliveryItem<BahanMakanan> deliveryItem : deliveryItemsBahanMakanan) {
            deliveryItem.kurangiWaktu(waktu);
        }
    }
    
    // public void checkWaktuDeliveryItem() {
    //     for (DeliveryItem<T> deliveryItem : deliveryItems) {
    //         int waktu = deliveryItem.getWaktu();
    //         if (waktu <= 0) {
    //             T item = deliveryItem.getTipeObjek();
    //             String namaObjek = deliveryItem.getNamaObjek();
    //             Sim sim = deliveryItem.getSim();
    //             int quantity = deliveryItem.getQuantity();
    //             if (item instanceof BahanMakanan) {
    //                 sim.getInventoryBahanMakanan().tambahStock((BahanMakanan) item, quantity);
    //             } else if (item instanceof Furniture) {
    //                 sim.getInventoryFurniture().tambahStock((Furniture) item, quantity);
    //             }
    //             System.out.println(namaObjek + " telah diterima oleh " + sim.getNamaLengkap() + " sejumlah" + quantity);
    //             deliveryItems.remove(deliveryItem);
    //         } 
    //     }
    // }

    public void checkWaktuDeliveryItemFurniture() {
        Iterator<DeliveryItem<Furniture>> iterator = deliveryItemsFurniture.iterator();
        while (iterator.hasNext()) {
            DeliveryItem<Furniture> deliveryItem = iterator.next();
            int waktu = deliveryItem.getWaktu();
            if (waktu <= 0) {
                Furniture item = deliveryItem.getTipeObjek();
                String namaObjek = deliveryItem.getNamaObjek();
                Sim sim = deliveryItem.getSim();
                int quantity = deliveryItem.getQuantity();
                sim.getInventoryFurniture().tambahStock(item, quantity);
                System.out.println(namaObjek + " telah diterima oleh " + sim.getNamaLengkap() + " sejumlah " + quantity);
                iterator.remove();
            }
        }
    }
    
    public void checkWaktuDeliveryItemBahanMakanan() {
        Iterator<DeliveryItem<BahanMakanan>> iterator = deliveryItemsBahanMakanan.iterator();
        while (iterator.hasNext()) {
            DeliveryItem<BahanMakanan> deliveryItem = iterator.next();
            int waktu = deliveryItem.getWaktu();
            if (waktu <= 0) {
                BahanMakanan item = deliveryItem.getTipeObjek();
                String namaObjek = deliveryItem.getNamaObjek();
                Sim sim = deliveryItem.getSim();
                int quantity = deliveryItem.getQuantity();
                sim.getInventoryBahanMakanan().tambahStock(item, quantity);
                System.out.println(namaObjek + " telah diterima oleh " + sim.getNamaLengkap() + " sejumlah " + quantity);
                iterator.remove();
            }
        }
    }
    
    
    //print all deliveryItemsFurniture and deliveryItemsBahanMakanan information
    public void printAllDeliveryItems() {
        if (deliveryItemsFurniture.isEmpty() && deliveryItemsBahanMakanan.isEmpty()) {
            System.out.println("Tidak ada delivery item yang sedang berlangsung.");
            return;
        }

        System.out.println("Daftar delivery item yang sedang berlangsung:");
        for (DeliveryItem<Furniture> deliveryItem : deliveryItemsFurniture) {
            System.out.println("Nama objek: " + deliveryItem.getNamaObjek());
            System.out.println("Sim: " + deliveryItem.getSim().getNamaLengkap());
            System.out.println("Waktu: " + deliveryItem.getWaktu());
            System.out.println("Quantity: " + deliveryItem.getQuantity());
            System.out.println();
        }
        for (DeliveryItem<BahanMakanan> deliveryItem : deliveryItemsBahanMakanan) {
            System.out.println("Nama objek: " + deliveryItem.getNamaObjek());
            System.out.println("Sim: " + deliveryItem.getSim().getNamaLengkap());
            System.out.println("Waktu: " + deliveryItem.getWaktu());
            System.out.println("Quantity: " + deliveryItem.getQuantity());
            System.out.println();
        }
    }

    // public void checkIsDead() {
    //     for(Sim sim : simList){
    //         if (sim.getKekenyangan() <= 0 || sim.getKesehatan() <= 0 || sim.getMood() <= 0) {

    //             if(sim.getKekenyangan() <= 0){
    //                 System.out.println("Sim " + sim.getNamaLengkap() + " mati karena kelaparan");
    //             }
    //             else if(sim.getKesehatan() <= 0){
    //                 System.out.println("Sim " + sim.getNamaLengkap() + " mati karena sakit");
    //             }
    //             else if(sim.getMood() <= 0){
    //                 System.out.println("Sim " + sim.getNamaLengkap() + " mati karena depresi");
    //             }

    //             //set every sim house that have entered died sim house to their ownhouse dengan parameter nama masing masing house
    //             for (Sim anotherSim : simList){
    //                 if(anotherSim.getHouse().getHouseName().equalsIgnoreCase(sim.getHouse().getHouseName())){
    //                     anotherSim.setHouse(anotherSim.getOwnHouse());
    //                 }
    //             }
                
    //             // Remove upgradeHouse from daftarUpgradeRumah if any
    //             if (sim.getUpgradeHouse() != null) {
    //                 for (UpgradeHouse uh : daftarUpgradeRumah) {
    //                     if (uh.getSim().getNamaLengkap().equalsIgnoreCase(sim.getNamaLengkap())) {
    //                         daftarUpgradeRumah.remove(uh);
    //                         break;
    //                     }
    //                 }
    //             }
                
    //             // Remove delivery items
    //             deliveryItemsFurniture.removeIf(item -> item.getSim().getNamaLengkap().equalsIgnoreCase(sim.getNamaLengkap()));
    //             deliveryItemsBahanMakanan.removeIf(item -> item.getSim().getNamaLengkap().equalsIgnoreCase(sim.getNamaLengkap()));
        
    //             // Remove sim from waktuTidakTidur and waktuTidakBuangAir maps
    //             waktuTidakTidur.remove(sim);
    //             waktuTidakBuangAir.remove(sim);
        
    //             //Remove House from map
    //             int[] houseLocation = getHouseLocation(sim.getHouse());
    //             setHouse(houseLocation[0] , houseLocation[1] , null);

                
    //             // Remove sim from simList
    //             simList.remove(sim);
                
    //         }
    //     }
        
    // }

    public void checkIsDead() {
        Iterator<Sim> iterator = simList.iterator();
        while (iterator.hasNext()) {
            // System.out.println("Masuk Sini");
            Sim sim = iterator.next();
            if (sim.getKekenyangan() <= 0 || sim.getKesehatan() <= 0 || sim.getMood() <= 0) {
                // System.out.println("Masuk Sini 2");
                if (sim.getKekenyangan() <= 0) {
                    System.out.println("Sim " + sim.getNamaLengkap() + " mati karena kelaparan");
                } else if (sim.getKesehatan() <= 0) {
                    System.out.println("Sim " + sim.getNamaLengkap() + " mati karena sakit");
                } else if (sim.getMood() <= 0) {
                    System.out.println("Sim " + sim.getNamaLengkap() + " mati karena depresi");
                }
                //set every sim house that have entered died sim house to their ownhouse dengan parameter nama masing masing house
                for (Sim anotherSim : simList) {
                    if (anotherSim.getHouse().getHouseName().equalsIgnoreCase(sim.getOwnHouse().getHouseName())) {
                        anotherSim.setHouse(anotherSim.getOwnHouse());
                    }
                }
                // Remove upgradeHouse from daftarUpgradeRumah if any
                if (sim.getUpgradeHouse() != null) {
                    Iterator<UpgradeHouse> upgradeHouseIterator = daftarUpgradeRumah.iterator();
                    while (upgradeHouseIterator.hasNext()) {
                        UpgradeHouse uh = upgradeHouseIterator.next();
                        if (uh.getSim().getNamaLengkap().equalsIgnoreCase(sim.getNamaLengkap())) {
                            upgradeHouseIterator.remove();
                            break;
                        }
                    }
                }
                // Remove delivery items
                deliveryItemsFurniture.removeIf(item -> item.getSim().getNamaLengkap().equalsIgnoreCase(sim.getNamaLengkap()));
                deliveryItemsBahanMakanan.removeIf(item -> item.getSim().getNamaLengkap().equalsIgnoreCase(sim.getNamaLengkap()));
                // Remove sim from waktuTidakTidur and waktuTidakBuangAir maps
                waktuTidakTidur.remove(sim);
                waktuTidakBuangAir.remove(sim);
                //Remove House from map
                int[] houseLocation = getHouseLocation(sim.getHouse());
                setHouse(houseLocation[0], houseLocation[1], null);
                // Remove sim from simList
                iterator.remove();
            }
        }
    }
    

    

    //check waktu tidak tidur 
    public void checkWaktuTidakTidur() {
        for (Sim sim : simList) {
            if (getWaktuTidakTidur(sim.getNamaLengkap()) <= 0) {
                sim.efekTidakTidur();
                waktuTidakTidur.remove(sim);
                System.out.println(sim.getNamaLengkap() + " tidak tidur dalam 10 menit di hari ini " + "( hari ke-" + getHariDunia() + " )");
            }
        }
    }

    //Check Waktu Tidak Buang Air
    // public void checkWaktuTidakBuangAir() {
    //     for (Sim sim : simList) {
    //         if (getWaktuTidakBuangAir(sim.getNamaLengkap()) <= 0) {
    //             sim.efekTidakBuangAir();
    //         }
    //     }
    // }
    //Mengurangi Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakBuangAir
    // public void reduceWaktuTidakBuangAir(String name , int waktu){
    //     //ngecek apakah waktuTidakBuangAir empty atau tidak
    //     if(!(waktuTidakBuangAir.isEmpty())){
    //         for (Sim sim : waktuTidakBuangAir.keySet()){
    //             if (sim.getNamaLengkap().equalsIgnoreCase(name)){
    //                 int currentTime = waktuTidakBuangAir.get(sim);
    //                 waktuTidakBuangAir.remove(sim);
    //                 waktuTidakBuangAir.put(sim , currentTime - waktu);
    //             }
    //         }
    //     }
    // }

    public void checkWaktuTidakBuangAir(String name , int waktu){
        //ngecek apakah waktuTidakBuangAir empty atau tidak
        if(!(waktuTidakBuangAir.isEmpty())){
            for (Sim sim : waktuTidakBuangAir.keySet()){
                int reduceCurrentTime = waktuTidakBuangAir.get(sim) - waktu;
                if (reduceCurrentTime <= 0){
                    sim.efekTidakBuangAir();
                    // waktuTidakBuangAir.remove(sim);
                    waktuTidakBuangAir.put(sim , 240);
                } else {
                    waktuTidakBuangAir.put(sim , reduceCurrentTime);
                }
                
                
            }
        }
    }
 

    //Mengurangi Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakTidur
    public void reduceWaktuTidakTidur(String name, int waktu) {
       
            Iterator<Map.Entry<Sim, Integer>> iterator = waktuTidakTidur.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Sim, Integer> entry = iterator.next();
                if (entry.getKey().getNamaLengkap().equals(name)) {
                    int currentTime = entry.getValue();
                    // iterator.remove(); // hapus entry dengan iterator
                    waktuTidakTidur.put(entry.getKey(), currentTime - waktu); // tambahkan kembali entry dengan nilai baru
                }
            }
        
    }
    
    
    

    //Menambah Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakBuangAir
    public void increaseWaktuTidakBuangAir(String name , int waktu){
        for (Sim sim : waktuTidakBuangAir.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                int currentTime = waktuTidakBuangAir.get(sim);
                // waktuTidakBuangAir.remove(sim);
                waktuTidakBuangAir.put(sim , currentTime + waktu);
            }
        }
    }

    //Menambah Nilai integer berdasarkan paramater String simname pada hashmap waktuTidakTidur
    public void increaseWaktuTidakTidur(String name , int waktu){
        for (Sim sim : waktuTidakTidur.keySet()){
            if (sim.getNamaLengkap().equals(name)){
                int currentTime = waktuTidakBuangAir.get(sim);
                // waktuTidakBuangAir.remove(sim);
                waktuTidakBuangAir.put(sim , currentTime + waktu);
            }
        }
    }


    //Check Aksi Pasif
    public void checkWaktuSetelahAksi(String nama,int waktuAksi)
    {
        addWaktuDunia(waktuAksi);
        // reduceWaktuTidakBuangAir(nama, waktuAksi);
        //Udah aku tangani di checkWaktuTidakBuangAir
        reduceWaktuTidakTidur(nama, waktuAksi);
        kurangiWaktuUpgrade(waktuAksi);
        checkUpgradeRoom();  
        checkWaktuTidakBuangAir(nama , waktuAksi);
        checkWaktuTidakTidur();
        kurangiWaktuDeliveryItemFurniture(waktuAksi);
        kurangiWaktuDeliveryItemBahanMakanan(waktuAksi);
        checkWaktuDeliveryItemBahanMakanan();
        checkWaktuDeliveryItemFurniture();
        checkIsDead();
    }

    
}



