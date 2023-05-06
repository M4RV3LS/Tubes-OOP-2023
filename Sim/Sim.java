package Sim;

import java.util.*;
import Inventory.*;
import Map.*;
import Fitur.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sim extends AksiUtama implements AksiTambahan{
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
        this.uang = 2000;
        this.kekenyangan = 100;
        this.mood = 80;
        this.kesehatan = 80;
        this.inventoryBahanMakanan = new Inventory<>();
        this.inventoryFurniture = new Inventory<>();
        //Menambah Objek yang wajib ada saat pertama kali objek Sim di instantiasi
        for (Furniture furniture : Furniture.values()) {
            // if (!(furniture.getName().equalsIgnoreCase("Komputer")) && !(furniture.getName().equalsIgnoreCase("Bola Kristal")) && !(furniture.getName().equalsIgnoreCase("Kitab Suci")) && !(furniture.getName().equalsIgnoreCase("Sapu")) && !(furniture.getName().equalsIgnoreCase("Sofa")) && !(furniture.getName().equalsIgnoreCase("Kotak Obat")) && !(furniture.getName().equalsIgnoreCase("Microphone"))){
            //     inventoryFurniture.tambahStock(furniture,1);
            // } 
            inventoryFurniture.tambahStock(furniture,1);
            
        }
        this.inventoryMasakan = new Inventory<>();
        //Buat Test Case
        // for (Masakan masakan : Masakan.values()) {
        //     if (masakan.getNama().equalsIgnoreCase("Nasi Ayam")){
        //         inventoryMasakan.tambahStock(masakan,1);
        //     } 
        // }
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

    //membuat objek Job berdasarkan masukan String pengguna
    public Job createJob(String jobName) {
        if (jobName.equalsIgnoreCase("Badut Sulap")) {
            return Job.BADUT_SULAP;
        } else if (jobName.equalsIgnoreCase("Koki")) {
            return Job.KOKI;
        } else if (jobName.equalsIgnoreCase("Polisi")) {
            return Job.POLISI;
        } else if (jobName.equalsIgnoreCase("Programmer")) {
            return Job.PROGRAMMER;
        } else if (jobName.equalsIgnoreCase("Dokter")) {
            return Job.DOKTER;
        } else {
            throw new IllegalArgumentException("Job name not recognized.");
        }
    }

    //print all job list 
    public void printAllJobs() {
        System.out.println("+------------------------------------+");
        System.out.printf("| %1$-15s | %2$15s |\n", "Job Name", "Daily Salary");
        System.out.println("+------------------------------------+");
        for (Job job : Job.values()) {
            System.out.printf("| %1$-15s | %2$15d |\n", job.getName(), job.getDailySalary());
        }
        System.out.println("+------------------------------------+");
    }
    
    //validasi masukan input user apakah string job yang dimasukkan ada di dalam dafta job 
    public boolean validateJob(String jobName) {
        for (Job job : Job.values()) {
            if (job.getName().equalsIgnoreCase(jobName)) {
                return true;
            }
        }
        return false;
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

    //membuat objek furniture berdasarkan input string dari user
public Furniture createFurniture(String input) {
    if (input.equalsIgnoreCase("Kasur Single")) {
        return Furniture.KASUR_SINGLE;
    } else if (input.equalsIgnoreCase("Kasur Queen Size")) {
        return Furniture.KASUR_QUEEN_SIZE;
    } else if (input.equalsIgnoreCase("Kasur King Size")) {
        return Furniture.KASUR_KING_SIZE;
    } else if (input.equalsIgnoreCase("Toilet")) {
        return Furniture.TOILET;
    } else if (input.equalsIgnoreCase("Kompor Gas")) {
        return Furniture.KOMPOR_GAS;
    } else if (input.equalsIgnoreCase("Kompor Listrik")) {
        return Furniture.KOMPOR_LISTRIK;
    } else if (input.equalsIgnoreCase("Meja dan Kursi")) {
        return Furniture.MEJA_KURSI;
    } else if (input.equalsIgnoreCase("Jam")) {
        return Furniture.JAM;
    } else if (input.equalsIgnoreCase("Komputer")) {
        return Furniture.KOMPUTER;
    } else if (input.equalsIgnoreCase("Bola Kristal")) {
        return Furniture.BOLA_KRISTAL;
    } else if (input.equalsIgnoreCase("Kotak Obat")) {
        return Furniture.KOTAK_OBAT;
    } else if (input.equalsIgnoreCase("Microphone")) {
        return Furniture.MICROPHONE;
    } else if (input.equalsIgnoreCase("Kitab Suci")) {
        return Furniture.KITAB_SUCI;
    } else if (input.equalsIgnoreCase("Sapu")) {
        return Furniture.SAPU;
    } else if (input.equalsIgnoreCase("Sofa")) {
        return Furniture.SOFA;
    } else {
        return null;
    }
}

//Mengecek Inputan user dalam 
public boolean checkInputFurniture(String input) {
    if (input.equals("KASUR_SINGLE")) {
    return true;
    } else if (input.equals("KASUR_QUEEN_SIZE")) {
    return true;
    } else if (input.equals("KASUR_KING_SIZE")) {
    return true;
    } else if (input.equals("TOILET")) {
    return true;
    } else if (input.equals("KOMPOR_GAS")) {
    return true;
    } else if (input.equals("KOMPOR_LISTRIK")) {
    return true;
    } else if (input.equals("MEJA_KURSI")) {
    return true;
    } else if (input.equals("JAM")) {
    return true;
    } else if (input.equals("KOMPUTER")) {
    return true;
    } else if (input.equals("BOLA_KRISTAL")) {
    return true;
    } else if (input.equals("KOTAK_OBAT")) {
    return true;
    } else if (input.equals("MICROPHONE")) {
    return true;
    } else if (input.equals("KITAB_SUCI")) {
    return true;
    } else if (input.equals("SAPU")) {
    return true;
    } else if (input.equals("SOFA")) {
    return true;
    } else {
    return false;
    }
    }


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
    //Membuat Iterator untuk melakukan loop pada list daftarUpgradeRumah
    Iterator<UpgradeHouse> iterator = world.getDaftarUpgradeHouse().iterator();
    
    while (iterator.hasNext()) {
        UpgradeHouse upgradeHouse1 = iterator.next();
        if (upgradeHouse1.getSim().getNamaLengkap().equalsIgnoreCase(this.getNamaLengkap())) {
            //Menghapus upgradeHouse milik Sim ini pada list daftarUpgradeRumah menggunakan iterator.remove()
            iterator.remove();
        }
    }
    
    if(inputUpgradeHouse != null){
        this.upgradeHouse = inputUpgradeHouse;
        //menambahkan upgradeHouse kedalam list daftarUpgradeRumah
        world.getDaftarUpgradeHouse().add(inputUpgradeHouse);
    }
}

    //getter isInHouse
    public Boolean getIsInHouse() {
        return isInHouse;
    }

    //getter checkSsInHouse 
    public void checkIsInHouse(){
        if(getHouse() != getOwnHouse())
        {
             setInHouse(false);
          
        }
        else
        {
             setInHouse(true);
             
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
    public void setIsGantiKerja(boolean isGantiKerja){
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
        if(this.getKekenyangan() <= 0 || this.getKesehatan() <= 0 || this.getMood() <= 0){

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
        System.out.println("===========STATS===========");
        System.out.println("Nama Lengkap: " + namaLengkap);
        System.out.println("Pekerjaan: " + pekerjaan);
        System.out.println("Uang: " + uang);
        System.out.println("Kekenyangan: " + kekenyangan);
        System.out.println("Mood: " + mood);
        System.out.println("Kesehatan: " + kesehatan);
        System.out.println("Status: " + status);
        System.out.println("Rumah: " + house.getHouseName());
        if(world.getHouseOwner(getHouse()) != null){
            System.out.println("Owner Rumah: " + world.getHouseOwner(getHouse()).getNamaLengkap());
        }
        System.out.println("Ruangan: " + room.getRoomName());
        if(world.getWaktuTidakTidur().containsKey(this)){
            System.out.println("Waktu tidak tidur: " + world.getWaktuTidakTidur(getNamaLengkap()));
        }
        if(world.getWaktuTidakBuangAir().containsKey(this)){
            System.out.println("Waktu tidak buang air: " + world.getWaktuTidakBuangAir(getNamaLengkap()));
        } 
    }

    //print sim stat
    public void printStat()
    {
        System.out.println("=========SIM SELESAI " + this.getStatus().toUpperCase() + "=========");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
        System.out.println("Uang anda sekarang: " + getUang());
        if(world.getWaktuTidakTidur().containsKey(this)){
            System.out.println("Waktu tidak tidur: " + world.getWaktuTidakTidur(getNamaLengkap()));
        }
        if(world.getWaktuTidakBuangAir().containsKey(this)){
            System.out.println("Waktu tidak buang air: " + world.getWaktuTidakBuangAir(getNamaLengkap()));
        }     
        
    }

    // // Ganti Hari
    // public void gantiHari() {
    //     waktuTidakTidur = 0;
    //     waktuTidakBuangAir = 0;
    // }

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
            System.out.println("Waktu tersisa untuk upgrade rumah: " + waktuUpgrade + " detik");
        }
        if (!world.getDeliveryItemsFurniture().isEmpty()) {
            System.out.println("Delivery Furniture:");
            Iterator<DeliveryItem<Furniture>> iterator = world.getDeliveryItemsFurniture().iterator();
            while (iterator.hasNext()) {
                DeliveryItem<Furniture> deliveryItem = iterator.next();
                int waktuDelivery = deliveryItem.getWaktu();
                String namaObjek = deliveryItem.getNamaObjek();
                System.out.println("- " + namaObjek + " (" + waktuDelivery + " detik)");
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

    //Tabel Furniture dengan aksinya
    public void printFurnitureAksi(){
        System.out.printf("-----------------------------------------------%n");
        System.out.printf("|       AKSI DAN FURNITUR SIMPLICITY          |%n");

        System.out.printf("-----------------------------------------------%n");
        System.out.printf("| %-15s | %-25s |%n", "AKSI", "FURNITURE");
        System.out.printf("-----------------------------------------------%n");

        System.out.printf("| %-15s | %-25s |%n", "Tidur", "Kasur Single (KSS)");
        System.out.printf("| %-15s | %-25s |%n", "Tidur", "Kasur Queen Size (KQS)");
        System.out.printf("| %-15s | %-25s |%n", "Tidur", "Kasur King Size (KSS)");
        System.out.printf("| %-15s | %-25s |%n", "Buang Air", "Toilet (TLT)");
        System.out.printf("| %-15s | %-25s |%n", "Memasak", "Kompor Gas (KMG)");
        System.out.printf("| %-15s | %-25s |%n", "Memasak", "Kompor Listrik (KML)");
        System.out.printf("| %-15s | %-25s |%n", "Makan", "Meja dan Kursi (MDK)");
        System.out.printf("| %-15s | %-25s |%n", "Melihat Waktu", "Jam (JAM)");

        System.out.printf("| %-15s | %-25s |%n", "Main Game", "Komputer (KOM)");
        System.out.printf("| %-15s | %-25s |%n", "Santet", "Bola Kristal (KRS)");
        System.out.printf("| %-15s | %-25s |%n", "Berobat", "Kotak Obat (MED)");
        System.out.printf("| %-15s | %-25s |%n", "Karaoke", "Microphone (MIC)");
        System.out.printf("| %-15s | %-25s |%n", "Puasa", "Kitab Suci (KTB)");
        System.out.printf("| %-15s | %-25s |%n", "Bersih-Bersih", "Sapu (SPU)");
        System.out.printf("| %-15s | %-25s |%n", "Melawak", "Sofa (SOF)");
        

        System.out.printf("-----------------------------------------------%n");
    }

    // Aksi Kerja
    @Override
    public void kerja(int lamaKerja)
    {   
        if(!(this.getIsGantiKerja()) && lamaKerja%5 == 0)
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
                        
                        // Thread.sleep(lamaKerja*1000);
                        Thread.sleep(1*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally{
                        int kenyangTurun = getKekenyangan()+(lamaKerja/30)*(-10);
                        setKekenyangan(kenyangTurun);
                        int moodTurun = getMood() + (lamaKerja/30)*(-10);
                        setMood(moodTurun);
            
                        totalWaktuKerja += lamaKerja;
                        
                        if(totalWaktuKerja >= 10)
                        {
                            if(pekerjaan.getName().equalsIgnoreCase("Badut Sulap"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/10) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Koki"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/10) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Polisi"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/10) *getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Programmer"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/10) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Dokter"))
                            {
                                int tambahGaji = getUang() + (totalWaktuKerja/10) *getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            //nyimpen sisa waktu kerja
                            totalWaktuKerja = totalWaktuKerja - ((totalWaktuKerja/10)*10);
                        }

                        // print stats
                        // System.out.println("=========SIM SELESAI BEKERJA=========");
                        setStatus("Bekerja");
                        
                        
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaKerja);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }

                        printStat();
                        System.out.println("Anda bekerja selama " + lamaKerja + " detik");
                        

                        // Tambahin Waktu ke World
                        // if (isDead()){
                        //     System.out.println("SIM telah meninggal");
                        // } 
                    }
                        // System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
                        // System.out.println("Mood anda sekarang: " + getMood());
                        // System.out.println("Uang anda sekarang: " + getUang());
                        // System.out.println(" ");
                }
            });
            thread.run();
        }
        
        else if(this.getIsGantiKerja())
        {
            System.out.println(this.getNamaLengkap() + " telah mengganti kerja di hari ini , silahkan coba di hari berikutnya");
        }
        else if((lamaKerja%120) != 0)
        {
            System.out.println("Waktu bekerja harus kelipatan 120 detik");
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
                        // Thread.sleep(lamaOlahraga*1000);
                        Thread.sleep(1*1000);
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
                        // System.out.println("=========SIM SELESAI OLAHRAGA=========");
                        setStatus("Olahraga");
                        
                        
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaOlahraga);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }

                        printStat();
                        System.out.println("Anda bekerja selama " + lamaOlahraga + " detik");
                        

                        // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
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
            int sisaWaktuTidurdiHariSelanjutnya = (-1) * (world.getWaktuSim() - lamaTidur);
            world.checkIsGantiHari(lamaTidur);
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
                        // Thread.sleep(lamaTidur*1000);
                        Thread.sleep(1*1000);
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
                        // System.out.println("=========SIM SEDANG TIDUR=========");
                        // System.out.println("Anda tidur selama " + lamaTidur + " detik");
                        // System.out.println("Mood anda sekarang: " + getMood());
                        // System.out.println("Kesehatan anda sekarang: " + getKesehatan());
                        setStatus("Tidur");
                        // Tambahin Waktu ke World
                        
                        
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaTidur);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                            if(sisaWaktuTidurdiHariSelanjutnya >= 3){
                                world.removeWaktuTidakTidur(world.getSimByName(getNamaLengkap()));
                            }
                            else if(sisaWaktuTidurdiHariSelanjutnya < 3){
                                world.removeWaktuTidakTidur(world.getSimByName(getNamaLengkap()));
                                world.addWaktuTidakTidur(world.getSimByName(getNamaLengkap()), sisaWaktuTidurdiHariSelanjutnya + 600);
                            }
                        }
                        else if(!(world.getIsGantiHari())){
                            world.removeWaktuTidakTidur(world.getSimByName(getNamaLengkap()));
                        }
                    }
                    
                    if(sisaWaktuTidur >= 240){
                        int moodNaik = getMood() + (sisaWaktuTidur)/240*30;
                        setMood(moodNaik);
                        int kesehatanNaik = getKesehatan()+ (sisaWaktuTidur)/240*20;
                        setKesehatan(kesehatanNaik);
                        sisaWaktuTidur = (sisaWaktuTidur - ((sisaWaktuTidur/240)*240));
                    }

                    printStat();
                    if (isDead()){
                            System.out.println("SIM telah meninggal");
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
        
            int moodTurun = getMood() - 5;
            setMood(moodTurun);
            int kesehatanTurun = getKesehatan() - 5;
            setKesehatan(kesehatanTurun);
        

        // print stats
        // System.out.println("=========SIM BUTUH TIDUR=========");
        // System.out.println("Anda tidak tidur selama " + world.getWaktuTidakTidur(getNamaLengkap()) + " detik");
        // System.out.println("Mood anda sekarang: " + getMood());
        // System.out.println("Kesehatan anda sekarang: " + getKesehatan());
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
                    // Thread.sleep((long)waktuMasak);
                    Thread.sleep(1*1000);
        
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally
                {
                    double waktuMasak = 1.5 * diMasak.getKekenyangan();

                    // Masak berhasil
                    inventoryMasakan.tambahStock(diMasak, 1);
                    System.out.println("Masakan " + diMasak.getNama() + " berhasil dimasak");
        
                    // Check aksi pasif
                    setStatus("Makan");
                    world.checkWaktuSetelahAksi(getNamaLengkap(), (int)waktuMasak);
                    printStat();
                }
            }
            else
            {
                System.out.println("Gagal memasak");
            }
        
    }

    public static void showMasakan() {
        System.out.println("Daftar Masakan yang bisa dimakan:");
        for (Masakan masakan : Masakan.values()) {
            System.out.println("Nama: " + masakan.getNama() +
                               ", Kekenyangan: " + masakan.getKekenyangan());
            System.out.println();
        }
    }

    public void makan()
    {
        showMasakan();
        System.out.println("");
        System.out.println("Berikut ini adalah daftar makanan yang ada di inventory " + getNamaLengkap());
        this.inventoryMasakan.printInventory();
        Scanner input = new Scanner(System.in);
        
        System.out.print("Masukkan nama masakan yang ingin dimasak: ");
        String namaMasakan = input.nextLine();

        Boolean inputValid = false;
        Boolean stockAda = false;
        Masakan diMakan = null;
        
        for(Masakan masakan: Masakan.values()){
            if(namaMasakan.equalsIgnoreCase(masakan.getNama())){
                inputValid = true;
                diMakan = masakan;
                break;
            }
        }
        
        if(inputValid){
            HashMap<Masakan, Integer> stockMasakan = getInventoryMasakan().getStock();
            if(diMakan != null){
                if(stockMasakan.containsKey(diMakan)){
                    int jumlah = stockMasakan.get(diMakan);
                    if(jumlah > 0){
                        stockAda = true;
                    }
                }
            } 
        }
        else{
            System.out.println("Masakan tidak ada");
        }

        if(stockAda)
        {
            int waktuSisaDiHariSelanjutnya = (-1) * (world.getWaktuSim() - 30);
            world.checkIsGantiHari(30);
            Masakan finalDiMakan = diMakan;
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
                        // Thread.sleep(30*1000);
                        Thread.sleep(1*1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    finally
                    {
                        int kenyangNaik = getKekenyangan() + finalDiMakan.getKekenyangan();
                        setKekenyangan(kenyangNaik);

                        setStatus("Makan");
                        
                        // Remove masakan from inventory
                        inventoryMasakan.kurangiStock(finalDiMakan, 1);
                        
                        
                        // world.addWaktuTidakBuangAir(getNamaLengkap(), 240);
                        world.checkWaktuSetelahAksi(getNamaLengkap(), 30);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }

                        //aksi efek tidak buang air
                        if(world.getIsGantiHari()){
                            world.addWaktuTidakBuangAir(world.getSimByName(getNamaLengkap()) , 240 + waktuSisaDiHariSelanjutnya );
                        }
                        else if(!(world.getIsGantiHari()) && !(world.getWaktuTidakBuangAir().containsKey(world.getSimByName(getNamaLengkap())))){
                            world.addWaktuTidakBuangAir(world.getSimByName(getNamaLengkap()) , 240);
                        }

                        printStat();
                        // ngecek mati atau ngga
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        } 
                            
                        
                        
            
                    }
               }
            });
            thread.run();
        }
        
        else
        {
            System.out.println("Sim Gagal Makan Karena Stock Tidak Tersedia Di Inventory");
        }
    }

    public void Berkunjung()
    {   
        System.out.println("Berikut ini adalah daftar sim yang memiliki rumah");
        for ( Sim daftarSim : world.getSimList()) {
            System.out.println("- " + daftarSim.getNamaLengkap());
        }
        Sim simYangDikunjungi = null;
        Boolean inputValid = false;
        Boolean adaTetangga = false;
        if(world.getSimList().size() > 1){
            while(!(inputValid)){
                Scanner input = new Scanner(System.in);
                System.out.print("Masukkan nama sim yang rumahnya ingin dikunjungi: ");
                String namaSim = input.nextLine();
                
                    
                        if(world.findSim(namaSim)  ){
                            if((getHouse() != world.getSimByName(namaSim).getOwnHouse())){
                                inputValid = true;
                                simYangDikunjungi = world.getSimByName(namaSim);
                                adaTetangga = true;
                            }
                            // else if((getHouse().equals(getOwnHouse())) && !(namaSim.equalsIgnoreCase(getNamaLengkap()))){
                            //     inputValid = true;
                            //     simYangDikunjungi = world.getSimByName(namaSim);
                            //     adaTetangga = true;
                            // }
                            else if((getHouse().equals(world.getSimByName(namaSim).getOwnHouse()))){
                                System.out.println("Tidak dapat berkunjung ke rumah sendiri");
                            }
                        }
                        else {
                            System.out.println("Sim tidak ditemukan");
                        }
                    }
            }
        
        else{
            System.out.println("Tidak ada tetangga");
        }
           
        
        // Cek apakah house terdapat pada private HashMap<House, int[]> houseLocations = new HashMap<>();
        if (!(adaTetangga)) 
        {
            System.out.println("Silahkan membuat tetangga terlebih dahulu");
        }

        else{
            //Menerima input dari user untuk menentukan ruangan mana yang dipilih oleh user dengan cara menampilkan list
            //Rungan yang tersedia pada rumah simYangDikunjungi pada ArrayList<Room> rooms;
            //validasi apakah ruangan yang diinput oleh user terdapat di rumah simYangDikunjungi 
            Boolean inputValid2 = false;
            Room roomYangDikunjungi = null;
            while(!(inputValid2)){
                Scanner input = new Scanner(System.in);
                System.out.println("Berikut ini adalah daftar ruangan yang terdapat pada rumah " + simYangDikunjungi.getNamaLengkap());
                for (Room room : simYangDikunjungi.getOwnHouse().getRooms()) {
                    System.out.println("- " + room.getRoomName());
                }
                System.out.println("");
                System.out.print("Masukkan nama ruangan yang ingin dikunjungi: ");
                String namaRuangan = input.nextLine();
                for (Room room : simYangDikunjungi.getOwnHouse().getRooms()) {
                    if(room.getRoomName().equalsIgnoreCase(namaRuangan)){
                        inputValid2 = true;
                        roomYangDikunjungi = room;
                        break;
                    }
                    else{
                        System.out.println("Ruangan tidak ditemukan");
                    }
                }
            }
            House house = this.getHouse();
            int[] houseLocation = world.getHouseLocation(simYangDikunjungi.getHouse());
            int[] currentLocation = world.getHouseLocation(house);
            int selisihX = Math.abs(houseLocation[0]-currentLocation[0]);
            int selisihY = Math.abs(houseLocation[1]-currentLocation[1]);
            
            // double waktuPerjalanan = Math.sqrt(Math.pow(selisihX,2) + Math.pow(selisihY,2) );
            // int waktuPerjalananSim = (int) waktuPerjalanan;
            int waktuPerjalananSim = 5;
            world.checkIsGantiHari(waktuPerjalananSim);
            Sim simYangSedangDikunjungi = simYangDikunjungi;
            Room roomSekarang = roomYangDikunjungi;
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try {
                        System.out.println("==========SIM SEDANG BERKUNJUNG==========");
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
                        // Thread.sleep(waktuPerjalananSim*1000);
                        Thread.sleep(1*1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        int moodNaik = getMood() + waktuPerjalananSim/30*10;
                        setMood(moodNaik);
                        int kenyangTurun = getKekenyangan() + waktuPerjalananSim/30*(-10);
                        setKekenyangan(kenyangTurun);

                        setHouse(simYangSedangDikunjungi.getOwnHouse());
                        setRoom(roomSekarang);
                        System.out.println(getHouse().getHouseName());
                        System.out.println(getNamaLengkap() + " Berkunjung ke rumah " + simYangSedangDikunjungi.getNamaLengkap());
                        setStatus("Berkunjung");
                         
                        checkIsInHouse();
                        
                        world.checkWaktuSetelahAksi(getNamaLengkap(), waktuPerjalananSim);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }

                         // Tambahin Waktu ke World
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
                        }

                        // Print hasil
                        printStat();
                        System.out.println("Waktu perjalanan: " + waktuPerjalananSim + " detik");
                        System.out.println("posisi anda sekarang: X " + houseLocation[0] + ", Y " + houseLocation[1]);
                        

                       
                    }
               }
            });
            thread.run();
        }
    }

    //Aksi Buang Air
    public void buangAir()
    {   
        world.checkIsGantiHari(10);
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
                    // Thread.sleep(10*1000);
                    Thread.sleep(1*1000);

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
                    setStatus("Buang Air");
                    world.checkWaktuSetelahAksi(getNamaLengkap(), 10);
                    world.removeWaktuTidakBuangAir(world.getSimByName(getNamaLengkap()));

                    //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                    if (world.getIsGantiHari()){
                        System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                    }
                    // Tambahin Waktu ke World
                    if (isDead()){
                        System.out.println("SIM telah meninggal");
                    } 
                    
                    printStat();

                    

                        
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

            int moodTurun = getMood() - 5;
            setMood(moodTurun);
            int kenyangTurun = getKekenyangan() - 5;
            setKekenyangan(kenyangTurun);

            // print stats
            // System.out.println("Anda tidak buang air selama " + world.getWaktuTidakBuangAir(getNamaLengkap()) + " detik");
            // System.out.println("Mood anda sekarang: " + getMood());
            // System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
        
    }

     // AKSI TAMBAHAN
     public void mainGame(int lamaMain)
     {
         if (lamaMain > 0){
            world.checkIsGantiHari(lamaMain);
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG MAIN GAME==========");
                         // 1. main game
                        System.out.println(" ██████   █████  ███    ███ ███████      ██████  ███    ██ ██ ");
                        System.out.println("██       ██   ██ ████  ████ ██          ██    ██ ████   ██ ██ ");
                        System.out.println("██   ███ ███████ ██ ████ ██ █████       ██    ██ ██ ██  ██ ██ ");
                        System.out.println("██    ██ ██   ██ ██  ██  ██ ██          ██    ██ ██  ██ ██    ");
                        System.out.println(" ██████  ██   ██ ██      ██ ███████      ██████  ██   ████ ██ ");
                        System.out.println("");
                        //  Thread.sleep(lamaMain*1000);
                        Thread.sleep(1*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaMain/10);
                         setMood(moodNaik);
                         int kesehatanTurun = getKesehatan() - (lamaMain/30);
                         setKesehatan(kesehatanTurun);
                         int kekenyanganTurun = getKekenyangan() - (lamaMain/20);
                         setKekenyangan(kekenyanganTurun);
 

                        //  else if(!isMakan)
                        //  {
                        //     world.
                        //  }
                        setStatus("Main Game");
                        world.checkWaktuSetelahAksi(getNamaLengkap(), lamaMain);

                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }

                        printStat();
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
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
            world.checkIsGantiHari(waktuDibutuhkan);
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG MENYANTET==========");
                                // 2. santet
                        System.out.println("██████   █████   █████  ██     ██ ██████  ██████  ");
                        System.out.println("██   ██ ██   ██ ██   ██ ██     ██ ██   ██ ██   ██ ");
                        System.out.println("██████  ███████ ███████ ██  █  ██ ██████  ██████  ");
                        System.out.println("██   ██ ██   ██ ██   ██ ██ ███ ██ ██   ██ ██   ██ ");
                        System.out.println("██   ██ ██   ██ ██   ██  ███ ███  ██   ██ ██   ██ ");
                        System.out.println("");
                        //  Thread.sleep(waktuDibutuhkan*1000);
                        Thread.sleep(1*1000);
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
                         
                         
                        setStatus("Santet");     
                        world.checkWaktuSetelahAksi(getNamaLengkap(), waktuDibutuhkan);
                        //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                        if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }

                        System.out.println("Informasi Sim ( " + getNamaLengkap() + " ) yang menyantet : " );
                         printStat();
                         System.out.println("Informasi Sim ( " + simLain.getNamaLengkap() + " ) yang disantet : " );
                         simLain.printStat();
                         if (isDead()){
                             System.out.println("SIM telah meninggal");
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
            world.checkIsGantiHari(lamaBerobat);
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG BEROBAT==========");
                                 // 3. berobat
                            System.out.println("██   ██ ███████  █████  ██      ██ ███    ██  ██████           ");
                            System.out.println("██   ██ ██      ██   ██ ██      ██ ████   ██ ██                ");
                            System.out.println("███████ █████   ███████ ██      ██ ██ ██  ██ ██   ███          ");
                            System.out.println("██   ██ ██      ██   ██ ██      ██ ██  ██ ██ ██    ██          ");
                            System.out.println("██   ██ ███████ ██   ██ ███████ ██ ██   ████  ██████  ██ ██ ██");
                            System.out.println("");
                        //  Thread.sleep(lamaBerobat*1000);
                        Thread.sleep(1*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaBerobat/30);
                         setMood(moodNaik);
                         int kesehatanNaik = getKesehatan() + (lamaBerobat/10)*2;
                         setKesehatan(kesehatanNaik);
                         
                        
                         setUang(uangTurun);
                         setStatus("Berobat");
                         world.checkWaktuSetelahAksi(getStatus(), lamaBerobat);
                         
                         //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                         if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                         }

                         printStat();
                         if (isDead()) {
                             System.out.println("SIM telah meninggal");
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
            world.checkIsGantiHari(lamaKaraoke);
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                         System.out.println("==========SIM SEDANG KARAOKE==========");
                        // 4. karaoke
                        System.out.println("██   ██  █████  ██████   █████   ██████  ██   ██ ███████ ██");
                        System.out.println("██  ██  ██   ██ ██   ██ ██   ██ ██    ██ ██  ██  ██      ██ ");
                        System.out.println("█████   ███████ ██████  ███████ ██    ██ █████   █████   ██ ");
                        System.out.println("██  ██  ██   ██ ██   ██ ██   ██ ██    ██ ██  ██  ██         ");
                        System.out.println("██   ██ ██   ██ ██   ██ ██   ██  ██████  ██   ██ ███████ ██ ");
                        System.out.println("");
                        //  Thread.sleep(lamaKaraoke*1000);
                        Thread.sleep(1*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaKaraoke/10);
                         setMood(moodNaik);
                         int kesehatanTurun = getKesehatan() - (lamaKaraoke/30);
                         setKesehatan(kesehatanTurun);
                         int kekenyanganTurun = getKekenyangan() - (lamaKaraoke/20);
                         setKekenyangan(kekenyanganTurun);
                         
                        
                         setUang(uangTurun);
                         setStatus("Karaoke");
                         world.checkWaktuSetelahAksi(getNamaLengkap(), lamaKaraoke);
                         
                         //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                         if (world.getIsGantiHari()){
                            System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                        }
                        printStat();
                        if (isDead()){
                            System.out.println("SIM telah meninggal");
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
         int waktuDibutuhkan = 10;
         world.checkIsGantiHari(waktuDibutuhkan);
         thread = new Thread(new Runnable() {
             public void run(){
                 try {
                     System.out.println("==========SIM SEDANG PUASA==========");
                             // 5. puasa
                    System.out.println("██████  ██    ██  █████  ███████  █████           ");
                    System.out.println("██   ██ ██    ██ ██   ██ ██      ██   ██          ");
                    System.out.println("██████  ██    ██ ███████ ███████ ███████          ");
                    System.out.println("██      ██    ██ ██   ██      ██ ██   ██          ");
                    System.out.println("██       ██████  ██   ██ ███████ ██   ██ ██ ██ ██ ");
                    System.out.println("");
                    //  Thread.sleep(waktuDibutuhkan*1000);
                    Thread.sleep(1*1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } finally {
                     int kesehatanNaik = getKesehatan() + 10;
                     setKesehatan(kesehatanNaik);
                     int kekenyanganTurun = getKekenyangan() - 50;
                     setKekenyangan(kekenyanganTurun);
                     
                     
                     setStatus("Puasa");
                     world.checkWaktuSetelahAksi(getNamaLengkap(), waktuDibutuhkan);
                     
                     //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                    if (world.getIsGantiHari()){
                        System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                    }
                    printStat();
                     if (isDead()) {
                         System.out.println("SIM telah meninggal");
                     } 
                 }
             }
         });
         thread.run();
     }
 
     public void bersihBersih(int lamaBersihBersih)
     {
         if (lamaBersihBersih > 0){
             world.checkIsGantiHari(lamaBersihBersih);
             thread = new Thread(new Runnable() {
                 public void run(){
                     try {
                        //  System.out.println("==========SIM SEDANG BERSIH-BERSIH==========");
                                // 6. bersih-bersih
                        System.out.println(" ██████ ██      ███████  █████  ███    ██ ██ ███    ██  ██████           ");
                        System.out.println("██      ██      ██      ██   ██ ████   ██ ██ ████   ██ ██                ");
                        System.out.println("██      ██      █████   ███████ ██ ██  ██ ██ ██ ██  ██ ██   ███          ");
                        System.out.println("██      ██      ██      ██   ██ ██  ██ ██ ██ ██  ██ ██ ██    ██          ");
                        System.out.println(" ██████ ███████ ███████ ██   ██ ██   ████ ██ ██   ████  ██████  ██ ██ ██ ");
                        System.out.println("");
                        //  Thread.sleep(lamaBersihBersih*1000);
                        Thread.sleep(1*1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         int moodNaik = getMood() + (lamaBersihBersih/20);
                         setMood(moodNaik);
                         int kesehatanNaik = getKesehatan() + (lamaBersihBersih/5);
                         setKesehatan(kesehatanNaik);
                         int kekenyanganTurun = getKekenyangan() - (lamaBersihBersih/15);
                         setKekenyangan(kekenyanganTurun);
                         
                        
                         setStatus("Bersih-Bersih");
                         world.checkWaktuSetelahAksi(getNamaLengkap(), lamaBersihBersih);
                            
                         //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                         if (world.getIsGantiHari()){
                             System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                         }   
                         printStat();
                         if (isDead()) {
                             System.out.println("SIM telah meninggal");
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
         int waktuDibutuhkan = 10;
         world.checkIsGantiHari(waktuDibutuhkan);
         thread = new Thread(new Runnable() {
             public void run(){
                 try {
                     System.out.println("==========SIM SEDANG MELAWAK==========");
                             // 7. melawak
                    System.out.println("██   ██  █████  ██   ██  █████  ██   ██  █████   ██ ");
                    System.out.println("██   ██ ██   ██ ██   ██ ██   ██ ██   ██ ██   ██  ██ ");
                    System.out.println("███████ ███████ ███████ ███████ ███████ ███████  ██ ");
                    System.out.println("██   ██ ██   ██ ██   ██ ██   ██ ██   ██ ██   ██     ");
                    System.out.println("██   ██ ██   ██ ██   ██ ██   ██ ██   ██ ██   ██  ██ ");
                    System.out.println("");
                    //  Thread.sleep(waktuDibutuhkan*1000);
                    Thread.sleep(1*1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } finally {
                     int moodNaik = getMood() + 10;
                     setMood(moodNaik);
                     int kesehatanNaik = getKesehatan() + 5;
                     setKesehatan(kesehatanNaik);
                     int kekenyanganTurun = getKekenyangan() - 3;
                     setKekenyangan(kekenyanganTurun);
 
                     
                     setStatus("Melawak");
                     world.checkWaktuSetelahAksi(getNamaLengkap(), waktuDibutuhkan);
                     
                     //melakukan print hari sudah berganti jika isGantiHari pada class world bernilai true
                     if (world.getIsGantiHari()){
                        System.out.println("Hari telah berganti , sekarang sudah hari ke-" + world.getHariDunia());
                     }

                     printStat();
                     if (isDead()){
                         System.out.println("SIM telah meninggal");
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
                    Boolean inputYN = false;
                    while(!(inputYN)){
                        if (jawaban.equalsIgnoreCase("y")){
                            int number = readInteger(input);
                            tidur(number);
                            inputYN = true;
                        } else if(jawaban.equalsIgnoreCase("n")) {
                            System.out.println("Anda tidak ingin melakukan aksi tidur");
                            inputYN = true;
                        }
                        else{
                            System.out.println("Masukan tidak valid");
                            inputYN = false;
                        }
                    }
                        break;

                    case "KQS":
                        //Tidur
                        System.out.print("Apakah anda ingin melakukan aksi tidur ? (y/n)");
                        Scanner input2 = new Scanner(System.in);
                        String jawaban2 = input2.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban2.equalsIgnoreCase("y")){
                                int number = readInteger(input2);
                                tidur(number);
                                inputYN = true;
                            } else if(jawaban2.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi tidur");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;
                    case "KKS":
                    //Tidur
                    System.out.print("Apakah anda ingin melakukan aksi tidur ? (y/n)");
                    Scanner input3 = new Scanner(System.in);
                    String jawaban3 = input3.nextLine();
                    inputYN = false;
                    while(!(inputYN)){
                        if (jawaban3.equalsIgnoreCase("y")){
                            int number = readInteger(input3);
                            tidur(number);
                            inputYN = true;
                        } else if(jawaban3.equalsIgnoreCase("n")) {
                            System.out.println("Anda tidak ingin melakukan aksi tidur");
                            inputYN = true;
                        }
                        else{
                            System.out.println("Masukan tidak valid");
                            inputYN = false;
                        }
                    }
                        break;
                        
                    case "TLT":
                        //Buang air
                        System.out.print("Apakah anda ingin melakukan aksi buang air ? (y/n)");
                        Scanner input4 = new Scanner(System.in);
                        String jawaban4 = input4.nextLine();
                        inputYN = false;
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
                    inputYN = false;
                    while(!(inputYN)){
                        if (jawaban5.equalsIgnoreCase("y")){
                            masak();
                            inputYN = true;
                        } else if(jawaban5.equalsIgnoreCase("n")) {
                            System.out.println("Anda tidak ingin melakukan aksi masak");
                            inputYN = true;
                        }
                        else{
                            System.out.println("Masukan tidak valid");
                            inputYN = false;
                        }
                    }
                        break;

                    case "KML":
                        //Masak
                        System.out.print("Apakah anda ingin melakukan aksi masak ? (y/n)");
                        Scanner input6 = new Scanner(System.in);
                        String jawaban6 = input6.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban6.equalsIgnoreCase("y")){
                                masak();
                                inputYN = true;
                            } else if(jawaban6.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi masak");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;

                    case "MDK":
                        //Makan
                        System.out.print("Apakah anda ingin melakukan aksi makan ? (y/n)");
                        Scanner input7 = new Scanner(System.in);   
                        String jawaban7 = input7.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban7.equalsIgnoreCase("y")){
                                makan();
                                inputYN = true;
                            } else if(jawaban7.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi makan");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = true;
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
                    
                    case "KOM":
                        //main game
                        System.out.print("Apakah anda ingin melakukan aksi main game ? (y/n)");
                        Scanner input9 = new Scanner(System.in);
                        String jawaban9 = input9.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban9.equalsIgnoreCase("y")){
                                int number = readInteger(input9);
                                mainGame(number);
                                inputYN = true;
                            } else if(jawaban9.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi main game");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                            break;
                    
                    case "KRS":
                        //santet
                        System.out.print("Apakah anda ingin melakukan aksi santet ? (y/n)");
                        Scanner input10 = new Scanner(System.in);
                        String jawaban10 = input10.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban10.equalsIgnoreCase("y")){
                                System.out.println("Berikut adalah SIM yang tersedia:");
                                for ( Sim daftarSim : world.getSimList()) {
                                    System.out.println("- " + daftarSim.getNamaLengkap());
                                }
                                System.out.println("");
                                System.out.print("Masukan nama sim yang ingin disantet");
                                String namaSim = input10.nextLine();
                                if(world.getSimList().isEmpty()){
                                    inputYN = true;
                                    System.out.println("Sim lain tidak tersedia");
                                }
                                else if (world.getSimByName(namaSim) != null && !(namaSim.equalsIgnoreCase(getNamaLengkap()))) {
                                    santet(world.getSimByName(namaSim));
                                    inputYN = true;
                                } else if (world.getSimByName(namaSim) == null){
                                    System.out.println("Nama sim tidak ditemukan");
                                    inputYN = true;
                                }
                                else if(namaSim.equalsIgnoreCase(getNamaLengkap())){
                                    System.out.println("Anda tidak bisa santet diri sendiri");
                                    inputYN = true;
                                }
                            } else if(jawaban10.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi santet");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;
                    
                    case "MED":
                        //berobat
                        System.out.print("Apakah anda ingin melakukan aksi berobat ? (y/n)");
                        Scanner input11 = new Scanner(System.in);
                        String jawaban11 = input11.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban11.equalsIgnoreCase("y")){
                                int number = readInteger(input11);
                                berobat(number);
                                inputYN = true;
                            } else if(jawaban11.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi berobat");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;
                    
                    case "MIC":
                        //karaoke
                        System.out.print("Apakah anda ingin melakukan aksi karaoke ? (y/n)");
                        Scanner input12 = new Scanner(System.in);
                        String jawaban12 = input12.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban12.equalsIgnoreCase("y")){
                                int number = readInteger(input12);
                                karaoke(number);
                                inputYN = true;
                            } else if(jawaban12.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi karaoke");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;

                    case "KTB":
                        //puasa
                        System.out.print("Apakah anda ingin melakukan aksi puasa ? (y/n)");
                        Scanner input13 = new Scanner(System.in);
                        String jawaban13 = input13.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban13.equalsIgnoreCase("y")){
                                puasa();
                                inputYN = true;
                            } else if(jawaban13.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi puasa");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;

                    case "SPU":
                        //bersih-bersih
                        System.out.print("Apakah anda ingin melakukan aksi bersih-bersih ? (y/n)");
                        Scanner input14 = new Scanner(System.in);
                        String jawaban14 = input14.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban14.equalsIgnoreCase("y")){
                                int number = readInteger(input14);
                                bersihBersih(number);
                                inputYN = true;
                            } else if(jawaban14.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi bersih-bersih");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
                            }
                        }
                        break;

                    case "SOF":
                        //melawak
                        System.out.print("Apakah anda ingin melakukan aksi melawak ? (y/n)");
                        Scanner input15 = new Scanner(System.in);
                        String jawaban15 = input15.nextLine();
                        inputYN = false;
                        while(!(inputYN)){
                            if (jawaban15.equalsIgnoreCase("y")){
                                melawak();
                                inputYN = true;
                            } else if(jawaban15.equalsIgnoreCase("n")) {
                                System.out.println("Anda tidak ingin melakukan aksi melawak");
                                inputYN = true;
                            }
                            else{
                                System.out.println("Masukan tidak valid");
                                inputYN = false;
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