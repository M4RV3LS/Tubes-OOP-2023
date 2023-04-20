package Inventory;
import java.util.*;

public class Inventory {
    private HashMap<BahanMakanan, Integer> stockBahanMakanan;
    private HashMap<Masakan, Integer> stockMasakan;
    private HashMap<Furniture, Integer> stockFurniture;

    public Inventory() {
        stockBahanMakanan = new HashMap<>();
        stockMasakan = new HashMap<>();
        stockFurniture = new HashMap<>();
    }

    public void tambahStock(BahanMakanan bahanMakanan, int jumlah) {
        int currentStock = stockBahanMakanan.getOrDefault(bahanMakanan, 0);
        stockBahanMakanan.put(bahanMakanan, currentStock + jumlah);
    }

    public void kurangiStock(BahanMakanan bahanMakanan, int jumlah) {
        int currentStock = stockBahanMakanan.getOrDefault(bahanMakanan, 0);
        if (currentStock >= jumlah) {
            stockBahanMakanan.put(bahanMakanan, currentStock - jumlah);
        } else {
            System.out.println("Stock " + bahanMakanan.getName() + " tidak cukup");
        }
    }

    public void tambahStock(Masakan masakan, int jumlah) {
        int currentStock = stockMasakan.getOrDefault(masakan, 0);
        stockMasakan.put(masakan, currentStock + jumlah);
    }

    public void kurangiStock(Masakan masakan, int jumlah) {
        int currentStock = stockMasakan.getOrDefault(masakan, 0);
        if (currentStock >= jumlah) {
            stockMasakan.put(masakan, currentStock - jumlah);
        } else {
            System.out.println("Stock " + masakan.getNama() + " tidak cukup");
        }
    }

    public void tambahStock(Furniture furniture, int jumlah) {
        int currentStock = stockFurniture.getOrDefault(furniture, 0);
        stockFurniture.put(furniture, currentStock + jumlah);
    }

    public void kurangiStock(Furniture furniture, int jumlah) {
        int currentStock = stockFurniture.getOrDefault(furniture, 0);
        if (currentStock >= jumlah) {
            stockFurniture.put(furniture, currentStock - jumlah);
        } else {
            System.out.println("Stock " + furniture.getName() + " tidak cukup");
        }
    }

    public int getStock(BahanMakanan bahanMakanan) {
        return stockBahanMakanan.getOrDefault(bahanMakanan, 0);
    }

    public int getStock(Masakan masakan) {
        return stockMasakan.getOrDefault(masakan, 0);
    }

    public int getStock(Furniture furniture) {
        return stockFurniture.getOrDefault(furniture, 0);
    }
}