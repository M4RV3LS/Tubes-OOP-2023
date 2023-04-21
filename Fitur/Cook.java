package Fitur;


import java.util.HashMap;
import java.util.List;

import Inventory.BahanMakanan;
import Inventory.Inventory;
import Inventory.Masakan;
import Sim.*;

public class Cook {

    private Sim sim;

    public Cook(Sim sim){
        this.sim = sim;
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
    
    public static void showMasakan() {
        System.out.println("List Bahan Makanan yang Dijual:");
        for (Masakan masakan : Masakan.values()) {
            System.out.println("Nama: " + masakan.getNama() +
                               ", Kekenyangan: " + masakan.getKekenyangan());
            System.out.println();
        }
    }
    
}
