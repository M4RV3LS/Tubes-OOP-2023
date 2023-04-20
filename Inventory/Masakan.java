package Inventory;
import java.util.*;
public enum Masakan {
    NASI_AYAM("Nasi Ayam", Arrays.asList(BahanMakanan.NASI, BahanMakanan.AYAM), 16),
    NASI_KARI("Nasi Kari", Arrays.asList(BahanMakanan.NASI, BahanMakanan.KENTANG, BahanMakanan.WORTEL, BahanMakanan.SAPI), 30),
    SUSU_KACANG("Susu Kacang", Arrays.asList(BahanMakanan.SUSU, BahanMakanan.KACANG), 5),
    TUMIS_SAYUR("Tumis Sayur", Arrays.asList(BahanMakanan.WORTEL, BahanMakanan.BAYAM), 5),
    BISTIK("Bistik", Arrays.asList(BahanMakanan.KENTANG, BahanMakanan.SAPI), 22);

    private final String nama;
    private final List<BahanMakanan> bahanMakanan;
    private final int kekenyangan;

    Masakan(String nama, List<BahanMakanan> bahanMakanan, int kekenyangan) {
        this.nama = nama;
        this.bahanMakanan = bahanMakanan;
        this.kekenyangan = kekenyangan;
    }

    public String getNama() {
        return nama;
    }

    public List<BahanMakanan> getBahanMakanan() {
        return bahanMakanan;
    }

    public int getKekenyangan() {
        return kekenyangan;
    }
}
