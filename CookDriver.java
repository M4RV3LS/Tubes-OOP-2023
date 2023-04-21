import java.util.*;

import Fitur.*;
import Inventory.*;
import Sim.*;

public class CookDriver {

    public static void main(String[] args) {

        // Inisialisasi Sim dan Inventory
        Sim sim = new Sim("Marvel");
        Inventory inventory = sim.getInventory();

        System.out.println("");
        // Print daftar masakan yang tersedia
        Cook.showMasakan();

        // Menambahkan bahan makanan ke inventory
        BahanMakanan nasi = BahanMakanan.NASI;
        BahanMakanan ayam = BahanMakanan.AYAM;
        inventory.tambahStock(nasi, 10);
        inventory.tambahStock(ayam, 5);

        // Menambahkan daftar masakan
        Masakan nasiAyam = Masakan.NASI_AYAM;
        Masakan susuKacang = Masakan.SUSU_KACANG;
        Cook.masak(nasiAyam , inventory);
        Cook.masak(susuKacang , inventory);

        

        System.out.println("");
        //print inventory
        inventory.printInventory();
    }

}
