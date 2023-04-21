package Fitur;

import Inventory.*;
import Sim.*;
import java.util.*;

public class Shop {
    private Sim sim;

    public Shop(Sim sim){
        this.sim = sim;
    }
    
    public static void showFurniture() {
        System.out.println("List Furniture yang Dijual:");
        for (Furniture furniture : Furniture.values()) {
            System.out.println("Nama: " + furniture.getName() +
                               ", Harga: " + furniture.getHarga());
            System.out.println();
        }
    }
    
    public static void buyFurniture(Furniture furniture, int quantity, Sim sim) {
        int totalPrice = furniture.getHarga() * quantity;
        // if (sim.getUang() >= totalPrice) {
        //     if (sim.getInventory().getStockFurniture().containsKey(furniture)) {
        //         sim.getInventory().getStockFurniture().put(furniture, sim.getInventory().getStockFurniture().get(furniture) + quantity);
        //     } else {
        //         sim.getInventory().getStockFurniture().put(furniture, quantity);
        //     }
        //     sim.setUang(sim.getUang() - totalPrice);
        //     System.out.println("Berhasil membeli " + quantity + " " + furniture.getName());
        // } else {
        //     System.out.println("Maaf uang anda tidak mencukupi untuk membeli objek ini");
        // }
        if (sim.getUang() >= totalPrice) {
            
            sim.getInventory().tambahStock(furniture, quantity);
            
            sim.setUang(sim.getUang() - totalPrice);
            System.out.println("Berhasil membeli " + quantity + " " + furniture.getName());
        } else {
            System.out.println("Maaf uang anda tidak mencukupi untuk membeli objek ini");
        }
    }

    public static void showBahanMakanan() {
        System.out.println("List Bahan Makanan yang Dijual:");
        for (BahanMakanan bahanMakanan : BahanMakanan.values()) {
            System.out.println("Nama: " + bahanMakanan.getName() +
                               ", Harga: " + bahanMakanan.getHarga());
            System.out.println();
        }
    }
    
    public static void buyBahanMakanan(BahanMakanan bahanMakanan, int quantity, Sim sim) {
        int totalPrice = bahanMakanan.getHarga() * quantity;
        // if (sim.getUang() >= totalPrice) {
        //     if (sim.getInventory().getStockFurniture().containsKey(furniture)) {
        //         sim.getInventory().getStockFurniture().put(furniture, sim.getInventory().getStockFurniture().get(furniture) + quantity);
        //     } else {
        //         sim.getInventory().getStockFurniture().put(furniture, quantity);
        //     }
        //     sim.setUang(sim.getUang() - totalPrice);
        //     System.out.println("Berhasil membeli " + quantity + " " + furniture.getName());
        // } else {
        //     System.out.println("Maaf uang anda tidak mencukupi untuk membeli objek ini");
        // }
        if (sim.getUang() >= totalPrice) {
            sim.getInventory().tambahStock(bahanMakanan, quantity);
            sim.setUang(sim.getUang() - totalPrice);
            System.out.println("Berhasil membeli " + quantity + " " + bahanMakanan.getName());
        } else {
            System.out.println("Maaf uang anda tidak mencukupi untuk membeli objek ini");
        }
    }
}
