import java.util.*;

public class Sim {
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
    
    //Indikasi Rumah sendiri
    private House ownHouse;

    // Indikasi Lokasi
    private House house;
    private Room room;
    
    // Nandai udah makan
    private boolean isMakan = false;

    // Reset saat ganti kerja
    private int totalWaktuKerja = 0;
    private int jedaGantiKerja = 720;
    
    // Reset jika ganti hari
    private int sisaWaktuTidur = 0;
    private int waktuTidakTidur = 0;

    // Reset jika sudah buang air
    private int waktuTidakBuangAir = 0;
    private boolean isBuangAir = false;

    // Thread
    private static Thread thread;


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
            inventoryFurniture.tambahStock(furniture,1);
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
        House house = new House("H" + String.format("%04d",world.getHouseList().size() + 1) + " ");
        world.setHouse(x, y, house);
        return house;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public Job getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(Job pekerjaan) {
        this.pekerjaan = pekerjaan;
        jedaGantiKerja = 0;
    }

    public int getUang() {
        return uang;
    }

    public void setUang(int uang) {
        this.uang = uang;
    }

    // public Inventory getInventory() {
    //     return inventory;
    // }

    // public void setInventory(Inventory inventory) {
    //         this.inventory = inventory;
    //     }

    public Inventory<BahanMakanan> getInventoryBahanMakanan() {
        return inventoryBahanMakanan;
    }

    public void setInventoryBahanMakanan(Inventory<BahanMakanan> inventoryBahanMakanan) {
        this.inventoryBahanMakanan = inventoryBahanMakanan;
    }

    public Inventory<Furniture> getInventoryFurniture() {
        return inventoryFurniture;
    }

    public void setInventoryFurniture(Inventory<Furniture> inventoryFurniture) {
        this.inventoryFurniture = inventoryFurniture;
    }

    public Inventory<Masakan> getInventoryMasakan() {
        return inventoryMasakan;
    }

    public void setInventoryMasakan(Inventory<Masakan> inventoryMasakan) {
        this.inventoryMasakan = inventoryMasakan;
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

    public int getKekenyangan() {
        return kekenyangan;
    }

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

    public int getMood() {
        return mood;
    }

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

    public int getKesehatan() {
        return kesehatan;
    }

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

    public String getStatus() {
        return status;
    }

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

    public House getHouse(){
        return this.house;
    }
    
    public void setHouse(House house){
        this.house = house;
    }

    public Room getRoom(){
        return this.room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public Boolean isDead(){
        if(this.getKekenyangan() < 0 || this.getKesehatan() < 0 || this.getMood() < 0){
            world.removeSim(this.getNamaLengkap());
            return true
        }
        else{
            return false;
        }
    }

    public House getOwnHouse(){
        return this.ownHouse;
    }

    public World getWorld() {
        return world;
    }

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

    public void printStat()
    {
        System.out.println("=========SIM SEDANG " + this.getStatus().toUpperCase() + "=========");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
        System.out.println("Uang anda sekarang: " + getUang());
    }

    // Ganti Hari
    public void gantiHari() {
        waktuTidakTidur = 0;
        waktuTidakBuangAir = 0;
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


    // Aksi Kerja
    public void kerja(int lamaKerja)
    {   
        if(jedaGantiKerja >= 720 && lamaKerja%120 == 0)
        {
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try {
                        System.out.println("==========SIM SEDANG BEKERJA==========");
                        System.out.println("        .-------,     ");
                        System.out.println("     ../         \\  ");
                        System.out.println("    /  ,   ,   ,  \\ ");
                        System.out.println("   /  , \\__\\___\\   \\ ");
                        System.out.println("  |   | __ || __',. \\  ");
                        System.out.println("  |   \\_'_/ \\_'_/.   |  Kerja YUKK!");
                        System.out.println("  |  (|    v    |)   |  ---   -----");
                        System.out.println("  ,    |       |    .       /");
                        System.out.println("   |    \\  ~  /     |   ---'");
                        System.out.println("   |   /. | | .\\    .");
                        System.out.println("   / ,/ |/   \\| \\,  |,");
                        System.out.println("  ( <-,  \\___/  ,->   )");
                        System.out.println("   |  ,_ \\   / _,  .|");
                        System.out.println("   | \\  \\ \\ / /   / |");
                        System.out.println("   | |   \\ * /    | |");
                        System.out.println("   | |     #      | |");
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
                        setStatus("Sedang Bekerja");
            
                        totalWaktuKerja += lamaKerja;
            
            
                        if(totalWaktuKerja >= 240)
                        {
                            if(pekerjaan.getName().equalsIgnoreCase("Badut Sulap"))
                            {
                                int tambahGaji = getUang() + (240/240) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Koki"))
                            {
                                int tambahGaji = getUang() + (240/240) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Polisi"))
                            {
                                int tambahGaji = getUang() + (240/240) *getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Programmer"))
                            {
                                int tambahGaji = getUang() + (240/240) * getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
                            else if(pekerjaan.getName().equalsIgnoreCase("Dokter"))
                            {
                                int tambahGaji = getUang() + (240/240) *getPekerjaan().getDailySalary();
                                setUang(tambahGaji);
                            }
            
                            int temp = totalWaktuKerja-240;
                            totalWaktuKerja = temp;
                        }

                        // Tambahin Waktu ke World
                        if(!isMakan)
                        {
                            getWorld().TambahWaktuTidakBuangAir(getNamaLengkap(), lamaKerja);
                        }
                        
                        getWorld().TambahWaktuTidakTidur(getNamaLengkap(), lamaKerja);

                        // print stats
                        System.out.println("=========SIM SELESAI BEKERJA=========");
                        System.out.println("Anda bekerja selama " + lamaKerja + " detik");
                        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
                        System.out.println("Mood anda sekarang: " + getMood());
                        System.out.println("Uang anda sekarang: " + getUang());
                        System.out.println(" ");
                    }
                }
            });
            thread.run();
        }

        else
        {
            System.out.println("Belum ada jeda satu hari saat anda pindah kerja atau waktu kerja salah");
        }
    }

    public void olahraga(int lamaOlahraga)
    {
        if(lamaOlahraga%20 == 0)
        {   
            thread = new Thread(new Runnable() 
            {
               public void run()
               {
                    try
                    {
                        System.out.println("==========SIM SEDANG OLAHRAGA==========");
                        System.out.println("     ");
                        System.out.println("                        ,////,");
                        System.out.println("                        /// 6|");
                        System.out.println("                        //  _|");
                        System.out.println("                       _/_,-'");
                        System.out.println("                  _.-/'/   \\   ,/,;");
                        System.out.println("               ,-' /'  \\_   \\ / _/");
                        System.out.println("               `\\ /     _/\\  ` /");
                        System.out.println("                 |     /,  `\\_/");
                        System.out.println("                 |     \\'");
                        System.out.println("    /\\_        /`      /\\");
                        System.out.println("   /' /_``--.__/\\  `,. /  \\");
                        System.out.println("  |_/`  `-._     `\\/  `\\   `.");
                        System.out.println("            `-.__/'     `\\   |");
                        System.out.println("                          `\\  \\");
                        System.out.println("                            `\\ \\");
                        System.out.println("                              \\_\\__");
                        System.out.println("                               \\___)");
        
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

                        setStatus("Olahraga");

                        // Tambah hashmap
                        if(!isMakan)
                        {
                            getWorld().TambahWaktuTidakBuangAir(getNamaLengkap(), lamaOlahraga);

                        }
                        
                        getWorld().TambahWaktuTidakTidur(getNamaLengkap(), lamaOlahraga);

                        // print stats
                        System.out.println("=========SIM SELESAI OLAHRAGA=========");
                        System.out.println("SIM berolahraga selama " + lamaOlahraga + " detik");
                        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
                        System.out.println("Mood anda sekarang: " + getMood());
                        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
                        System.out.println(" ");
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
                    
                        setStatus("Tidur");
                        sisaWaktuTidur += (lamaTidur - ((lamaTidur/240)*240));
                        
                        // Update hashmap
                        getWorld().updateWaktuTidakTidur(getNamaLengkap(), 0);

                        if(!isMakan)
                        {
                            getWorld().TambahWaktuTidakBuangAir(getNamaLengkap(), lamaTidur);
                        }
                        
                        // print stats
                        System.out.println("=========SIM SEDANG TIDUR=========");
                        System.out.println("Anda tidur selama " + lamaTidur + " detik");
                        System.out.println("Mood anda sekarang: " + getMood());
                        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
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
        System.out.println("Anda tidak tidur selama " + waktuTidakTidur + " detik");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
    }

    // Aksi Masak
    public static void masak(Masakan masakan, Inventory<BahanMakanan> inventoryBahanMakanan , Inventory<Masakan> inventoryMasakan) {
        List<BahanMakanan> bahanMakanan = masakan.getBahanMakanan();
        HashMap<BahanMakanan, Integer> stockBahanMakanan = inventoryBahanMakanan.getStock();
        for (BahanMakanan bahan : bahanMakanan) {
            if (stockBahanMakanan.containsKey(bahan)) {
                int jumlah = stockBahanMakanan.get(bahan);
                if (jumlah <= 0) {
                    System.out.println("Maaf, stock bahan makanan " + bahan.getName() + " habis");
                    return;
                }
            } else {
                System.out.println("Maaf, stock bahan makanan " + bahan.getName() + " tidak ada");
                return;
            }
        }
        
        for (BahanMakanan bahan : bahanMakanan) {
            inventoryBahanMakanan.kurangiStock(bahan, 1);
        }
        
        try{
            System.out.println("     ( ( (              ))     ");
            System.out.println("      ) ) )           ((       ");
            System.out.println("     ( ( (          ___o___");
            System.out.println("   '. ___ .'        |     |====O");
            System.out.println("  '  (> <) '        |_____|");
            System.out.println("--ooO-(_)-Ooo--------------------");
            System.out.println(" ");
            
            // Nunggu Masak
            double waktuMasak = 1.5 * masakan.getKekenyangan();
            Thread.sleep((long)waktuMasak);
            
            // Nambahin Masakan
            inventoryMasakan.tambahStock(masakan, 1);
            System.out.println("Masakan " + masakan.getNama() + " berhasil dimasak");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Update hashmap
        // getWorld().updateWaktuTidakTidur(getNamaLengkap(), 0);

        // if(!isMakan)
        // {
        //     getWorld().TambahWaktuTidakBuangAir(getNamaLengkap(), lamaTidur);
        // }
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

                        //buang air dan waktuTidak buang air di reset
                        isBuangAir = false;
                        isMakan = true;
                        
                        // nambahin hashmap
                        world.updateWaktuTidakBuangAir(getNamaLengkap(), 0);
                        world.TambahWaktuTidakTidur(getNamaLengkap(), 30);
                        
                        // print stats
                        System.out.println("=========SIM SEDANG MAKAN=========");
                        System.out.println("Anda makan " + masakan.getNama());
                        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
                        System.out.println("==================================");
                    }
               }
            });
            thread.start();
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
                        
                        System.out.println("          __      ");
                        System.out.println("         /\\ `.");
                        System.out.println("         ^^)_|");
                        System.out.println("         \\/(_");
                        System.out.println("           )/,`.");
                        System.out.println("        __((  \\/");
                        System.out.println("       /.--|_.L\\\\_");
                        System.out.println("           \\, \\ \\=");
                        System.out.println("           / ,/L");
                        System.out.println("          7 (\\ \\...,_" );
                        System.out.println("          | | \\____| )");
                        System.out.println("          ]_|      `\\)");
                        System.out.println("          /_)  ");
                        System.out.println("         `\"");  
                        System.out.println("  ");

                        // Tunggu 
                        Thread.sleep((long)waktuPerjalanan*1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        int moodNaik = getMood() + (int)waktuPerjalanan/30*10;
                        setMood(moodNaik);
                        int kenyangTurun = getKekenyangan() + (int)waktuPerjalanan/30*(-10);
                        setKekenyangan(kenyangTurun);

                        setStatus("Berkunjung");
                        setHouse(visitedHouse);
                        setRoom(visitedHouse.getRoom(visitedRoom.getRoomName()));

                        // Print hasil
                        System.out.println("=========SIM SEDANG BERKUNJUNG=========");
                        System.out.println("Waktu perjalanan: " + waktuPerjalanan + " detik");
                        System.out.println("posisi anda sekarang: X " + houseLocation[0] + ", Y " + houseLocation[1]);

                        // Tambah Hashmap
                        if(!isMakan)
                        {
                            world.TambahWaktuTidakBuangAir(getNamaLengkap(), (int)waktuPerjalanan);
                        }
                        else
                        {
                            world.TambahWaktuTidakTidur(getNamaLengkap(), (int)waktuPerjalanan);
                        }
                        world.TambahWaktuTidakTidur(getNamaLengkap(), (int)waktuPerjalanan);
                    }
               }
            });
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
                    System.out.println("          _____");
                    System.out.println("         /      \\");
                    System.out.println("        (____/\\  )");
                    System.out.println("         |___  U?(____");
                    System.out.println("         _\\L.   |      \\     ___");
                    System.out.println("       / /\"\"\"\\ /.-'     |   |\\  |");
                    System.out.println("      ( /  _/u     |    \\___|_)_|");
                    System.out.println("       \\|  \\\\      /   / \\_(___ __)");
                    System.out.println("        |   \\\\    /   /  |  |    |");
                    System.out.println("        |    )  _/   /   )  |    |");
                    System.out.println("        _\\__/.-'    /___(   |    |    ");
                    System.out.println("     _/  __________/     \\  |    |");
                    System.out.println("    //  /  (              ) |    |");
                    System.out.println("   ( \\__|___\\    \\______ /__|____|");
                    System.out.println("    \\    (___\\   |______)_/");
                    System.out.println("     \\   |\\   \\  \\     /");
                    System.out.println("      \\  | \\__ )  )___/");
                    System.out.println("       \\  \\  )/  /__(       ");
                    System.out.println("   ___ |  /_//___|   \\_________");
                    System.out.println("     _/  ( /          \\");
                    System.out.println("    `----'(____________)");
                    System.out.println(" ");
                        
                    // Tunggu 10 detik
                    Thread.sleep(10*1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    int kenyangTurun = getKekenyangan() - 20;
                    setKekenyangan(kenyangTurun);
                    int moodNaik = getMood() + 10;
                    setMood(moodNaik);
                    
                    waktuTidakBuangAir = 0;
                    isBuangAir = true;
                    
                    setStatus("Buang Air");
                    System.out.println("Uhhh lega... SIM sudah buang air");

                    // print stats
                    printStat();

                    //Update Hashmap
                    world.updateWaktuTidakBuangAir(getNamaLengkap(), 0);
                    world.TambahWaktuTidakTidur(getNamaLengkap(), 10);     
                }
            }
        });
        thread.run();
    }

    public void efekTidakBuangAir()
    {
        if(world.getWaktuTidakBuangAir(getNamaLengkap()) >= 240 && isBuangAir == false)
        {
            int moodTurun = getMood() - 5;
            setMood(moodTurun);
            int kenyangTurun = getKekenyangan() - 5;
            setKekenyangan(kenyangTurun);
        }

        // print stats
        System.out.println("Anda tidak buang air selama " + world.getWaktuTidakBuangAir(getNamaLengkap()) + " detik");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
    }

    // AKSI TAMBAHAN
    public void mainGame(int lamaMain)
    {
        if (lamaMain > 0){
            thread = new Thread(new Runnable() {
                public void run(){
                    try {
                        System.out.println("==========SIM SEDANG MAIN GAME==========");
                        System.out.println(" _n_________________");
                        System.out.println("|_|_______________|_|");
                        System.out.println("|  ,-------------.  |");
                        System.out.println("| |  .---------.  | |");
                        System.out.println("| |  |         |  | |");
                        System.out.println("| |  |         |  | |");
                        System.out.println("| |  |         |  | |");
                        System.out.println("| |  |         |  | |");
                        System.out.println("| |  `---------'  | |");
                        System.out.println("| `---------------' |");
                        System.out.println("|   _ GAME BOY      |");
                        System.out.println("| _| |_         ,-. |");
                        System.out.println("||_ O _|   ,-. "._,"|");
                        System.out.println("|  |_|    "._,"   A | hjw");
                        System.out.println("|    _  _    B      | `97");
                        System.out.println("|   // //           |");
                        System.out.println("|  // //    ///////  |");
                        System.out.println("|  `  `      /////// ,");
                        System.out.println("|________...______,\"");
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
                        } else {
                            setStatus("Main Game");
                            printStat();
                            world.addWaktuDunia(lamaMain);
                            world.kurangiWaktuUpgrade(lamaMain);
                            world.checkUpgradeRoom();
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
                        System.out.println("==========SIM SEDANG MENYANTET==========");
                        System.out.println("    .-')     ('-.         .-') _  .-') _     ('-.   .-') _    ");
                        System.out.println("    ( OO ).  ( OO ).-.    ( OO ) )(  OO) )  _(  OO) (  OO) )   ");
                        System.out.println("   (_)---\_) / . --. /,--./ ,--,' /     '._(,------./     '._  ");
                        System.out.println("   /    _ |  | \-.  \ |   \ |  |\ |'--...__)|  .---'|'--...__) ");
                        System.out.println("   \  :` `..-'-'  |  ||    \|  | )'--.  .--'|  |    '--.  .--' ");
                        System.out.println("    '..`''.)\| |_.'  ||  .     |/    |  |  (|  '--.    |  |    ");
                        System.out.println("   .-._)   \ |  .-.  ||  |\    |     |  |   |  .--'    |  |    ");
                        System.out.println("   \       / |  | |  ||  | \   |     |  |   |  `---.   |  |    ");
                        System.out.println("    `-----'  `--' `--'`--'  `--'     `--'   `------'   `--'    ");
                        Thread.sleep(waktuDibutuhkan*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // this sim
                        int thisMood = getMood() + 15;
                        this.setMood(thisMood);
                        int thisKesehatan = getKesehatan() - 5;
                        this.setKesehatan(thisKesehatan);
                        int thisKekenyangan = getKekenyangan() - 5;
                        this.setKekenyangan(thisKekenyangan);

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
                            this.setStatus("Santet");
                            printStat();
                            world.addWaktuDunia(waktuDibutuhkan);
                            world.kurangiWaktuUpgrade(waktuDibutuhkan);
                            world.checkUpgradeRoom();
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
                            world.addWaktuDunia(lamaBerobat);
                            world.kurangiWaktuUpgrade(lamaBerobat);
                            world.checkUpgradeRoom();
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
                        Thread.sleep(lamaBerobat*1000);
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
                            world.addWaktuDunia(lamaKaraoke);
                            world.kurangiWaktuUpgrade(lamaKaraoke);
                            world.checkUpgradeRoom();
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
                        world.addWaktuDunia(waktuDibutuhkan);
                        world.kurangiWaktuUpgrade(waktuDibutuhkan);
                        world.checkUpgradeRoom();
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
                        System.out.println("==========SIM SEDANG BERSIH-BERSIH==========");
                        System.out.println("         888                            d8b                   ");
                        System.out.println("         888                            Y8P                   ");
                        System.out.println("         888                                                  ");
                        System.out.println(".d8888b  888  .d88b.   8888b.  88888b.  888 88888b.   .d88b.  ");
                        System.out.println("d88P"    888 d8P  Y8b     "88b 888 "88b 888 888 "88b d88P"88b ");
                        System.out.println("888      888 88888888 .d888888 888  888 888 888  888 888  888 ");
                        System.out.println("Y88b.    888 Y8b.     888  888 888  888 888 888  888 Y88b 888 ");
                        System.out.println(""Y8888P  888  "Y8888  "Y888888 888  888 888 888  888  "Y88888 ");
                        System.out.println("                                                     888 ");
                        System.out.println("                                                Y8b d88P ");
                        System.out.println("                                                 \"Y88P\"  ");
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
                            world.addWaktuDunia(lamaBersihBersih);
                            world.kurangiWaktuUpgrade(lamaBersihBersih);
                            world.checkUpgradeRoom();
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
                        world.addWaktuDunia(waktuDibutuhkan);
                        world.kurangiWaktuUpgrade(waktuDibutuhkan);
                        world.checkUpgradeRoom();
                    }
                }
            }
        });
        thread.run();
    }

    //Aksi Upgrade Rumah
    public void upgradeHouse(Room newRoom)
    {
        if(getUang() >= 1500)
        {
            getOwnHouse().upgradeHouse(newRoom);
        }

        else
        {
            System.out.println("Uang tidak cukup");
        }
    }

    //Aksi Move To Objek
    public void moveToObject(int x, int y) {
        String objName = this.room.getLayoutContent(x , y);
            if (objName != "") {
                // System.out.println("Moving to " + obj.getName() + " at (" + x + "," + y + ")");
                switch(objName) {
                    case "KS":
                    System.out.println("Tidur");
                        //Tidur
                        break;
                    case "KQS":
                    System.out.println("Tidur");
                        //Tidur
                        break;
                    case "KKS":
                    System.out.println("Tidur");
                        //Tidur
                        break;
                    case "TLT":
                    System.out.println("Buang Air");
                        //Buang air
                        break;
                    case "KMG":
                    System.out.println("Masak");
                        //Masak()
                        break;
                    case "KML":
                    System.out.println("Masak");
                        //Masak()
                        break;
                    case "MDK":
                    System.out.println("Belajar");
                        //Do something
                        break;
                    case "JAM":
                    System.out.println("Lihat Waktu");
                        //Melihat Waktu
                        break;
                }
            }
            else{
                System.out.println("Object not found at (" + x + "," + y + ")");
            }
    }
}