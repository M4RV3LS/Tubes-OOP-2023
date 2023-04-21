import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Map.*;
import java.util.*;
import Inventory.*;
import Sim.*;
public class Main {

    private static List<Sim> simList = new ArrayList<>();


    

    public void exit(){
        System.out.println("Program telah di-close.");
            System.exit(0);
    }
    
    public static void main(String[] args){
        PrintASCII ascii = new PrintASCII();
        Main obj = new Main();
        ascii.printWelcome();

        //Membuat Objek Baru 
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equalsIgnoreCase("exit")) {
            System.out.println("Apakah anda ingin membuat objek SIM baru? (ya/tidak)");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("ya")) {
                System.out.println("Masukkan nama SIM: ");
                String nama = scanner.nextLine();
                Sim sim = new Sim(nama);
                simList.add(sim);
                System.out.println("Objek SIM " + nama + " berhasil ditambahkan ke dalam list!");
            }
            else if(input.equalsIgnoreCase("exit")){
                scanner.close();
                obj.exit();
            }
            else{
                System.out.println("Input tidak valid.");
            }

            //Mencari Informasi Sim
            System.out.println("Apakah anda ingin melihat informasi SIM? (ya/tidak)");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("ya")) {
                if (simList.isEmpty()) {
                    System.out.println("Tidak ada SIM yang tersedia.");
                } else {
                    System.out.println("Berikut adalah SIM yang tersedia:");
                for (Sim sim : simList) {
                    System.out.println("- " + sim.getNamaLengkap());
                }
                
                System.out.println("Masukkan nama SIM yang ingin anda lihat informasinya : ");
                String nama = scanner.nextLine();
                Sim sim = null;
                for(Sim s : simList){
                    if(s.getNamaLengkap().equals(nama)){
                        sim = s;
                        break;
                    }
                }
                if(sim != null){
                    System.out.println("Informasi detail SIM " + sim.getNamaLengkap() + ":");
                    System.out.println("Pekerjaan: " + sim.getPekerjaan().toString());
                    System.out.println("Uang: " + sim.getUang());
                    System.out.println("Kekenyangan: " + sim.getKekenyangan());
                    System.out.println("Mood: " + sim.getMood());
                    System.out.println("Kesehatan: " + sim.getKesehatan());
                    System.out.println("Status: " + sim.getStatus());
                    System.out.println("Inventory: ");
                    // List<InventoryItem> items = sim.getInventory().getItems();
                    // for (InventoryItem item : items) {
                    //     System.out.println("- " + item.getItem().toString() + ": " + item.getQuantity());
                    // }
                } else {
                    System.out.println("SIM dengan nama " + nama + " tidak ditemukan dalam list!");
                }
                }
            }
            else if(input.equalsIgnoreCase("exit")){
                scanner.close();
                obj.exit();
                    }
            else{
                System.out.println("Input tidak valid.");
            }
        }
}
}
