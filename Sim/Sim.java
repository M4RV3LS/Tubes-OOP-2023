package Sim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import Inventory.*;
import Map.*;

public class Sim {
    private String namaLengkap;
    // private String pekerjaan;
    private Job pekerjaan;
    private int uang;
    private Inventory<BahanMakanan> inventoryBahanMakanan;
    private Inventory<Furniture> inventoryFurniture;
    private Inventory<Masakan> inventoryMasakan;
    private int kekenyangan;
    private int mood;
    private int kesehatan;
    private String status;
    private House house;

    public Sim(String namaLengkap) {
        this.namaLengkap = namaLengkap;
        this.uang = 100;
        this.kekenyangan = 80;
        this.mood = 80;
        this.kesehatan = 80;
        this.inventoryBahanMakanan = new Inventory<>();
        this.inventoryFurniture = new Inventory<>();
        //Menambah Objek yang wajib ada saat pertama kali objek Sim di instantiasi
        for (Furniture furniture : Furniture.values()) {
            inventoryFurniture.tambahStock(furniture,1);
        }
        this.inventoryMasakan = new Inventory<>();
        this.status = "Tidak melakukan apa-apa";
        // this.pekerjaan = getRandomPekerjaan();
        // this.job = Job.valueOf(pekerjaan.toUpperCase().replace(" ", "_"));
        this.pekerjaan = getRandomJob();
        this.house = generateRandomHouse();
    }

    //Menggenerate Pekerjaan secara random 
    private Job getRandomJob() {
        Job[] jobs = Job.values();
        Random random = new Random();
        return jobs[random.nextInt(jobs.length)];
    }

    //Menggenerate House secara random
    private House generateRandomHouse() {
        Random rand = new Random();
        World world = World.getInstance();
        int x, y;
        do {
            x = rand.nextInt(64);
            y = rand.nextInt(64);
        } while (world.getHouse(x, y) != null);
        House house = new House("H" + String.format("%04d",world.getHouseList().size() + 1) + " ");
        world.setHouse(x, y, house);
        return house;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public Job getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(Job pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public int getUang() {
        return uang;
    }

    public void setUang(int uang) {
        this.uang = uang;
    }

    // public Inventory getInventory() {
    //     return inventory;
    // }

    // public void setInventory(Inventory inventory) {
    //         this.inventory = inventory;
    //     }

    public Inventory<BahanMakanan> getInventoryBahanMakanan() {
        return inventoryBahanMakanan;
    }

    public void setInventoryBahanMakanan(Inventory<BahanMakanan> inventoryBahanMakanan) {
        this.inventoryBahanMakanan = inventoryBahanMakanan;
    }

    public Inventory<Furniture> getInventoryFurniture() {
        return inventoryFurniture;
    }

    public void setInventoryFurniture(Inventory<Furniture> inventoryFurniture) {
        this.inventoryFurniture = inventoryFurniture;
    }

    public Inventory<Masakan> getInventoryMasakan() {
        return inventoryMasakan;
    }

    public void setInventoryMasakan(Inventory<Masakan> inventoryMasakan) {
        this.inventoryMasakan = inventoryMasakan;
    }

    // Print all Inventory
    public void printAllInventory() {
        System.out.println("Inventory Bahan Makanan");
        this.inventoryBahanMakanan.printInventory();
        System.out.println("");
        System.out.println("Inventory Furniture");
        this.inventoryFurniture.printInventory();
        System.out.println("");
        System.out.println("Inventory Masakan");
        this.inventoryMasakan.printInventory();
        
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getKesehatan() {
        return kesehatan;
    }

    public void setKesehatan(int kesehatan) {
        this.kesehatan = kesehatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // private String getRandomPekerjaan() {
    //     // ArrayList<String> pekerjaanList = new ArrayList<>(Arrays.asList("Guru", "Dokter", "Pengacara", "Programmer", "Pengusaha", "Arsitek", "Petani"));
    //     ArrayList<String> pekerjaanList = this.job;
    //     Random rand = new Random();
    //     int index = rand.nextInt(this.job.size());
    //     return pekerjaanList.get(index);
    // }

    public House getHouse(){
        return this.house;
    }
    
    public void setHouse(House house){
        this.house = house;
    }

    public void viewSimInfo() {
        System.out.println("Nama Lengkap: " + namaLengkap);
        System.out.println("Pekerjaan: " + pekerjaan);
        System.out.println("Uang: " + uang);
        System.out.println("Kekenyangan: " + kekenyangan);
        System.out.println("Mood: " + mood);
        System.out.println("Kesehatan: " + kesehatan);
        System.out.println("Status: " + status);
        System.out.println("Rumah: " + house.getHouseName());
    }
    
}
