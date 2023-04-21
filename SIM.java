package Bagian;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;

public class SIM {
    private String nama;
    private Job pekerjaan;
    private int uang;
    private Inventory inventory;
    private int kekenyangan;
    private int mood;
    private int kesehatan;
    private String status;
    private House rumah;
    private simLoc lokasiSIM;
    
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
        
        this.uang = 100;
        this.mood = 80;
        this.kesehatan = 80;
        this.kekenyangan = 80;
        this.status = null;
        this.inventory = new Inventory();
        this.rumah = new House(new Coordinate(21, 21));
        this.lokasiSIM = new simLoc(rumah);
    }

    // Getter & Setter
    public String getNamaLengkap() {
        return nama;
    }

    public String getPekerjaan() {
        return pekerjaan.getName();
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

    public int getUang() {
        return uang;
    }

    public void setUang(int uang) {
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
        this.kekenyangan += kekenyangan;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood += mood;
    }

    public int getKesehatan() {
        return kesehatan;
    }

    public void setKesehatan(int kesehatan) {
        this.kesehatan += kesehatan;
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

    // public Integer getWaktu()
    // {
    //     return waktu;
    // }

    // public void setWaktu(Integer waktu)
    // {
    //     this.waktu = waktu;
    // }

    // View sims stats
    public void viewStats()
    {
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


    // Aksi yang bisa dilakukan
    public void kerja(int lamaKerja)
    {   
        if(jedaGantiKerja >= 720)
        {
            int kenyangTurun = getKekenyangan()+lamaKerja/30*(-10);
            setKekenyangan(kenyangTurun);
            int moodTurun = getMood() + lamaKerja/30*(-10);
            setMood(moodTurun);
            setStatus("Sedang bekerja");

            if(pekerjaan.getName().equalsIgnoreCase("Badut Sulap"))
            {
                uang += lamaKerja/240*pekerjaan.getGaji();
                setUang(uang);
            }
            else if(pekerjaan.getName().equalsIgnoreCase("Koki"))
            {
                uang += lamaKerja/240*pekerjaan.getGaji();
                setUang(uang);
            }
            else if(pekerjaan.getName().equalsIgnoreCase("Polisi"))
            {
                uang += lamaKerja/240*pekerjaan.getGaji();
                setUang(uang);
            }
            else if(pekerjaan.getName().equalsIgnoreCase("Programmer"))
            {
                uang += lamaKerja/240*pekerjaan.getGaji();
                setUang(uang);
            }
            else if(pekerjaan.getName().equalsIgnoreCase("Dokter"))
            {
                uang += lamaKerja/240*pekerjaan.getGaji();
                setUang(uang);
            }

            totalWaktuKerja += lamaKerja;

            // print stats
            System.out.println("Anda bekerja selama " + lamaKerja + " detik");
            System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
            System.out.println("Mood anda sekarang: " + getMood());
            System.out.println("Uang anda sekarang: " + getUang());
        }

        else
        {
            System.out.println("Belum ada jeda satu hari saat anda pindah kerja");
        }
    }

    public void olahraga(int lamaOlahraga)
    {
        int kenyangTurun = getKekenyangan() + lamaOlahraga/20*(-5);
        setKekenyangan(kenyangTurun);
        int moodNaik = getMood() + lamaOlahraga/20*10;
        setMood(moodNaik);
        int kesehatanNaik = getKesehatan() + lamaOlahraga/20*10;
        setKesehatan(kesehatanNaik);

        setStatus("Olahraga");

        // print stats
        System.out.println("Anda olahraga selama " + lamaOlahraga + " detik");
        System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
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
        System.out.println("Anda tidak tidur selama " + waktuTidakTidur + " detik");
        System.out.println("Mood anda sekarang: " + getMood());
        System.out.println("Kesehatan anda sekarang: " + getKesehatan());
    }

    public void memasak(BahanMakanan bahanMakanan)
    {
        this.kekenyangan = this.kekenyangan + 10;
        this.mood = this.mood + 10;
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

            // print stats
            System.out.println("Anda makan " + masakan.getNama());
            System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
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
        Coordinate houseLoc = house.getCoordinate();
        Coordinate visitedHouseLoc = visitedHouse.getCoordinate();

        int selisihX = visitedHouseLoc.getX()- houseLoc.getX();
        int selisihY = visitedHouseLoc.getY()- houseLoc.getY();
        int kuadratX = selisihX*selisihX;
        int kuadratY = selisihY*selisihY;

        int waktuPerjalanan = kuadratX - kuadratY;
        
        int moodNaik = getMood() + waktuPerjalanan/30*10;
        setMood(moodNaik);
        int kenyangTurun = getKekenyangan() + waktuPerjalanan/30*(-10);
        setKekenyangan(kenyangTurun);

        setStatus("Berkunjung");

        // Print hasil
        System.out.println("Waktu perjalanan: " + waktuPerjalanan);
        System.out.println("posisi anda sekarang: " + visitedHouseLoc.getX() + " " + visitedHouseLoc.getY());

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
        System.out.println("Uhhh lega... udah buang air");
        
        // return waktu yang digunakan untuk buang air
        return 10;
    }

    // public void efekTidakBuangAir()
    // {
    //     if(waktuTidakBuangAir > 600 && isBuangAir == false)
    //     {
    //         int moodTurun = getMood() - 5;
    //         setMood(moodTurun);
    //         int kenyangTurun = getKekenyangan() - 5;
    //         setKekenyangan(kenyangTurun);
    //     }

    //     // print stats
    //     System.out.println("Anda tidak buang air selama " + waktuTidakBuangAir + " detik");
    //     System.out.println("Mood anda sekarang: " + getMood());
    //     System.out.println("Kekenyangan anda sekarang: " + getKekenyangan());
    // }

    public void pembelian(String item, int jumlah) { 
        // tambahkan item ke inventory
        for (BahanMakanan bahan : BahanMakanan.values()) 
        {
            if (bahan.getName().equalsIgnoreCase(item.replaceAll("\\s+",""))) 
            {   
                double totalHarga = bahan.getHarga() * jumlah;
                if (uang >= totalHarga) 
                {       
                    // kurangi uangVirtual dengan totalHarga
                    uang -= totalHarga;
                    setUang(uang);
                    inventory.tambahStock(bahan, jumlah);
                    System.out.println("Pembelian berhasil!");    
                }

                else 
                {
                    System.out.println("Maaf, uangVirtual Anda tidak mencukupi untuk melakukan pembelian.");
                }
            }
        } 
        
        for(Furniture furniture : Furniture.values())
        {
            if(furniture.getName().equalsIgnoreCase(item.replaceAll("\\s+","")))
            {
                double totalHarga = furniture.getHarga() * jumlah;
                if (uang >= totalHarga) 
                {       
                    uang -= totalHarga;
                    setUang(uang);
                    inventory.tambahStock(furniture, jumlah);
                    System.out.println("Pembelian berhasil!");    
                }

                else 
                {
                    System.out.println("Maaf, uangVirtual Anda tidak mencukupi untuk melakukan pembelian.");
                }
            }
        }
    }
    
    // Aksi tidak menggunakan waktu
    public void berpindahRuangan(Room newRoom)
    {
        Ruangan ruangan = getRumah().getRuangan(newRoom);
        setRuangan(newRoom);
    }

    public void lihatInventory()
    {
        this.inventory.printInventory();
    }
    
}

