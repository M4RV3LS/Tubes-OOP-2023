import java.util.*;

import Fitur.*;
import Inventory.*;
import Sim.*;


public class CookDriver {

    public static void main(String[] args) {

        // Inisialisasi Sim dan Inventory
        Sim sim = new Sim("Marvel");
        Inventory<BahanMakanan> inventoryBahanMakanan = sim.getInventoryBahanMakanan();
        Inventory<Masakan> inventoryMasakan = sim.getInventoryMasakan();

        System.out.println("");
        // Print daftar masakan yang tersedia
        Cook.showMasakan();

        // Menambahkan bahan makanan ke inventory
        BahanMakanan nasi = BahanMakanan.NASI;
        BahanMakanan ayam = BahanMakanan.AYAM;
        inventoryBahanMakanan.tambahStock(nasi, 10);
        inventoryBahanMakanan.tambahStock(ayam, 5);

        // Menambahkan masakan ke inventory
        Masakan nasiAyam = Masakan.NASI_AYAM;
        Masakan susuKacang = Masakan.SUSU_KACANG;
        Cook.masak(nasiAyam, inventoryBahanMakanan, inventoryMasakan);
        Cook.masak(susuKacang, inventoryBahanMakanan, inventoryMasakan);

        System.out.println("");
        // Print inventory
        inventoryBahanMakanan.printInventory();
        inventoryMasakan.printInventory();
    }

}
