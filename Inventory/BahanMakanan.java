package Inventory;

import Map.MyObject;

public enum BahanMakanan {
    NASI("Nasi", 5, 5),
    KENTANG("Kentang", 3, 4),
    AYAM("Ayam", 10, 8),
    SAPI("Sapi", 12, 15),
    WORTEL("Wortel", 3, 2),
    BAYAM("Bayam", 3, 2),
    KACANG("Kacang" , 2 , 2),
    SUSU("Susu" , 2 , 1);

    private final String name;
    private final int harga;
    private final int kekenyangan;

    BahanMakanan(String name, int harga, int kekenyangan) {
        this.name = name;
        this.harga = harga;
        this.kekenyangan = kekenyangan;
    }

    public String getName() {
        return name;
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public int getHarga() {
        return harga;
    }
}