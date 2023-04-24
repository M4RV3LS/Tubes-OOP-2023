import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class SIM {
    private String nama;
    private Job pekerjaan;
    private Double uang;
    private Inventory inventory;
    private int kekenyangan;
    private int mood;
    private int kesehatan;
    private String status;
    
    // Indikasi Lokasi rumah dan ruangan dari sim saat ini
    private House rumah;
    private String ruangan;
    
    // Reset saat ganti kerja
    private int totalWaktuKerja = 0;
    private int jedaGantiKerja = 720;
    
    // Reset jika ganti hari
    private int totalWaktuTidur = 0;
    private int waktuTidakTidur = 0;

    // Reset jika sudah buang air
    private int waktuTidakBuangAir = 0;
    private boolean isBuangAir = false;

    public SIM(String nama) {
        this.nama = nama;

        // Shuffle list 
        Job[] daftarPekerjaan = Job.values();
        int randIdx = ThreadLocalRandom.current().nextInt(daftarPekerjaan.length);
        Job randomElem = daftarPekerjaan[randIdx];
        this.pekerjaan = randomElem;
        
        this.uang = 100.0;
        this.mood = 80;
        this.kesehatan = 80;
        this.kekenyangan = 80;
        this.status = null;
        this.inventory = new Inventory();
        this.rumah = new House(12, 12);
        this.ruangan = rumah.getNamaRuangan(0);
    }

    // Getter & Setter
    public String getNamaLengkap() {
        return nama;
    }

    public Job getPekerjaan() {
        return this.pekerjaan;
    }

    public void setPekerjaan(Job pekerjaan) {
        if(totalWaktuKerja >= 720)
        {
            this.pekerjaan = pekerjaan;
            totalWaktuKerja = 0;
            jedaGantiKerja = 0;
            uang -= pekerjaan.getGaji()*0.5;
        }
    }

    public Double getUang() {
        return uang;
    }

    public void setUang(Double uang) {
        this.uang = uang;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
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
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public House getRumah() {
        return rumah;
    }

    public void setRumah(House rumah) {
        this.rumah = rumah;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    // View sims stats
    public void viewStats()
    {
        System.out.println("===========STATS===========");
        System.out.println("Nama: " + getNamaLengkap());
        System.out.println("Pekerjaan: " + getPekerjaan());
        System.out.println("Uang: " + getUang());
        System.out.println("Kekenyangan: " + getKekenyangan());
        System.out.println("Mood: " + getMood());
        System.out.println("Kesehatan: " + getKesehatan());
        System.out.println("Status: " + getStatus());
    }

    // Ganti Hari
    public void gantiHari() {
        totalWaktuTidur = 0;
    }

    // Lokasi SIM
    public void lokasiSIM()
    {
        System.out.println("===========LOKASI SIM===========");
        System.out.println("SIM berada pada rumah dengan koordinate: "); 
        System.out.println("X: " + getRumah().getX());
        System.out.println("Y: " + getRumah().getY());
        System.out.println("SIM berada di dalam ruangan: " + getRuangan());
        
    }


    // Aksi yang bisa dilakukan
    public double kerja(int lamaKerja)
    {   
        if(jedaGantiKerja >= 720 && lamaKerja%120 == 0)
        {
            int kenyangTurun = getKekenyangan()+lamaKerja/30*(-10);
            setKekenyangan(kenyangTurun);
            int moodTurun = getMood() + lamaKerja/30*(-10);
            setMood(moodTurun);
            setStatus("Sedang bekerja");

            totalWaktuKerja += lamaKerja;


            if(totalWaktuKerja >= 240)
            {
                if(pekerjaan.getName().equalsIgnoreCase("Badut Sulap"))
                {
                    Double tambahGaji = getUang() + (240/240.0) * getPekerjaan().getGaji();
                    setUang(tambahGaji);
                }
                else if(pekerjaan.getName().equalsIgnoreCase("Koki"))
                {
                    Double tambahGaji = getUang() + (240/240.0) * getPekerjaan().getGaji();
                    setUang(tambahGaji);
                }
                else if(pekerjaan.getName().equalsIgnoreCase("Polisi"))
                {
                    Double tambahGaji = getUang() + (240/240.0) *getPekerjaan().getGaji();
                    setUang(tambahGaji);
                }
                else if(pekerjaan.getName().equalsIgnoreCase("Programmer"))
                {
                    Double tambahGaji = getUang() + (240/240.0) * getPekerjaan().getGaji();
                    setUang(tambahGaji);
                }
                else if(pekerjaan.getName().equalsIgnoreCase("Dokter"))
                {
                    Double tambahGaji = getUang() + (240/240.0) *getPekerjaan().getGaji();
                    setUang(tambahGaji);
                }

                int temp = totalWaktuKerja-240;
                totalWaktuKerja = temp;
            }

            // print stats
            System.out.println("=========SIM SEDANG BEKERJA=========");
            System.out.println("Anda bekerja selama " + lamaKerja + " detik");
            System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
            System.out.println("Mood anda sekarang: " + getMood());
            System.out.println("Uang anda sekarang: " + getUang());
            System.out.println(" ");
        }

        else
        {
            System.out.println("Belum ada jeda satu hari saat anda pindah kerja atau waktu kerja salah");
        }

        return lamaKerja;
    }

    public void olahraga(int lamaOlahraga)
    {
        if(lamaOlahraga%20 == 0)
        {
            int kenyangTurun = getKekenyangan() + lamaOlahraga/20*(-5);
            setKekenyangan(kenyangTurun);
            int moodNaik = getMood() + lamaOlahraga/20*10;
            setMood(moodNaik);
            int kesehatanNaik = getKesehatan() + lamaOlahraga/20*5;
            setKesehatan(kesehatanNaik);

            setStatus("Olahraga");

            // print stats
            System.out.println("=========SIM SEDANG OLAHRAGA=========");
            System.out.println("SIM berolahraga selama " + lamaOlahraga + " detik");
            System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
            System.out.println("Mood anda sekarang: " + getMood());
            System.out.println("Kesehatan anda sekarang: " + getKesehatan());
            System.out.println(" ");
        }

        else
        {
            System.out.println("Waktu olahraga salah");
        }
        
    }

    public void tidur(int lamaTidur)
    {
        int moodNaik = getMood() + lamaTidur/240*30;
        setMood(moodNaik);
        int kesehatanNaik = getKesehatan()+lamaTidur/240*20;
        setKesehatan(kesehatanNaik);

        setStatus("Tidur");;
        totalWaktuTidur += lamaTidur;
        waktuTidakTidur = 0;

        // print stats
        System.out.println("=========SIM SEDANG TIDUR=========");
        System.out.println("Anda tidur selama " + lamaTidur + " detik");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
    }
    
    public void efekTidakTidur()
    {
        if(waktuTidakTidur > 600 && totalWaktuTidur < 180)
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

    public static void masak(Masakan masakan, Inventory inventory) {
        List<BahanMakanan> bahanMakanan = masakan.getBahanMakanan();
        HashMap<BahanMakanan, Integer> stockBahanMakanan = inventory.getStockBahanMakanan();
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
            inventory.kurangiStock(bahan, 1);
        }

        inventory.tambahStock(masakan, 1);
        System.out.println("Masakan " + masakan.getNama() + " berhasil dimasak");
    }

    public static void printDaftarMasakan(List<Masakan> daftarMasakan) {
        System.out.println("Daftar Masakan:");
        for (Masakan masakan : daftarMasakan) {
            System.out.println(" - " + masakan.getNama());
            System.out.println("   Bahan Makanan:");
            for (BahanMakanan bahan : masakan.getBahanMakanan()) {
                System.out.println("   - " + bahan.getName());
            }
        }
    }

    public double makan(Masakan masakan)
    {
        if(inventory.getStock(masakan) > 0)
        {
            int kenyangNaik = getKekenyangan() + masakan.getKekenyangan();
            setKekenyangan(kenyangNaik);

            setStatus("Makan");
            
            // Remove masakan from inventory
            inventory.kurangiStock(masakan, 1);

            // Jika belum 4 menit tapi udah makan lagi
            if (!isBuangAir) {
                isBuangAir = false;
                waktuTidakBuangAir = 0;
            }
            // print stats
            System.out.println("=========SIM SEDANG MAKAN=========");
            System.out.println("Anda makan " + masakan.getNama());
            System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
            System.out.println("==================================");
        }
        else
        {

            System.out.println("Tidak ada masakan tersebut di inventory");
        }

        // return waktu yang digunakan untuk buang air
        return 30;
    }

    public double Berkunjung(House visitedHouse)
    {   
        House house = getRumah();
        int selisihX = Math.abs(visitedHouse.getX()-house.getX());
        int selisihY = Math.abs(visitedHouse.getY()-house.getY());
        
        double waktuPerjalanan = Math.sqrt(Math.pow(selisihX,2) + Math.pow(selisihY,2) );
        
        int moodNaik = getMood() + (int)waktuPerjalanan/30*10;
        setMood(moodNaik);
        int kenyangTurun = getKekenyangan() + (int)waktuPerjalanan/30*(-10);
        setKekenyangan(kenyangTurun);

        setStatus("Berkunjung");
        setRumah(visitedHouse);
        setRuangan(visitedHouse.getNamaRuangan(0));

        // Print hasil
        System.out.println("=========SIM SEDANG BERKUNJUNG=========");
        System.out.println("Waktu perjalanan: " + waktuPerjalanan + " detik");
        System.out.println("posisi anda sekarang: X " + visitedHouse.getX() + ", Y " + visitedHouse.getY());

        // return waktu yang digunakan untuk perjalanan
        return waktuPerjalanan;
    }

    public double buangAir()
    {   
        int kenyangTurun = getKekenyangan() - 20;
        setKekenyangan(kenyangTurun);
        int moodNaik = getMood() + 10;
        setMood(moodNaik);
        waktuTidakBuangAir = 0;
        isBuangAir = true;

        setStatus("Buang Air");
        System.out.println("=========SIM SEDANG BUANG AIR=========");
        System.out.println("Uhhh lega... SIM sudah buang air");
        
        // return waktu yang digunakan untuk buang air
        return 10;
    }

    public void efekTidakBuangAir()
    {
        if(waktuTidakBuangAir >= 240 && isBuangAir == false)
        {
            int moodTurun = getMood() - 5;
            setMood(moodTurun);
            int kenyangTurun = getKekenyangan() - 5;
            setKekenyangan(kenyangTurun);
        }

        // print stats
        System.out.println("Anda tidak buang air selama " + waktuTidakBuangAir + " detik");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
    }


    // AKSI BISA DITINGGAL
    public void pembelian(String item, int jumlah) { 
        // tambahkan item ke inventory
        for (BahanMakanan bahan : BahanMakanan.values()) 
        {
            if (bahan.getName().equalsIgnoreCase(item)) 
            {   
                double totalHarga = bahan.getHarga() * jumlah;
                if (uang >= totalHarga) 
                {       
                    // kurangi uangVirtual dengan totalHarga
                    uang -= totalHarga;
                    setUang(uang);

                    // Tunggu waktu pengiriman
                    Random random = new Random();
                    int nilaiAcak = random.nextInt(5) + 1; // menghasilkan nilai acak antara 1 hingga 5
                    int durasiDetik = nilaiAcak * 30;                    
                    
                    System.out.println("Menunggu waktu pengiriman...");
                    try {
                        Thread.sleep(10000);
                        System.out.println("Barang Telah Diantar");
                        // tambahkan stock bahan makanan ke inventory
                        inventory.tambahStock(bahan, jumlah);
                        System.out.println("Pembelian berhasil!");   
                    } 
                    
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    } 
                }

                else 
                {
                    System.out.println("Maaf, uang Anda tidak mencukupi untuk melakukan pembelian.");
                }
            }
        } 
        
        for(Furniture furniture : Furniture.values())
        {
            if(furniture.getName().equalsIgnoreCase(item))
            {
                double totalHarga = furniture.getHarga() * jumlah;
                if (uang >= totalHarga) 
                {     
                    // kurangi uangVirtual dengan totalHarga
                    uang -= totalHarga;
                    setUang(uang);

                    // Tunggu waktu pengiriman
                    Random random = new Random();
                    int nilaiAcak = random.nextInt(5) + 1; // menghasilkan nilai acak antara 1 hingga 5
                    int durasiDetik = nilaiAcak * 30;                    
                    
                    System.out.println("Menunggu waktu pengiriman...");
                    try {
                        Thread.sleep(10000);
                        System.out.println("Barang Telah Diantar");
                        // tambahkan stock bahan makanan ke inventory
                        inventory.tambahStock(furniture, jumlah);
                        System.out.println("Pembelian berhasil!");   
                    } 
                    
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }   
                }
                else 
                {
                    System.out.println("Maaf, uang Anda tidak mencukupi untuk melakukan pembelian.");
                }
            }
        }
    }
    
    public void showSaledItem() 
    {
        System.out.println("===== SELAMAT DATANG =====");
        System.out.println("=========== DI ============");
        System.out.println("==== PASAR SIMPLICITY ====");
        System.out.println(" ");

        System.out.println("List Furniture yang Dijual:");
        for (Furniture furniture : Furniture.values()) {
            System.out.println("Nama: " + furniture.getName() +
                               ", Harga: " + furniture.getHarga());
            System.out.println();
        }

        System.out.println("List Bahan Makanan yang Dijual:");
        for (BahanMakanan bahanMakanan : BahanMakanan.values()) {
            System.out.println("Nama: " + bahanMakanan.getName() +
                               ", Harga: " + bahanMakanan.getHarga());
            System.out.println();
        }
    }
    
    public void upgradeRumah(String namaRuangan)
    {
        rumah.ugradeRumah(namaRuangan);
    }

    // Aksi tidak menggunakan waktu
    public void berpindahRuangan(String ruangan)
    {   
        ArrayList<Room> ruanganRumah = rumah.getRooms();
        
        for(Room room : ruanganRumah)
        {
            if(room.getRoomName().equalsIgnoreCase(ruangan))
            {
                setRuangan(ruangan);
                System.out.println("Berhasil pindah ke ruangan " + ruangan);
            }
        }
    }

    public void lihatInventory()
    {
        this.inventory.printInventory();
    }
    
}

