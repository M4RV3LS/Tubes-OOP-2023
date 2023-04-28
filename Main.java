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
import java.io.IOException;
import Fitur.*;

public class Main {

    private static List<Sim> simList = new ArrayList<>();
    private Sim sim;

    

    // public void changeSim(String nama){
    //     for(Sim s : simList){
    //         if(s.getNamaLengkap().equals(nama)){
    //             this.sim = s;
    //             break;
    //         }
    //     }
    // }   
    
    public Sim getSim(){
        return this.sim;
    }
    public void print(String text){
        System.out.println(text);
    }

    public void exit(){
        System.out.println("Program telah di-close.");
            System.exit(0);
    }

    public static String getProgressBar(double percentage) {
        int width = 20;
        int progressChars = (int) (percentage * width);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < width; i++) {
            if (i < progressChars) {
                bar.append("=");
            } else {
                bar.append(" ");
            }
        }
        bar.append("]");
        return bar.toString();
    }
    
    public static void main(String[] args) throws InterruptedException{
        Main obj = new Main();       
        PrintASCII ascii = new PrintASCII();
        World world = World.getInstance();
        House house;
        Room room;
        Sim sim = obj.getSim();
        
        // Ascii Welcome
        ascii.printWelcome();
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        boolean started = false;
        boolean exit = false;
        
        //Loading Bar Features 
        int totalTasks = 10;
        for(int i = 0; i <= totalTasks; i++) {
            double progressPercentage = (double)i/totalTasks;
            String progressBar = getProgressBar(progressPercentage);
            System.out.print("\r" + progressBar + " " + Math.round(progressPercentage*100) + "%");
            Thread.sleep(1000);
        }

        // print the message and make it blink until user input
        while (!started) {
            System.out.print("\rPress Any Key to Start The Game...   ");
            Thread.sleep(500);
            System.out.print("\r                                     ");
            Thread.sleep(500);
            try {
                if (System.in.available() > 0) {
                    started = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // game logic goes here
        System.out.println("");
        System.out.println("Pilihlah Salah Satu Menu Dibawah ini: ");
        System.out.println("1. Start The Game");
        System.out.println("2. Help");
        System.out.println("3. Exit");

        scanner.nextLine();
        String menuInput = scanner.nextLine();
        
        if(menuInput.equals("1") || menuInput.equalsIgnoreCase("Start The Game")){
            
            System.out.println("Masukkan nama SIM: ");
            String nama = scanner.nextLine();
            Sim firstSim = new Sim(nama);
            simList.add(firstSim);
            sim = firstSim;
            System.out.println("Selamat datang " + nama + "!");
            System.out.println("Sekarang kamu tinggal di sebuah rumah yang sangat sederhana.");
            System.out.println("Kamu harus membangun rumah ini menjadi rumah yang indah.");
            System.out.println("Kamu juga harus membangun karirmu menjadi seorang yang sukses");
            System.out.println("");
    
            System.out.println("");
            for(int i=0; i<4; i++) {
                System.out.print("\rGenerating Sims Map . . . . . ");
                Thread.sleep(1000);
                System.out.print("\r                                 ");
                Thread.sleep(1000);
            }
            System.out.println("");
            house = sim.getHouse();
            room = house.getRoom("Living Room");
            room.printRoom();
            
            obj.print("");
            obj.print("Menu: ");
            obj.print("1.View Sim Info");
            obj.print("2.View Current Location");
            obj.print("3.View Inventory");
            obj.print("4.Upgrade House");
            obj.print("5.Move Room");
            obj.print("6.Edit Room");
            obj.print("7.Add Sim");
            obj.print("8.Change Sim");
            obj.print("9.List Object");
            obj.print("10.Go To Object");
            obj.print("11.Action");
            obj.print("12.Exit");
            obj.print("");
            obj.print("action: ");
            obj.print("13.Beli barang");
            obj.print("14.Memasang barang");

            while(!exit){
            System.out.print("Masukkan Angka atau Aksi yang diiginkan: ");
            menuInput = scanner.nextLine();

            if(menuInput.equals("1")|| menuInput.equalsIgnoreCase("View Sim Info")){
                sim.viewSimInfo();
            }
            else if(menuInput.equals("2")|| menuInput.equalsIgnoreCase("View Current Location")){
                
            }
            else if(menuInput.equals("3")|| menuInput.equalsIgnoreCase("View Inventory")){
                sim.printAllInventory();
            }
            else if(menuInput.equals("4")|| menuInput.equalsIgnoreCase("Upgrade House")){
                
            }
            else if(menuInput.equals("5")|| menuInput.equalsIgnoreCase("Move Room")){
                obj.print("Berikut ini adalah pilihan ruangan yang bisa anda kunjungi dirumah ini: ");
                for(Room houseRoom : house.getRooms()){
                    obj.print("- " + houseRoom.getRoomName());
                }
                obj.print("");
                System.out.print("Masukkan nama ruangan yang ingin anda kunjungi: ");
                menuInput = scanner.nextLine();
                room = house.getRoom(menuInput);
            }
            else if(menuInput.equals("6")|| menuInput.equalsIgnoreCase("Edit Room")){
                
            }
            else if(menuInput.equals("7")|| menuInput.equalsIgnoreCase("Add Sim")){
                System.out.println("Masukkan nama SIM: ");
                nama = scanner.nextLine();
                Sim newSim = new Sim(nama);
                simList.add(newSim);
                System.out.println("Objek SIM " + nama + " berhasil ditambahkan ke dalam list!");
                // for (Sim daftarSim : simList) {
                //     System.out.println("- " + daftarSim.getNamaLengkap());
                // }
            }
            else if(menuInput.equals("8")|| menuInput.equalsIgnoreCase("Change Sim")){
                if (simList.isEmpty()) {
                    System.out.println("Tidak ada SIM yang tersedia.");
                } else {
                    System.out.println("Berikut adalah SIM yang tersedia:");
                for (Sim daftarSim : simList) {
                    System.out.println("- " + daftarSim.getNamaLengkap());
                }
                System.out.println("Masukkan nama SIM yang ingin anda gunakan : ");
                nama = scanner.nextLine();
                for(Sim s : simList){
                     if(s.getNamaLengkap().equals(nama)){
                         sim = s;
                         house = sim.getHouse();
                         room = house.getRoom("Living Room");
                     }
                 }
                }
            }
            else if(menuInput.equals("9")|| menuInput.equalsIgnoreCase("List Object")){
                obj.print("Berikut ini adalah daftar objek yang berada didalam ruangan ini : ");
                room.printObjectCounts();
            }
            else if(menuInput.equals("10")|| menuInput.equalsIgnoreCase("Go To Object")){
                
            }
            else if(menuInput.equals("11")|| menuInput.equalsIgnoreCase("Action")){
                obj.print("Berikut ini adalah beberapa aksi yang bisa dilakukan oleh SIM : ");
                obj.print("1. Kerja");
                obj.print("2. Olahraga");
                obj.print("3. Tidur");
                obj.print("4. Makan");
                obj.print("5. Memasak");
                obj.print("6. Berkunjung");
                obj.print("7. Buang Air");
                obj.print("8. Duduk");
                obj.print("9. Melihat Waktu");
                
                
                Boolean action = false;
                while(!action){
                    System.out.print("Masukkan aksi yang ingin dilakukan: ");
                    menuInput = scanner.nextLine();
                    if(menuInput.equals("1")|| menuInput.equalsIgnoreCase("Kerja")){
                        action = true;
                    }
                    else if(menuInput.equals("2")|| menuInput.equalsIgnoreCase("Olahraga")){
                        action = true;
                    }
                    else if(menuInput.equals("3")|| menuInput.equalsIgnoreCase("Tidur")){
                        System.out.println("Kunjungi Objek Kasur apapun di Suatu Ruangan !");
                        action = true;
                    }
                    else if(menuInput.equals("4")|| menuInput.equalsIgnoreCase("Makan")){
                        System.out.println("Kunjungi Objek Meja dan Kursi di Suatu Ruangan !");
                        action = true;
                    }
                    else if(menuInput.equals("5")|| menuInput.equalsIgnoreCase("Memasak")){
                        System.out.println("Kunjungi Objek Kompor Gas atau Listrik di Suatu Ruangan !");
                        action = true;
                    }
                    else if(menuInput.equals("6")|| menuInput.equalsIgnoreCase("Berkunjung")){
                        action = true;
                    }
                    else if(menuInput.equals("7")|| menuInput.equalsIgnoreCase("Buang Air")){
                        System.out.println("Kunjungi Objek Toilet di Suatu Ruangan !");
                        action = true;
                    }
                    else if(menuInput.equals("8")|| menuInput.equalsIgnoreCase("Duduk")){
                        action = true;
                    }
                    else if(menuInput.equals("9")|| menuInput.equalsIgnoreCase("Melihat Waktu")){
                        System.out.println("Kunjungi Objek Jam di Suatu Ruangan !");
                        action = true;
                    }
                    else{
                        System.out.println("Aksi yang anda masukkan tidak tersedia!");
                    }
                }
            }
            else if(menuInput.equals("12")|| menuInput.equalsIgnoreCase("Exit")){
                obj.exit();
            }
            else if(menuInput.equals("13")|| menuInput.equalsIgnoreCase("Beli barang")){
                ascii.printShop();
                Shop shop = new Shop(sim);
                Inventory<BahanMakanan> inventoryBahanMakanan = sim.getInventoryBahanMakanan();
                Inventory<Furniture> inventoryFurniture = sim.getInventoryFurniture();
                Inventory<Masakan> inventoryMasakan = sim.getInventoryMasakan();

                Scanner input = new Scanner(System.in);

            while (true) {
                System.out.println("Selamat datang di The Sims Shop!");
                System.out.println("1. Melihat Shop");
                System.out.println("2. Melihat Inventory");
                System.out.print("Masukkan pilihan: ");
                String menuChoice = input.nextLine();

                if (menuChoice.equals("1") || menuChoice.equalsIgnoreCase("Melihat Shop")) {
                    while (true) {
                        System.out.println("1. Melihat Daftar Furniture");
                        System.out.println("2. Melihat Daftar Bahan Makanan");
                        System.out.print("Masukkan pilihan: ");
                        String shopChoice = input.nextLine();

                        if (shopChoice.equals("1") || shopChoice.equalsIgnoreCase("Melihat Daftar Furniture")) {
                            shop.showFurniture();
                            while (true) {
                                System.out.print("Masukkan nama furniture yang ingin dibeli: ");
                                String itemName = input.nextLine();

                                Furniture furniture = null;
                                for (Furniture item : Furniture.values()) {
                                    if (item.getName().equalsIgnoreCase(itemName)) {
                                        furniture = item;
                                        break;
                                    }
                                }

                                if (furniture != null) {
                                    System.out.print("Masukkan jumlah furniture yang ingin dibeli: ");
                                    int quantity = input.nextInt();
                                    input.nextLine();
                                    shop.buyFurniture(furniture, quantity, sim);
                                } else {
                                    System.out.println("Furniture tidak ditemukan.");
                                }

                                System.out.println();
                                System.out.print("Apakah Anda ingin membeli barang lagi? (y/n): ");
                                String choice = input.nextLine();

                                if (choice.equalsIgnoreCase("n")) {
                                    break;
                                }
                            }
                            break;

                        } else if (shopChoice.equals("2") || shopChoice.equalsIgnoreCase("Melihat Daftar Bahan Makanan")) {
                            shop.showBahanMakanan();
                            while (true) {
                                System.out.print("Masukkan nama bahan makanan yang ingin dibeli: ");
                                String itemName = input.nextLine();

                                BahanMakanan bahanMakanan = null;
                                for (BahanMakanan item : BahanMakanan.values()) {
                                    if (item.getName().equalsIgnoreCase(itemName)) {
                                        bahanMakanan = item;
                                        break;
                                    }
                                }

                                if (bahanMakanan != null) {
                                    System.out.print("Masukkan jumlah bahan makanan yang ingin dibeli: ");
                                    int quantity = input.nextInt();
                                    input.nextLine();
                                    shop.buyBahanMakanan(bahanMakanan, quantity, sim);
                                } else {
                                    System.out.println("Bahan makanan tidak ditemukan.");
                                }

                                System.out.println();
                                System.out.print("Apakah Anda ingin membeli barang lagi? (y/n): ");
                                String choice = input.nextLine();

                                if (choice.equalsIgnoreCase("n")) {
                                    break;
                                }
                            }
                            break;

                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                    }

                    } else if (menuChoice.equals("2") || menuChoice.equalsIgnoreCase("Melihat Inventory")) {
                        System.out.println("1. Melihat Inventory Bahan Makanan");
                        System.out.println("2. Melihat Inventory Furniture");
                        System.out.println("3. Melihat Inventory Masakan");
                        System.out.println("4. Melihat Semua Inventory");
                        System.out.print("Masukkan pilihan: ");
                        String inventoryChoice = input.nextLine();

                        if (inventoryChoice.equals("1") || inventoryChoice.equalsIgnoreCase("Melihat Inventory Bahan Makanan")) {
                            inventoryBahanMakanan.printInventory();
                        } else if (inventoryChoice.equals("2") || inventoryChoice.equalsIgnoreCase("Melihat Inventory Furniture")) {
                            inventoryFurniture.printInventory();
                        } else if (inventoryChoice.equals("3") || inventoryChoice.equalsIgnoreCase("Melihat Inventory Masakan")) {
                            inventoryMasakan.printInventory();
                        } 
                        else if(inventoryChoice.equals("4") || inventoryChoice.equalsIgnoreCase("Melihat Semua Inventory")){
                            sim.printAllInventory();
                        }
                        else {
                            System.out.println("Pilihan tidak valid.");
                        }

                    } else {
                        System.out.println("Input tidak valid.");
                    }

                    System.out.println();
                    System.out.print("Apakah Anda ingin melakukan transaksi lain? (y/n): ");
                    String transaksiChoice = input.nextLine();

                    if (transaksiChoice.equalsIgnoreCase("n")) {
                        break;
                    }
                }
            }
            else if(menuInput.equals("14")|| menuInput.equalsIgnoreCase("Memasang barang")){
                obj.print("Berikut ini adalah list barang anda: ");
                sim.getInventoryFurniture().printInventory();
                System.out.print("Masukkan nama Furniture: ");
                String input = scanner.nextLine();
                System.out.print("Masukkan Koordinat X : ");
                int x = scanner.nextInt();
                System.out.print("Masukkan Koordinat Y : ");
                int y = scanner.nextInt();
                System.out.print("Apakah barang ingin diletakkan secara horizontal ?");
                scanner.nextLine();
                menuInput = scanner.nextLine();
                Boolean horizontal = true;
                if(menuInput.equalsIgnoreCase("Y")){
                    horizontal = true;
                }
                else if(menuInput.equalsIgnoreCase("N")){
                    horizontal = false;
                }
                else{
                    System.out.println("Input tidak valid.");
                }
                // if(horizontal){
                //     System.out.println("Horizontal");
                // }
                // else{
                //     System.out.println("Vertical");
                // }

                try {
                    Boolean valid;
                    Furniture furniture = Furniture.valueOf(input.toUpperCase());
                    valid = room.placeFurniture(x , y , furniture.getDimensi() , furniture.getNamaInisial() , horizontal);
                    if(valid){
                        sim.getInventoryFurniture().kurangiStock(furniture,1);
                        MyObject myObject = new MyObject(furniture.getName()); // membuat objek MyObject
                        int jumlahBarang = 1; // jumlah barang Furniture yang ingin ditambahkan

                        // Memasukkan objek MyObject dan jumlah barang ke dalam HashMap objectCounts
                        if (room.getObjectCounts().containsKey(myObject)) { // jika objek MyObject sudah ada di HashMap
                            int currentCount = room.getObjectCounts().get(myObject); // ambil nilai jumlah barang saat ini
                            room.getObjectCounts().put(myObject, currentCount + jumlahBarang); // tambahkan jumlah barang yang baru ke nilai yang sudah ada
                        } else { // jika objek MyObject belum ada di HashMap
                            room.getObjectCounts().put(myObject, jumlahBarang); // tambahkan objek MyObject dan jumlah barang ke HashMap
                        }
                        System.out.println("Furniture berhasil diletakkan di koordinat (" + x + "," + y + ")");
                    }
                    else{
                        System.out.println("Tidak dapat meletakkan furniture di koordinat (" + x + "," + y + ")");
                    }
                    room.printRoom();
                } catch (IllegalArgumentException e) {
                    System.out.println("Furniture tidak ditemukan");
                }
                catch (Exception e) {
                    System.out.println("Terjadi kesalahan: " + e.getMessage());
                }
            }
            else{
                System.out.println("Input tidak valid.");
            }
            
            }
        }
        else if(menuInput.equals("2") || menuInput.equalsIgnoreCase("Help")){

        }

        else if(menuInput.equals("3") || menuInput.equalsIgnoreCase("Exit")){
            obj.exit();
        }

        else{
            System.out.println("Input tidak valid.");
        }

        // //Membuat Objek Baru 
        // Scanner scanner = new Scanner(System.in);
        // String input = "";
        // while (!input.equalsIgnoreCase("exit")) {
        //     System.out.println("Apakah anda ingin membuat objek SIM baru? (ya/tidak)");
        //     input = scanner.nextLine();
        //     if (input.equalsIgnoreCase("ya")) {
        //         System.out.println("Masukkan nama SIM: ");
        //         String nama = scanner.nextLine();
        //         Sim sim = new Sim(nama);
        //         simList.add(sim);
        //         System.out.println("Objek SIM " + nama + " berhasil ditambahkan ke dalam list!");
        //     }
        //     else if(input.equalsIgnoreCase("exit")){
        //         scanner.close();
        //         obj.exit();
        //     }
        //     else{
        //         System.out.println("Input tidak valid.");
        //     }

        //     //Mencari Informasi Sim
        //     System.out.println("Apakah anda ingin melihat informasi SIM? (ya/tidak)");
        //     input = scanner.nextLine();
        //     if (input.equalsIgnoreCase("ya")) {
        //         if (simList.isEmpty()) {
        //             System.out.println("Tidak ada SIM yang tersedia.");
        //         } else {
        //             System.out.println("Berikut adalah SIM yang tersedia:");
        //         for (Sim sim : simList) {
        //             System.out.println("- " + sim.getNamaLengkap());
        //         }
                
        //         System.out.println("Masukkan nama SIM yang ingin anda lihat informasinya : ");
        //         String nama = scanner.nextLine();
        //         Sim sim = null;
        //         for(Sim s : simList){
        //             if(s.getNamaLengkap().equals(nama)){
        //                 sim = s;
        //                 break;
        //             }
        //         }
        //         if(sim != null){
        //             System.out.println("Informasi detail SIM " + sim.getNamaLengkap() + ":");
        //             System.out.println("Pekerjaan: " + sim.getPekerjaan().toString());
        //             System.out.println("Uang: " + sim.getUang());
        //             System.out.println("Kekenyangan: " + sim.getKekenyangan());
        //             System.out.println("Mood: " + sim.getMood());
        //             System.out.println("Kesehatan: " + sim.getKesehatan());
        //             System.out.println("Status: " + sim.getStatus());
        //             System.out.println("House: " + sim.getHouse().getHouseName());
        //             System.out.println("Inventory: ");
        //             sim.printAllInventory();
        //             // List<InventoryItem> items = sim.getInventory().getItems();
        //             // for (InventoryItem item : items) {
        //             //     System.out.println("- " + item.getItem().toString() + ": " + item.getQuantity());
        //             // }
        //             System.out.println("");
        //             System.out.println("Berikut ini adalah peta dunia sekarang");
        //             world.printWorld();
        //             System.out.println("Jumlah rumah sekarang adalah : " + world.getHouseList().size());
        //         } else {
        //             System.out.println("SIM dengan nama " + nama + " tidak ditemukan dalam list!");
        //         }
        //         }
        //     }
        //     else if(input.equalsIgnoreCase("exit")){
        //         scanner.close();
        //         obj.exit();
        //             }
        //     else{
        //         System.out.println("Input tidak valid.");
        //     }
        // }


}
}
