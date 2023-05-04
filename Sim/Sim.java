package Sim;

import java.util.*;
import Inventory.*;
import Map.*;
import Fitur.*;import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sim {
    private static Thread thread;
    private String namaLengkap;
    // private String pekerjaan;
    private Job pekerjaan;
    private int uang;
    private Inventory<BahanMakanan> inventoryBahanMakanan;
    private Inventory<Furniture> inventoryFurniture;
    private Inventory<Masakan> inventoryMasakan;
    private int kekenyangan;
    private int mood;
    private int kesehatan;
    private String status;
    private World world;
    private House house;
    private Room room;
    private Boolean isDead;
    private UpgradeHouse upgradeHouse;
    private Boolean isInHouse;
    private House ownHouse;
    // private DeliveryItem<Furniture> deliveryFurniture;
    // private DeliveryItem<BahanMakanan> deliveryBahanMakanan;
    
    // Reset saat ganti kerja
    private int totalWaktuKerja;
    // private int jedaGantiKerja = 720;
    private Boolean isGantiKerja;
    
    // Reset jika ganti hari
    private int sisaWaktuTidur = 0;
    private int waktuTidakTidur = 0;

    // Reset jika sudah buang air
    private int waktuTidakBuangAir = 0;
    private boolean isBuangAir = false;


    public Sim(String namaLengkap) {
        this.namaLengkap = namaLengkap;
        this.uang = 100;
        this.kekenyangan = 80;
        this.mood = 80;
        this.kesehatan = 80;
        this.inventoryBahanMakanan = new Inventory<>();
        this.inventoryFurniture = new Inventory<>();
        //Menambah Objek yang wajib ada saat pertama kali objek Sim di instantiasi
        for (Furniture furniture : Furniture.values()) {
            if (!(furniture.getName().equalsIgnoreCase("Komputer")) && !(furniture.getName().equalsIgnoreCase("Bola Kristal")) && !(furniture.getName().equalsIgnoreCase("Kitab Suci")) && !(furniture.getName().equalsIgnoreCase("Sapu")) && !(furniture.getName().equalsIgnoreCase("Sofa")) && !(furniture.getName().equalsIgnoreCase("Kotak Obat")) && !(furniture.getName().equalsIgnoreCase("Microphone"))){
                inventoryFurniture.tambahStock(furniture,1);
            } 
            
        }
        this.inventoryMasakan = new Inventory<>();
        this.status = "Tidak melakukan apa-apa";
        // this.pekerjaan = getRandomPekerjaan();
        // this.job = Job.valueOf(pekerjaan.toUpperCase().replace(" ", "_"));
        this.pekerjaan = getRandomJob();
        this.world = World.getInstance();
        this.house = generateRandomHouse();
        this.room = house.getRoom("Living Room");
        this.isDead = false;
        this.upgradeHouse = null;
        this.isInHouse = true;
        this.ownHouse = this.house;
        this.isGantiKerja = false;
        this.totalWaktuKerja = 0;
        // this.deliveryFurniture = new DeliveryItem<>();
        // this.deliveryBahanMakanan = new DeliveryItem<>();
    }

    // //fitur ngecek apakah input merupakan integer
    // public int readInteger(Scanner scanner) {
    //     System.out.print("Enter an integer: ");
    //     while (!scanner.hasNextInt()) {
    //         System.out.print("Invalid input. Please enter an integer: ");
    //         scanner.nextLine();
    //     }
    //     int number = scanner.nextInt();
    //     scanner.nextLine();
    //     return number;
    // }

    //fitur mengecek apakah sebuah string hanya mengandung angka 
    public boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    //fitur yang mengembalikan nilai integer dan berfungsi untuk meminta input dari user sebuah string dan akan berhenti bila boolean isNumeric bernilai true
    public int readInteger(Scanner scanner) {
        System.out.print("Enter an integer: ");
        String input = scanner.nextLine();
        while (!isNumeric(input)) {
            System.out.print("Invalid input. Please enter an integer: ");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    //Menggenerate Pekerjaan secara random 
    private Job getRandomJob() {
        Job[] jobs = Job.values();
        Random random = new Random();
        return jobs[random.nextInt(jobs.length)];
    }

    //Menggenerate House secara random
    private House generateRandomHouse() {
        Random rand = new Random();
        World world = World.getInstance();
        int x, y;
        do {
            x = rand.nextInt(64);
            y = rand.nextInt(64);
        } while (world.getHouse(x, y) != null);
        House house = new House("H" + String.format("%04d", (x*y) + y) + " ");
        world.setHouse(x, y, house);
        return house;
    }

    //getter nama lengkap sim
    public String getNamaLengkap() {
        return namaLengkap;
    }

    //setter nama lengkap sim
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    //getter pekerjaan sim
    public Job getPekerjaan() {
        return pekerjaan;
    }

    //setter pekerjaan sim
    public void setPekerjaan(Job pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    //getter uang sim
    public int getUang() {
        return uang;
    }

    //setter uang sim
    public void setUang(int uang) {
        this.uang = uang;
    }

    // public Inventory getInventory() {
    //     return inventory;
    // }

    // public void setInventory(Inventory inventory) {
    //         this.inventory = inventory;
    //     }

    //getter kekenyangan sim
    public Inventory<BahanMakanan> getInventoryBahanMakanan() {
        return inventoryBahanMakanan;
    }

    //setter kekenyangan sim
    public void setInventoryBahanMakanan(Inventory<BahanMakanan> inventoryBahanMakanan) {
        this.inventoryBahanMakanan = inventoryBahanMakanan;
    }

    //getter mood sim
    public Inventory<Furniture> getInventoryFurniture() {
        return inventoryFurniture;
    }

    //setter mood sim
    public void setInventoryFurniture(Inventory<Furniture> inventoryFurniture) {
        this.inventoryFurniture = inventoryFurniture;
    }

    //getter kesehatan sim
    public Inventory<Masakan> getInventoryMasakan() {
        return inventoryMasakan;
    }

    //setter kesehatan sim
    public void setInventoryMasakan(Inventory<Masakan> inventoryMasakan) {
        this.inventoryMasakan = inventoryMasakan;
    }
    

    //getter upgradeHouse
    public UpgradeHouse getUpgradeHouse() {
        return upgradeHouse;
    }

    //setter upgradeHouse
    public void setUpgradeHouse(UpgradeHouse inputUpgradeHouse) {
        //Melakukan Looping untuk mencari apakah Sim ini sudah memiliki upgradeHouse didalam list daftarUpgradeRumah
        for (UpgradeHouse upgradeHouse1 : world.getDaftarUpgradeHouse()) {
            if (upgradeHouse1.getSim().getNamaLengkap().equalsIgnoreCase(this.getNamaLengkap())) {
                //Menghapus upgradeHouse milik Sim ini pada list daftarUpgradeRumah
                world.getDaftarUpgradeHouse().remove(getUpgradeHouse());
            }
            
            
        }
        if(inputUpgradeHouse != null){
            this.upgradeHouse = upgradeHouse;
            //menambahakan upgradeHouse kedalam list daftarUpgradeRumah
            world.getDaftarUpgradeHouse().add(inputUpgradeHouse);
        }
    }
    //getter isInHouse
    public Boolean getIsInHouse() {
        return isInHouse;
    }

    //getter checkSsInHouse 
    public Boolean checkIsInHouse(){
        if(getHouse() != getOwnHouse())
        {
             setInHouse(false);
             return false;
        }
        else
        {
             setInHouse(true);
             return true;
        }
     }
 
     //setter isInHouse
     public void setInHouse(Boolean isInHouse){
         this.isInHouse = isInHouse;
     }

    //getter ownHouse
    public House getOwnHouse(){
        return this.ownHouse;
    }

    //setter ownHouse
    public void setOwnHouse(House ownHouse){
        this.ownHouse = ownHouse;
    }
    // Print all Inventory
    public void printAllInventory() {
        System.out.println("Inventory Bahan Makanan");
        this.inventoryBahanMakanan.printInventory();
        System.out.println("");
        System.out.println("Inventory Furniture");
        this.inventoryFurniture.printInventory();
        System.out.println("");
        System.out.println("Inventory Masakan");
        this.inventoryMasakan.printInventory();
        
    }

    // getter kekenyangan sim
    public int getKekenyangan() {
        return kekenyangan;
    }

    // setter kekenyangan sim
    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
        if(this.kekenyangan >= 100)
        {
            this.kekenyangan = 100;
        }
        else if (this.kekenyangan < 0){
            this.kekenyangan = 0;
        }
    }

    // getter mood sim
    public int getMood() {
        return mood;
    }

    // setter mood sim
    public void setMood(int mood) {
        this.mood = mood;
        if(this.mood >= 100)
        {
            this.mood = 100;
        }
        else if (this.mood < 0){
            this.mood = 0;
        }
    }

    // getter kesehatan sim
    public int getKesehatan() {
        return kesehatan;
    }

    // setter kesehatan sim
    public void setKesehatan(int kesehatan) {
        this.kesehatan = kesehatan;
        if(this.kesehatan >= 100)
        {
            this.kesehatan = 100;
        }
        else if (this.kesehatan < 0){
            this.kesehatan = 0;
        }
    }

    // getter status aksi sim
    public String getStatus() {
        return status;
    }

    // setter status aksi sim
    public void setStatus(String status) {
        this.status = status;
    }

    // private String getRandomPekerjaan() {
    //     // ArrayList<String> pekerjaanList = new ArrayList<>(Arrays.asList("Guru", "Dokter", "Pengacara", "Programmer", "Pengusaha", "Arsitek", "Petani"));
    //     ArrayList<String> pekerjaanList = this.job;
    //     Random rand = new Random();
    //     int index = rand.nextInt(this.job.size());
    //     return pekerjaanList.get(index);
    // }

    // getter pekerjaan sim
    public House getHouse(){
        return this.house;
    }
    
    //setter pekerjaan sim
    public void setHouse(House house){
        this.house = house;
    }

    //getter ruangan yang sedang dikunjungi sim
    public Room getRoom(){
        return this.room;
    }

    //setter ruangan yang sedang dikunjungi sim
    public void setRoom(Room room){
        this.room = room;
    }

    //getter isGantiKerja
    public Boolean getIsGantiKerja(){
        return this.isGantiKerja;
    }

    //setter isGantiKerja
    public void setIsGantiKerja(Boolean isGantiKerja){
        this.isGantiKerja = isGantiKerja;
    }

    //getter totalWaktuKerja
    public int getTotalWaktuKerja(){
        return this.totalWaktuKerja;
    }

    //setter totalWaktuKerja
    public void setTotalWaktuKerja(int totalWaktuKerja){
        this.totalWaktuKerja = totalWaktuKerja;
    }


    //getter isDead sim
    public Boolean isDead(){
        if(this.getKekenyangan() <= 0 || this.getKesehatan() <= 0 || this.getMood() < 0){

            // //menghapus sim dari list of Sim di world
            // world.removeSim(this.getNamaLengkap());

            // //menghapus rumah Sim dari world
            // int[] houseLocation = world.getHouseLocation(house);
            // world.setHouse(houseLocation[0] , houseLocation[1] , this.getHouse())
            return true;
        }
        else{
            return false;
        }
    }

    //getter info sim
    public void viewSimInfo() {
        System.out.println("  ______   ______  ______   ______  ");
        System.out.println(" /\\  ___\\ /\\__  _\\/\\  __ \\ /\\__  _\\ ");
        System.out.println(" \\ \\___  \\\\/_/\\ \\/\\ \\  __ \\\\/_/\\ \\/ ");
        System.out.println("  \\/\\_____\\  \\ \\_\\ \\ \\_\\ \\_\\  \\ \\_\\ ");
        System.out.println("   \\/_____/   \\/_/  \\/_/\\/_/   \\/_/ ");

        System.out.println("Nama Lengkap: " + namaLengkap);
        System.out.println("Pekerjaan: " + pekerjaan);
        System.out.println("Uang: " + uang);
        System.out.println("Kekenyangan: " + kekenyangan);
        System.out.println("Mood: " + mood);
        System.out.println("Kesehatan: " + kesehatan);
        System.out.println("Status: " + status);
        System.out.println("Rumah: " + house.getHouseName());
        System.out.println("Ruangan: " + room.getRoomName());
    }

    //getter sim stat
    public void printStat()
    {
        System.out.println("=========SIM SELESAI " + this.getStatus().toUpperCase() + "=========");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
        System.out.println("Uang anda sekarang: " + getUang());
    }

    // // Ganti Hari
    // public void gantiHari() {
    //     waktuTidakTidur = 0;
    //     waktuTidakBuangAir = 0;
    // }

    //print buku resep masakan 
    public void printBukuResep(){
        System.out.printf("-----------------------------------------------%n");
        System.out.printf("|          BUKU RESEP SIMPLICITY              |%n");

        System.out.printf("-----------------------------------------------%n");
        System.out.printf("| %-15s | %-25s |%n", "MAKANAN", "BAHAN MAKANAN");
        System.out.printf("-----------------------------------------------%n");

        System.out.printf("| %-15s | %-25s |%n", "Nasi Ayam", "Nasi, Ayam");
        System.out.printf("| %-15s | %-25s |%n", "Nasi Kari", "Nasi, Kentang, Wortel");
        System.out.printf("| %-15s | %-25s |%n", "Susu Kacang", "Susu, Kacang");
        System.out.printf("| %-15s | %-25s |%n", "Tumis Sayur", "Wortel, Bayam");
        System.out.printf("| %-15s | %-25s |%n", "Bistik", "Kentang, Sapi");
        

        System.out.printf("-----------------------------------------------%n");
    }

    // Lokasi SIM
    public void lokasiSIM()
    {
        System.out.println("===========LOKASI SIM===========");
        System.out.println("SIM berada pada rumah dengan koordinaat: "); 
        for (House house : world.getHouses()) {
            if (house.getHouseName().equalsIgnoreCase(this.getHouse().getHouseName())){
                int[] houseLocation = world.getHouseLocation(house);
            System.out.println("Koordinat rumah: (" + houseLocation[0] + ", " + houseLocation[1] + ")");

            }
        }
        System.out.println("SIM berada di dalam ruangan: " + getRoom().getRoomName());
    }

    //Aksi melihat Waktu 
    public void lihatWaktu() {
        System.out.println("Hari ke-" + world.getHariDunia() + ", Sisa waktu Sim: " + world.getWaktuSim());
        if (upgradeHouse != null) {
            int waktuUpgrade = upgradeHouse.getWaktuUpgrade();
            System.out.println("Waktu tersisa untuk upgrade rumah: " + waktuUpgrade + " menit");
        }
        if (!world.getDeliveryItemsFurniture().isEmpty()) {
            System.out.println("Delivery Furniture:");
            Iterator<DeliveryItem<Furniture>> iterator = world.getDeliveryItemsFurniture().iterator();
            while (iterator.hasNext()) {
                DeliveryItem<Furniture> deliveryItem = iterator.next();
                int waktuDelivery = deliveryItem.getWaktu();
                String namaObjek = deliveryItem.getNamaObjek();
                System.out.println("- " + namaObjek + " (" + waktuDelivery + " menit)");
            }
        }
        if (!world.getDeliveryItemsBahanMakanan().isEmpty()) {
            System.out.println("Delivery Bahan Makanan:");
            Iterator<DeliveryItem<BahanMakanan>> iterator = world.getDeliveryItemsBahanMakanan().iterator();
            while (iterator.hasNext()) {
                DeliveryItem<BahanMakanan> deliveryItem = iterator.next();
                int waktuDelivery = deliveryItem.getWaktu();
                String namaObjek = deliveryItem.getNamaObjek();
                System.out.println("- " + namaObjek + " (" + waktuDelivery + " menit)");
            }
        }
    }

    // Aksi Kerja
    public void kerja(int lamaKerja)
    {   
        if(!(this.getIsGantiKerja()) && lamaKerja%120 == 0)
        {
            world.checkIsGantiHari(lamaKerja);
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try {
                        System.out.println("==========SIM SEDANG BEKERJA==========");
                        // System.out.println("        .-------,     ");
                        // System.out.println("     ../         \\  ");
                        // System.out.println("    /  ,   ,   ,  \\ ");
                        // System.out.println("   /  , \\__\\___\\   \\ ");
                        // System.out.println("  |   | __ || __',. \\  ");
                        // System.out.println("  |   \\_'_/ \\_'_/.   |  Kerja YUKK!");
                        // System.out.println("  |  (|    v    |)   |  ---   -----");
                        // System.out.println("  ,    |       |    .       /");
                        // System.out.println("   |    \\  ~  /     |   ---'");
                        // System.out.println("   |   /. | | .\\    .");
                        // System.out.println("   / ,/ |/   \\| \\,  |,");
                        // System.out.println("  ( <-,  \\___/  ,->   )");
                        // System.out.println("   |  ,_ \\   / _,  .|");
                        // System.out.println("   | \\  \\ \\ / /   / |");
                        // System.out.println("   | |   \\ * /    | |");
                        // System.out.println("   | |     #      | |");
                        System.out.println("  ");
                        
                        Thread.sleep(lamaKerja*1000);
            
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally{
                        int kenyangTurun = getKekenyangan()+(lamaKerja/30)*(-10);
                        setKekenyangan(kenyangTurun);
                        int moodTurun = getMood() + (lamaKerja/30)*(-10);
                        setMood(moodTurun);
            
                        totalWaktuKerja += lamaKerja;
                        
                        if(totalWaktuKerja >= 240)
                        {
                            if(pekerjaan.getName().equalsIgnoreCase("Badut Sulap"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/240) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Koki"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/240) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Polisi"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/240) *getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Programmer"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/240) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Dokter"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/240) *getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
            
                            totalWaktuKerja = totalWaktuKerja - ((totalWaktuKerja-240));
                        }

                        // print stats
                        System.out.println("=========SIM SELESAI BEKERJA=========");
                        System.out.println("Anda bekerja selama " + lamaKerja + " detik");
                        printStat();

                        // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        } 
                        setStatus("Sedang Bekerja");
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaKerja);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }
                    }
                        // System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
                        // System.out.println("Mood anda sekarang: " + getMood());
                        // System.out.println("Uang anda sekarang: " + getUang());
                        // System.out.println(" ");
                }
            });
            thread.run();
        }
        
        else
        {
            System.out.println(this.getNamaLengkap() + " telah mengganti kerja di hari ini , silahkan coba di hari berikutnya");
        }
    }
    
    public void olahraga(int lamaOlahraga)
    {
        if(lamaOlahraga%20 == 0)
        {   
            world.checkIsGantiHari(lamaOlahraga);
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try
                    {
                        System.out.println("==========SIM SEDANG OLAHRAGA==========");
                        // System.out.println("     ");
                        // System.out.println("                        ,////,");
                        // System.out.println("                        /// 6|");
                        // System.out.println("                        //  _|");
                        // System.out.println("                       _/_,-'");
                        // System.out.println("                  _.-/'/   \\   ,/,;");
                        // System.out.println("               ,-' /'  \\_   \\ / _/");
                        // System.out.println("               `\\ /     _/\\  ` /");
                        // System.out.println("                 |     /,  `\\_/");
                        // System.out.println("                 |     \\'");
                        // System.out.println("    /\\_        /`      /\\");
                        // System.out.println("   /' /_``--.__/\\  `,. /  \\");
                        // System.out.println("  |_/`  `-._     `\\/  `\\   `.");
                        // System.out.println("            `-.__/'     `\\   |");
                        // System.out.println("                          `\\  \\");
                        // System.out.println("                            `\\ \\");
                        // System.out.println("                              \\_\\__");
                        // System.out.println("                               \\___)");
        
                        // Nunggu waktu lari
                        Thread.sleep(lamaOlahraga*1000);
                        System.out.println(" ");
                    } 
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally{
                        int kenyangTurun = getKekenyangan() + lamaOlahraga/20*(-5);
                        setKekenyangan(kenyangTurun);
                        int moodNaik = getMood() + lamaOlahraga/20*10;
                        setMood(moodNaik);
                        int kesehatanNaik = getKesehatan() + lamaOlahraga/20*5;
                        setKesehatan(kesehatanNaik);

                        // print stats
                        System.out.println("=========SIM SELESAI OLAHRAGA=========");
                        System.out.println("Anda bekerja selama " + lamaOlahraga + " detik");
                        printStat();

                        // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        } 
                        setStatus("Sedang Olahraga");
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaOlahraga);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }
                    }
               }
            });
            thread.run();
        }

        else
        {
            System.out.println("Waktu olahraga tidak valid");
        }
        
    }

    //Aksi Tidur
    public void tidur(int lamaTidur)
    {
        if(lamaTidur >= 240 ){
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try {
                        System.out.println("                 _____|~~\\_____      _____________");
                        System.out.println("             _-~               \\    |    \\");
                        System.out.println("             _-    | )     \\    |__/   \\   \\");
                        System.out.println("             _-         )   |   |  |     \\  \\");
                        System.out.println("             _-    | )     /    |--|      |  |");
                        System.out.println("            __-_______________ /__/_______|  |_________");
                        System.out.println("           (                |----         |  |");
                        System.out.println("            `---------------'--\\\\      .`--'          ");
                        System.out.println("                                         `||||");
                        System.out.println("                                          ");

                        // Nunggu Tidur
                        Thread.sleep(lamaTidur*1000);
                        System.out.println(" ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally
                    {
                        int moodNaik = getMood() + (lamaTidur)/240*30;
                        setMood(moodNaik);
                        int kesehatanNaik = getKesehatan()+ (lamaTidur)/240*20;
                        setKesehatan(kesehatanNaik);
                    
                        sisaWaktuTidur += (lamaTidur - ((lamaTidur/240)*240));
                        
                        // print stats
                        System.out.println("=========SIM SEDANG TIDUR=========");
                        System.out.println("Anda tidur selama " + lamaTidur + " detik");
                        System.out.println("Mood anda sekarang: " + getMood());
                        System.out.println("Kesehatan anda sekarang: " + getKesehatan());

                        // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        } 
                        setStatus("Sedang Tidur");
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaTidur);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }
                    }
                    
                    if(sisaWaktuTidur >= 240){
                        int moodNaik = getMood() + (sisaWaktuTidur)/240*30;
                        setMood(moodNaik);
                        int kesehatanNaik = getKesehatan()+ (sisaWaktuTidur)/240*20;
                        setKesehatan(kesehatanNaik);
                        sisaWaktuTidur = (sisaWaktuTidur - ((sisaWaktuTidur/240)*240));
                    }
                }
            });
            thread.run();        
        }

        else
        {
            System.out.println("Waktu tidur tidak valid");
        }

    }
    
    public void efekTidakTidur()
    {
        if(world.getWaktuTidakTidur(getNamaLengkap()) > 600 )
        {
            int moodTurun = getMood() - 5;
            setMood(moodTurun);
            int kesehatanTurun = getKesehatan() - 5;
            setKesehatan(kesehatanTurun);
        }

        // print stats
        System.out.println("=========SIM BUTUH TIDUR=========");
        System.out.println("Anda tidak tidur selama " + world.getWaktuTidakTidur(getNamaLengkap()) + " detik");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
    }

    // Aksi Masak
    public Boolean aksiMasak(Masakan masakan, Inventory<BahanMakanan> inventoryBahanMakanan , Inventory<Masakan> inventoryMasakan) {
        List<BahanMakanan> bahanMakanan = masakan.getBahanMakanan();
        HashMap<BahanMakanan, Integer> stockBahanMakanan = inventoryBahanMakanan.getStock();
        for (BahanMakanan bahan : bahanMakanan) {
            if (stockBahanMakanan.containsKey(bahan)) {
                int jumlah = stockBahanMakanan.get(bahan);
                if (jumlah <= 0) {
                    System.out.println("Maaf, stock bahan makanan " + bahan.getName() + " habis");
                    return false;
                }
            } else {
                System.out.println("Maaf, stock bahan makanan " + bahan.getName() + " tidak ada");
                return false;
            }
        }
        
        for (BahanMakanan bahan : bahanMakanan) {
            inventoryBahanMakanan.kurangiStock(bahan, 1);
        }
        return true;
        
        
    }
    
    public void masak()
    {
        printBukuResep();
        Scanner input = new Scanner(System.in);
        
        System.out.print("Masukkan nama masakan yang ingin dimasak: ");
        String namaMasakan = input.nextLine();

        Boolean inputValid = false;
        Masakan diMasak = null;
        
        for(Masakan masakan: Masakan.values()){
            if(namaMasakan.equalsIgnoreCase(masakan.getNama())){
                inputValid = true;
                diMasak = masakan;
                break;
            }
        }
        
            if(inputValid && (aksiMasak(diMasak, getInventoryBahanMakanan(), getInventoryMasakan()))){
                
                try{
                    System.out.println("     ( ( (              ))     ");
                    System.out.println("      ) ) )           ((       ");
                    System.out.println("     ( ( (          ___o___");
                    System.out.println("   '. ___ .'        |     |====O");
                    System.out.println("  '  (> <) '        |_____|");
                    System.out.println("--ooO-(_)-Ooo--------------------");
                    System.out.println(" ");
                    
                    // Nunggu Masak
                    double waktuMasak = 1.5 * diMasak.getKekenyangan();
                    Thread.sleep((long)waktuMasak);
        
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally
                {
                    double waktuMasak = 1.5 * diMasak.getKekenyangan();

                    // Masak berhasil
                    inventoryMasakan.tambahStock(diMasak, 1);
                    System.out.println("Masakan " + diMasak.getNama() + " berhasil dimasak");
        
                    // Check aksi pasif
                    setStatus("Sedang Makan");
                    printStat();
                    world.checkWaktuSetelahAksi(getNamaLengkap(), (int)waktuMasak);
                }
            }
            else
            {
                System.out.println("Gagal memasak");
            }
        
    }

    public static void showMasakan() {
        System.out.println("List Bahan Makanan yang Dijual:");
        for (Masakan masakan : Masakan.values()) {
            System.out.println("Nama: " + masakan.getNama() +
                               ", Kekenyangan: " + masakan.getKekenyangan());
            System.out.println();
        }
    }

    public void makan(Masakan masakan)
    {
        if(inventoryMasakan.getStock(masakan) > 0)
        {
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try {
                        System.out.println("              ,-------------------.");
                        System.out.println("             ( Tried it, loved it! )");
                        System.out.println("        munch `-v-----------------'");
                        System.out.println(" ,---'. --------'");
                        System.out.println(" C.^o^|   munch");
                        System.out.println(" (_,-_)");
                        System.out.println(",--`|-. ");
                        System.out.println("|\\    ]\\__n_");
                        System.out.println("||`   '---E/   Ojo98");
                        System.out.println(" ");
                        
                        // Makan selama 30 detik
                        Thread.sleep(30*1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    finally
                    {
                        int kenyangNaik = getKekenyangan() + masakan.getKekenyangan();
                        setKekenyangan(kenyangNaik);

                        setStatus("Makan");
                        
                        // Remove masakan from inventory
                        inventoryMasakan.kurangiStock(masakan, 1);
                        
                        // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        } 
                            
                        setStatus("Sedang Makan");
                        printStat();
                        world.addWaktuTidakBuangAir(getNamaLengkap(), 240);
                        world.checkWaktuSetelahAksi(getNamaLengkap(), 30);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }
            
                    }
               }
            });
            thread.run();
        }
        
        else
        {
            System.out.println("Tidak ada masakan tersebut di inventory");
        }
    }

    public void Berkunjung(House visitedHouse , Room visitedRoom)
    {   
        // Cek apakah house terdapat pada private HashMap<House, int[]> houseLocations = new HashMap<>();
        if (world.getHouseLocation(visitedHouse) == null) 
        {
            System.out.println("Rumah tidak terdapat pada World");
        }

        else{
            House house = this.getHouse();
            int[] houseLocation = world.getHouseLocation(visitedHouse);
            int[] currentLocation = world.getHouseLocation(house);
            int selisihX = Math.abs(houseLocation[0]-currentLocation[0]);
            int selisihY = Math.abs(houseLocation[1]-currentLocation[1]);
            
            double waktuPerjalanan = Math.sqrt(Math.pow(selisihX,2) + Math.pow(selisihY,2) );

            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try {
                        
                        // System.out.println("          __      ");
                        // System.out.println("         /\\ `.");
                        // System.out.println("         ^^)_|");
                        // System.out.println("         \\/(_");
                        // System.out.println("           )/,`.");
                        // System.out.println("        __((  \\/");
                        // System.out.println("       /.--|_.L\\\\_");
                        // System.out.println("           \\, \\ \\=");
                        // System.out.println("           / ,/L");
                        // System.out.println("          7 (\\ \\...,_" );
                        // System.out.println("          | | \\____| )");
                        // System.out.println("          ]_|      `\\)");
                        // System.out.println("          /_)  ");
                        // System.out.println("         `\"");  
                        // System.out.println("  ");

                        // Tunggu 
                        Thread.sleep((long)waktuPerjalanan*1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        int moodNaik = getMood() + (int)waktuPerjalanan/30*10;
                        setMood(moodNaik);
                        int kenyangTurun = getKekenyangan() + (int)waktuPerjalanan/30*(-10);
                        setKekenyangan(kenyangTurun);

                        setHouse(visitedHouse);
                        setRoom(visitedHouse.getRoom(visitedRoom.getRoomName()));

                        // Print hasil
                        System.out.println("=========SIM SEDANG BERKUNJUNG=========");
                        System.out.println("Waktu perjalanan: " + waktuPerjalanan + " detik");
                        System.out.println("posisi anda sekarang: X " + houseLocation[0] + ", Y " + houseLocation[1]);

                        // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        } 
                        setStatus("Sedang Berkunjung");
                        world.checkWaktuSetelahAksi(getNamaLengkap(), (int)waktuPerjalanan);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }
                    }
               }
            });
            thread.run();
        }
    }

    //Aksi Buang Air
    public void buangAir()
    {   
        thread = new Thread(new Runnable() 
        {
            public void run()
            {
                try {
                    // System.out.println("          _____");
                    // System.out.println("         /      \\");
                    // System.out.println("        (____/\\  )");
                    // System.out.println("         |___  U?(____");
                    // System.out.println("         _\\L.   |      \\     ___");
                    // System.out.println("       / /\"\"\"\\ /.-'     |   |\\  |");
                    // System.out.println("      ( /  _/u     |    \\___|_)_|");
                    // System.out.println("       \\|  \\\\      /   / \\_(___ __)");
                    // System.out.println("        |   \\\\    /   /  |  |    |");
                    // System.out.println("        |    )  _/   /   )  |    |");
                    // System.out.println("        _\\__/.-'    /___(   |    |    ");
                    // System.out.println("     _/  __________/     \\  |    |");
                    // System.out.println("    //  /  (              ) |    |");
                    // System.out.println("   ( \\__|___\\    \\______ /__|____|");
                    // System.out.println("    \\    (___\\   |______)_/");
                    // System.out.println("     \\   |\\   \\  \\     /");
                    // System.out.println("      \\  | \\__ )  )___/");
                    // System.out.println("       \\  \\  )/  /__(       ");
                    // System.out.println("   ___ |  /_//___|   \\_________");
                    // System.out.println("     _/  ( /          \\");
                    // System.out.println("    `----'(____________)");
                    // System.out.println(" ");
                        
                    // Tunggu 10 detik
                    Thread.sleep(10*1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    int kenyangTurun = getKekenyangan() - 20;
                    setKekenyangan(kenyangTurun);
                    int moodNaik = getMood() + 10;
                    setMood(moodNaik);
                    
                    // waktuTidakBuangAir = 0;
                    isBuangAir = true;
                    
                    System.out.println("Uhhh lega... SIM sudah buang air");

                    // print stats
                    printStat();

                    // Tambahin Waktu ke World
                    if (isDead()){
                        System.out.println("SIM telah meninggal");
                    } 
                    setStatus("Sedang Buang Air");
                    printStat();
                    world.checkWaktuSetelahAksi(getNamaLengkap(), 10);
                    world.removeWaktuTidakBuangAir(getNamaLengkap());
                    
                    //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                    if (world.getIsGantiHari()){
                        System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                    }
                        
                }
            }
        });
        thread.run();
        // ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        // executorService.schedule(() -> {
        //     int moodNaik = getMood() + 10;
        //     setMood(moodNaik);
        //     int kesehatanNaik = getKesehatan() + 5;
        //     setKesehatan(kesehatanNaik);
        //     int kekenyanganTurun = getKekenyangan() - 3;
        //     setKekenyangan(kekenyanganTurun);
    
        //     if (isDead()) {
        //         System.out.println("SIM telah meninggal");
        //     } else {
        //         setStatus("Melawak");
        //         printStat();
        //         world.checkWaktuSetelahAksi(getNamaLengkap(), 10);
        //     }
        // }, 10, TimeUnit.SECONDS);
    }

    public void efekTidakBuangAir()
    {
        if(world.getWaktuTidakBuangAir(getNamaLengkap()) <= 0)
        {
            int moodTurun = getMood() - 5;
            setMood(moodTurun);
            int kenyangTurun = getKekenyangan() - 5;
            setKekenyangan(kenyangTurun);

            // print stats
            System.out.println("Anda tidak buang air selama " + world.getWaktuTidakBuangAir(getNamaLengkap()) + " detik");
            System.out.println("Mood anda sekarang: " + getMood());
            System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
        }
    }

     // AKSI TAMBAHAN
     public void mainGame(int lamaMain)
     {
         if (lamaMain > 0){
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG MAIN GAME==========");
                        //  System.out.println(" _n_________________");
                        //  System.out.println("|_|_______________|_|");
                        //  System.out.println("|  ,-------------.  |");
                        //  System.out.println("| |  .---------.  | |");
                        //  System.out.println("| |  |         |  | |");
                        //  System.out.println("| |  |         |  | |");
                        //  System.out.println("| |  |         |  | |");
                        //  System.out.println("| |  |         |  | |");
                        //  System.out.println("| |  `---------'  | |");
                        //  System.out.println("| `---------------' |");
                        //  System.out.println("|   _ GAME BOY      |");
                        //  System.out.println("| _| |_         ,-. |");
                        //  System.out.println("||_ O _|   ,-. "._,"|");
                        //  System.out.println("|  |_|    "._,"   A | hjw");
                        //  System.out.println("|    _  _    B      | `97");
                        //  System.out.println("|   // //           |");
                        //  System.out.println("|  // //    ///////  |");
                        //  System.out.println("|  `  `      /////// ,");
                        //  System.out.println("|________...______,\"");
                         Thread.sleep(lamaMain*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaMain/10);
                         setMood(moodNaik);
                         int kesehatanTurun = getKesehatan() - (lamaMain/30);
                         setKesehatan(kesehatanTurun);
                         int kekenyanganTurun = getKekenyangan() - (lamaMain/20);
                         setKekenyangan(kekenyanganTurun);
 
                         if (isDead()){
                             System.out.println("SIM telah meninggal");
                        }
                        //  else if(!isMakan)
                        //  {
                        //     world.
                        //  }
                          else {
                             setStatus("Main Game");
                             printStat();
                             world.checkWaktuSetelahAksi(getNamaLengkap(), lamaMain);
                         }
                     }
                 }
             });
             thread.run();
         } else {
             System. out.println("Durasi main game tidak valid!");
         }
     }
 
     public void santet(Sim simLain)
     {
         int waktuDibutuhkan = 60; // default
         if (world.findSim(simLain.getNamaLengkap())){
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                        //  System.out.println("==========SIM SEDANG MENYANTET==========");
                        //  System.out.println("    .-')     ('-.         .-') _  .-') _     ('-.   .-') _    ");
                        //  System.out.println("    ( OO ).  ( OO ).-.    ( OO ) )(  OO) )  _(  OO) (  OO) )   ");
                        //  System.out.println("   (_)---\_) / . --. /,--./ ,--,' /     '._(,------./     '._  ");
                        //  System.out.println("   /    _ |  | \-.  \ |   \ |  |\ |'--...__)|  .---'|'--...__) ");
                        //  System.out.println("   \  :` `..-'-'  |  ||    \|  | )'--.  .--'|  |    '--.  .--' ");
                        //  System.out.println("    '..`''.)\| |_.'  ||  .     |/    |  |  (|  '--.    |  |    ");
                        //  System.out.println("   .-._)   \ |  .-.  ||  |\    |     |  |   |  .--'    |  |    ");
                        //  System.out.println("   \       / |  | |  ||  | \   |     |  |   |  `---.   |  |    ");
                        //  System.out.println("    `-----'  `--' `--'`--'  `--'     `--'   `------'   `--'    ");
                         Thread.sleep(waktuDibutuhkan*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         // this sim
                         int thisMood = getMood() + 15;
                         setMood(thisMood);
                         int thisKesehatan = getKesehatan() - 5;
                         setKesehatan(thisKesehatan);
                         int thisKekenyangan = getKekenyangan() - 5;
                         setKekenyangan(thisKekenyangan);
 
                         // other sim
                         int simLainMood = getMood() - 10;
                         simLain.setMood(simLainMood);
                         int simLainKesehatan = getKesehatan() - 20;
                         simLain.setKesehatan(simLainKesehatan);
                         int simLainKekenyangan = getKekenyangan() - 15;
                         simLain.setKekenyangan(simLainKekenyangan);
 
                         if (isDead()){
                             System.out.println("SIM telah meninggal");
                         } else {
                             setStatus("Santet");
                             printStat();
                             world.checkWaktuSetelahAksi(getNamaLengkap(), waktuDibutuhkan);
                         }
                     }
                 }
             });
             thread.run();
         } else {
             System. out.println("Sim yang anda masukkan tidak ada!");
         }
     }
 
     public void berobat(int lamaBerobat)
     {
         int uangTurun = getUang() - (lamaBerobat/2);
         if ((uangTurun >= 0) && (lamaBerobat>0)){
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG BEROBAT==========");
                         System.out.println("          .         .                                                                       ");
                         System.out.println("         ,8.       ,8.          8 8888888888   8 888888888o.       8 8888     ,o888888o.    ");
                         System.out.println("        ,888.     ,888.         8 8888         8 8888    `^888.    8 8888    8888     `88.  ");
                         System.out.println("       .`8888.   .`8888.        8 8888         8 8888        `88.  8 8888 ,8 8888       `8. ");
                         System.out.println("      ,8.`8888. ,8.`8888.       8 8888         8 8888         `88  8 8888 88 8888           ");
                         System.out.println("     ,8'8.`8888,8^8.`8888.      8 888888888888 8 8888          88  8 8888 88 8888           ");
                         System.out.println("    ,8' `8.`8888' `8.`8888.     8 8888         8 8888          88  8 8888 88 8888           ");
                         System.out.println("   ,8'   `8.`88'   `8.`8888.    8 8888         8 8888         ,88  8 8888 88 8888           ");
                         System.out.println("  ,8'     `8.`'     `8.`8888.   8 8888         8 8888        ,88'  8 8888 `8 8888       .8' ");
                         System.out.println(" ,8'       `8        `8.`8888.  8 8888         8 8888    ,o88P'    8 8888    8888     ,88'  ");
                         System.out.println(",8'         `         `8.`8888. 8 888888888888 8 888888888P'       8 8888     `8888888P'    ");
                         Thread.sleep(lamaBerobat*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaBerobat/30);
                         setMood(moodNaik);
                         int kesehatanNaik = getKesehatan() + (lamaBerobat/10)*2;
                         setKesehatan(kesehatanNaik);
                         
                         if (isDead()) {
                             System.out.println("SIM telah meninggal");
                         } else {
                             setUang(uangTurun);
                             setStatus("Berobat");
                             printStat();
                             world.checkWaktuSetelahAksi(getStatus(), lamaBerobat);
                         }
                     }
                 }
             });
             thread.run();
         } else {
             System. out.println("Uang anda tidak cukup!");
         }
     }
                                          
     public void karaoke(int lamaKaraoke)
     {
         int uangTurun = getUang() - (lamaKaraoke/5);
         if ((uangTurun >= 0) && (lamaKaraoke>0)){
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG KARAOKE==========");
                         System.out.println("     __                            __          ");
                         System.out.println("    |  |--.---.-.----.---.-.-----.|  |--.-----.");
                         System.out.println("    |    <|  _  |   _|  _  |  _  ||    <|  -__|");
                         System.out.println("    |__|__|___._|__| |___._|_____||__|__|_____|");
                         Thread.sleep(lamaKaraoke*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaKaraoke/10);
                         setMood(moodNaik);
                         int kesehatanTurun = getKesehatan() - (lamaKaraoke/30);
                         setKesehatan(kesehatanTurun);
                         int kekenyanganTurun = getKekenyangan() - (lamaKaraoke/20);
                         setKekenyangan(kekenyanganTurun);
                         
                         if (isDead()){
                             System.out.println("SIM telah meninggal");
                         } else {
                             setUang(uangTurun);
                             setStatus("Karaoke");
                             printStat();
                             world.checkWaktuSetelahAksi(getNamaLengkap(), lamaKaraoke);
                         }
                     }
                 }
             });
             thread.run();
         } else {
             System. out.println("Uang anda tidak cukup!");
         }
     }
                                    
     public void puasa()
     {
         int waktuDibutuhkan = 360;
         thread = new Thread(new Runnable() {
             public void run(){
                 try {
                     System.out.println("==========SIM SEDANG PUASA==========");
                     System.out.println("d8888b. db    db  .d8b.  .d8888.  .d8b.  ");
                     System.out.println("88  `8D 88    88 d8' `8b 88'  YP d8' `8b ");
                     System.out.println("88oodD' 88    88 88ooo88 `8bo.   88ooo88 ");
                     System.out.println("88~~~   88    88 88~~~88   `Y8b. 88~~~88 ");
                     System.out.println("88      88b  d88 88   88 db   8D 88   88 ");
                     System.out.println("88      ~Y8888P' YP   YP `8888Y' YP   YP ");
                     Thread.sleep(waktuDibutuhkan*1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } finally {
                     int kesehatanNaik = getKesehatan() + 10;
                     setKesehatan(kesehatanNaik);
                     int kekenyanganTurun = getKekenyangan() - 50;
                     setKekenyangan(kekenyanganTurun);
 
                     if (isDead()) {
                         System.out.println("SIM telah meninggal");
                     } else {
                         setStatus("Puasa");
                         printStat();
                         world.checkWaktuSetelahAksi(getNamaLengkap(), waktuDibutuhkan);
                     }
                 }
             }
         });
         thread.run();
     }
 
     public void bersihBersih(int lamaBersihBersih)
     {
         if (lamaBersihBersih > 0){
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                        //  System.out.println("==========SIM SEDANG BERSIH-BERSIH==========");
                        //  System.out.println("         888                            d8b                   ");
                        //  System.out.println("         888                            Y8P                   ");
                        //  System.out.println("         888                                                  ");
                        //  System.out.println(".d8888b  888  .d88b.   8888b.  88888b.  888 88888b.   .d88b.  ");
                        //  System.out.println("d88P"    888 d8P  Y8b     "88b 888 "88b 888 888 "88b d88P"88b ");
                        //  System.out.println("888      888 88888888 .d888888 888  888 888 888  888 888  888 ");
                        //  System.out.println("Y88b.    888 Y8b.     888  888 888  888 888 888  888 Y88b 888 ");
                        //  System.out.println(""Y8888P  888  "Y8888  "Y888888 888  888 888 888  888  "Y88888 ");
                        //  System.out.println("                                                     888 ");
                        //  System.out.println("                                                Y8b d88P ");
                        //  System.out.println("                                                 \"Y88P\"  ");
                         Thread.sleep(lamaBersihBersih*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaBersihBersih/20);
                         setMood(moodNaik);
                         int kesehatanNaik = getKesehatan() + (lamaBersihBersih/5);
                         setKesehatan(kesehatanNaik);
                         int kekenyanganTurun = getKekenyangan() - (lamaBersihBersih/15);
                         setKekenyangan(kekenyanganTurun);
 
                         if (isDead()) {
                             System.out.println("SIM telah meninggal");
                         } else {
                             setStatus("Bersih-Bersih");
                             printStat();
                             world.checkWaktuSetelahAksi(getNamaLengkap(), lamaBersihBersih);
                         }    
                     }
                 }
             });
             thread.run();
         } else {
             System. out.println("Durasi bersih-bersih tidak valid!");
         }
     }
                                                                          
     public void melawak()
     {
         int waktuDibutuhkan = 20;
         thread = new Thread(new Runnable() {
             public void run(){
                 try {
                     System.out.println("==========SIM SEDANG MELAWAK==========");
                     System.out.println("     ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄ ");
                     System.out.println("    ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌");
                     System.out.println("    ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌");
                     System.out.println("    ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌");
                     System.out.println("    ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌");
                     System.out.println("    ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌");
                     System.out.println("    ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌");
                     System.out.println("    ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌");
                     System.out.println("    ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌");
                     System.out.println("    ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌");
                     System.out.println("     ▀         ▀  ▀         ▀  ▀         ▀  ▀         ▀  ▀         ▀  ▀         ▀ ");
                     Thread.sleep(waktuDibutuhkan*1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } finally {
                     int moodNaik = getMood() + 10;
                     setMood(moodNaik);
                     int kesehatanNaik = getKesehatan() + 5;
                     setKesehatan(kesehatanNaik);
                     int kekenyanganTurun = getKekenyangan() - 3;
                     setKekenyangan(kekenyanganTurun);
 
                     if (isDead()){
                         System.out.println("SIM telah meninggal");
                     } else {
                         setStatus("Melawak");
                         printStat();
                         world.checkWaktuSetelahAksi(getNamaLengkap(), waktuDibutuhkan);
                     }
                 }
             }
         });
         thread.run();
     }


    //Aksi Move To Objek
    public void moveToObject(int x, int y) {
        Scanner scanner = new Scanner(System.in);
        String objName = this.room.getLayoutContent(x , y);
        //Melakukan Cek apakah masukan melebihi peta layout [5] [5] 
        if (x > 5 || y > 5 || x < 0 || y < 0) {
            System.out.println("Posisi yang anda masukkan tidak valid");
        }
        else{
            if (objName != "") {
                // System.out.println("Moving to " + obj.getName() + " at (" + x + "," + y + ")");
                switch(objName) {
                    case "KSS":
                    //Tidur
                    System.out.print("Apakah anda ingin melakukan aksi tiudr ? (y/n)");
                    Scanner input = new Scanner(System.in);
                    String jawaban = input.nextLine();
                    while(!(jawaban.equalsIgnoreCase("y")) || !(jawaban.equalsIgnoreCase("n"))){
                        if (jawaban.equalsIgnoreCase("y")){
                            int number = readInteger(scanner);
                            tidur(number);
                        } else if(jawaban.equalsIgnoreCase("n")) {
                            System.out.println("Anda tidak ingin melakukan aksi tidur");
                        }
                        else{
                            System.out.println("Masukan tidak valid");
                        }
                    }
                        break;

                    case "KQS":
                        //Tidur
                        System.out.print("Apakah anda ingin melakukan aksi tidur ? (y/n)");
                        Scanner input2 = new Scanner(System.in);
                        String jawaban2 = input2.nextLine();
                        while(!(jawaban2.equalsIgnoreCase("y")) || !(jawaban2.equalsIgnoreCase("n"))){
                            if (jawaban2.equalsIgnoreCase("y")){
                                int number = readInteger(scanner);
                                tidur(number);
                            } else if(jawaban2.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi tidur");
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                            }
                        }
                        break;
                    case "KKS":
                    //Tidur
                    System.out.print("Apakah anda ingin melakukan aksi tidur ? (y/n)");
                    Scanner input3 = new Scanner(System.in);
                    String jawaban3 = input3.nextLine();
                    while(!(jawaban3.equalsIgnoreCase("y")) || !(jawaban3.equalsIgnoreCase("n"))){
                        if (jawaban3.equalsIgnoreCase("y")){
                            int number = readInteger(scanner);
                            tidur(number);
                        } else if(jawaban3.equalsIgnoreCase("n")) {
                            System.out.println("Anda tidak ingin melakukan aksi tidur");
                        }
                        else{
                            System.out.println("Masukan tidak valid");
                        }
                    }
                        break;
                        
                    case "TLT":
                        //Buang air
                        System.out.print("Apakah anda ingin melakukan aksi buang air ? (y/n)");
                        Scanner input4 = new Scanner(System.in);
                        String jawaban4 = input4.nextLine();
                        Boolean inputYN = false;
                        while(!(inputYN)){
                            if (jawaban4.equalsIgnoreCase("y")){
                                buangAir();
                                inputYN = true;
                            } else if(jawaban4.equalsIgnoreCase("n")) {
                                inputYN = true;
                                System.out.println("Anda tidak ingin melakukan aksi buang air");
                            }
                            else{
                                inputYN = false;
                                System.out.println("Masukan tidak valid");
                            }
                        }
                        break;

                    case "KMG":
                    //Masak
                    System.out.print("Apakah anda ingin melakukan aksi masak ? (y/n)");
                    Scanner input5 = new Scanner(System.in);
                    String jawaban5 = input5.nextLine();
                    while(!(jawaban5.equalsIgnoreCase("y")) || !(jawaban5.equalsIgnoreCase("n"))){
                        if (jawaban5.equalsIgnoreCase("y")){
                            // masak();
                        } else if(jawaban5.equalsIgnoreCase("n")) {
                            System.out.println("Anda tidak ingin melakukan aksi masak");
                        }
                        else{
                            System.out.println("Masukan tidak valid");
                        }
                    }
                        break;

                    case "KML":
                        //Masak
                        System.out.print("Apakah anda ingin melakukan aksi masak ? (y/n)");
                        Scanner input6 = new Scanner(System.in);
                        String jawaban6 = input6.nextLine();
                        while(!(jawaban6.equalsIgnoreCase("y")) || !(jawaban6.equalsIgnoreCase("n"))){
                            if (jawaban6.equalsIgnoreCase("y")){
                                // masak();
                            } else if(jawaban6.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi masak");
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                            }
                        }
                        break;

                    case "MDK":
                        //Makan
                        System.out.print("Apakah anda ingin melakukan aksi makan ? (y/n)");
                        Scanner input7 = new Scanner(System.in);   
                        String jawaban7 = input7.nextLine();
                        while(!(jawaban7.equalsIgnoreCase("y")) || !(jawaban7.equalsIgnoreCase("n"))){
                            if (jawaban7.equalsIgnoreCase("y")){
                                // this.makan();
                            } else if(jawaban7.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi makan");
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                            }
                        }
                        break;

                    case "JAM":
                        //Melihat Waktu
                        System.out.print("Apakah anda ingin melakukan aksi melihat waktu ? (y/n)");
                        Scanner input8 = new Scanner(System.in);
                        String jawaban8 = input8.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban8.equalsIgnoreCase("y")){
                                lihatWaktu();
                                inputYN = true;
                            } else if(jawaban8.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi melihat waktu");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                            }
                        }
                        break;
                }
            }
            else{
                System.out.println("Object not found at (" + x + "," + y + ")");
            }
        }
    }
}