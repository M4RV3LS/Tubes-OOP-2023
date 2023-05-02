package Fitur;

import Inventory.*;
import Sim.*;
import java.util.*;
import Map.*;

public class Shop {
    private static Shop instance = null;
    private Sim sim;
    private static World world = World.getInstance();


    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
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
            Random random = new Random();
            int randomNumber = random.nextInt(5) + 1;
            DeliveryItem<Furniture> deliveryFurniture = new DeliveryItem<Furniture>(sim,randomNumber * 30, furniture, furniture.getName() , quantity);
            world.addDeliveryItemFurniture(deliveryFurniture);
            sim.setUang(sim.getUang() - totalPrice);
            System.out.println("Berhasil memesan " + quantity + " " + furniture.getName());
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
            Random random = new Random();
            int randomNumber = random.nextInt(5) + 1;
            DeliveryItem<BahanMakanan> deliveryBahanMakanan = new DeliveryItem<BahanMakanan>(sim,randomNumber * 30, bahanMakanan, bahanMakanan.getName() , quantity);
            world.addDeliveryItemBahanMakanan(deliveryBahanMakanan);
            sim.setUang(sim.getUang() - totalPrice);
            System.out.println("Berhasil membeli " + quantity + " " + bahanMakanan.getName());
        } else {
            System.out.println("Maaf uang anda tidak mencukupi untuk membeli objek ini");
        }
    }
}
