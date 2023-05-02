package Inventory;
import java.util.*;
import Sim.*;

public class Inventory<T> {
    private HashMap<T, Integer> stock;

    public Inventory() {
        stock = new HashMap<>();
    }

    public void tambahStock(T item, int jumlah) {
        int currentStock = stock.getOrDefault(item, 0);
        stock.put(item, currentStock + jumlah);
    }

    public void kurangiStock(T item, int jumlah) {
        int currentStock = stock.getOrDefault(item, 0);
        if (currentStock >= jumlah) {
            stock.put(item, currentStock - jumlah);
        } else {
            System.out.println("Stock " + item.toString() + " tidak cukup");
        }
    }

    public int getStock(T item) {
        return stock.getOrDefault(item, 0);
    }

    public HashMap<T, Integer> getStock() {
        return stock;
    }

    public void printInventory() {
        System.out.println("========== Inventory ==========");
        for (Map.Entry<T, Integer> entry : stock.entrySet()) {
            T item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(item.toString() + " : " + quantity);
        }
        System.out.println("================================");
    }
}
