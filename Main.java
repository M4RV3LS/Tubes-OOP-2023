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

    private Sim sim;
    private World world;
    

    // public void changeSim(String nama){
    //     for(Sim s : world.simList){
    //         if(s.getNamaLengkap().equals(nama)){
    //             this.sim = s;
    //             break;
    //         }
    //     }
    // }   
    
    public Sim getSim(){
        return this.sim;
    }

    public void help(){
        System.out.println("-Help-");
        System.out.println("Sim-Plicity adalah permainan simulasi kehidupan yang berbasis Command Line Interface (CLI).");
        System.out.println("Pemain akan mengendalikan sejumlah sim yang harus dijaga kesejahteraannya agar tidak depresi dan mati.");
        System.out.println("Pemain harus memenuhi kebutuhan mood, kekenyangan, dan kesehatan dari setiap sim untuk menjaga kesejahteraannya.");
        System.out.println("Jumlah sim yang dapat dimainkan tergantung dari berapa sim yang dibuat pada awal permainan.");
        System.out.println();
        System.out.println("Aturan Permainan");
        System.out.println("1. Masukkan jumlah sim yang ingin dimainkan, kemudian masukkan nama-nama sim yang ingin dibuat");
        System.out.println("2. Setiap sim akan memiliki kebutuhan berupa mood, kekenyangan, dan kesehatan");
        System.out.println("3. Pemain harus memenuhi kebutuhan tersebut dengan memberi perintah yang tepat melalui CLI");
        System.out.println("4. Pemain dapat memberikan perintah untuk memberi makan, minum, membersihkan, merawat kesehatan, atau memberikan hiburan kepada setiap sim");
        System.out.println("5. Pemain harus memperhatikan level kebutuhan setiap sim dan segera memberikan perintah yang tepat untuk menghindari kematian atau depresi pada sim");
        System.out.println("6. Permainan berakhir jika semua sim meninggal atau depresi");
        System.out.println();
        System.out.println("Selamat bermain Sim-Plicity!");
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

    public void printMenu(){
            System.out.println("");
            System.out.println("Menu: ");
            System.out.println("1.View Sim Info");
            System.out.println("2.View Current Location");
            System.out.println("3.View Inventory");
            System.out.println("4.Upgrade House");
            System.out.println("5.Move Room");
            System.out.println("6.Edit Room");
            System.out.println("7.Add Sim");
            System.out.println("8.Change Sim");
            System.out.println("9.List Object");
            System.out.println("10.Go To Object");
            System.out.println("11.Action");
            System.out.println("12.Exit");
            System.out.println("13.Beli barang");
            System.out.println("14.Print Room");
            System.out.println("15.Print Map");
            System.out.println("16.Lihat Waktu");
    }

    public void addSim(){
        Scanner scanner = new Scanner(System.in);
        boolean isExists = true;
        Sim newSim;
        while (isExists) {
            System.out.println("Masukkan nama SIM: ");
            String nama = scanner.nextLine();
            newSim = new Sim(nama);
            if (!world.findSim(newSim.getNamaLengkap())) {
                isExists = false;
                world.addSim(newSim);
                world.addWaktuTidakTidur(newSim, 600);
                System.out.println("Objek SIM " + nama + " berhasil ditambahkan ke dalam list!");
            } else {
                System.out.println("Objek SIM " + nama + " sudah ada di dalam list, silahkan masukkan nama lain!");
            }
        }
    }

    public void changeSim(Sim sim , House house , Room room){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Berikut adalah SIM yang tersedia:");
        for ( Sim daftarSim : world.getSimList()) {
            System.out.println("- " + daftarSim.getNamaLengkap());
        }
        System.out.println("Masukkan nama SIM yang ingin anda gunakan : ");
        Boolean findSim = false;
        while(!findSim){
            String nama = scanner.nextLine();
            for(Sim s : world.getSimList()){
                if(s.getNamaLengkap().equalsIgnoreCase(nama)){
                    sim = s;
                    house = sim.getHouse();
                    room = house.getRoom("Living Room");
                }
            }
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException{
        Main obj = new Main();       
        PrintASCII ascii = new PrintASCII();
        World world = World.getInstance();
        House house;
        Room room;
        FurnitureData furnitureData;
        Shop shop = Shop.getInstance();
        Sim sim = obj.getSim();
        
        // Ascii Welcome
        ascii.printTitle();
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        boolean started = false;
        boolean exit = false;
        
        //Loading Bar Features 
        // int totalTasks = 10;
        // for(int i = 0; i <= totalTasks; i++) {
        //     double progressPercentage = (double)i/totalTasks;
        //     String progressBar = getProgressBar(progressPercentage);
        //     System.out.print("\r" + progressBar + " " + Math.round(progressPercentage*100) + "%");
        //     Thread.sleep(1000);
        // }

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
            world.addSim(firstSim);
            world.addWaktuTidakTidur(firstSim , 600);
            // world.addWaktuTidakBuangAir(firstSim , 0);
            sim = firstSim;
            System.out.println("Selamat datang " + nama + "!");
            System.out.println("Sekarang kamu tinggal di sebuah rumah yang sangat sederhana.");
            System.out.println("Kamu harus membangun rumah ini menjadi rumah yang indah.");
            System.out.println("Kamu juga harus membangun karirmu menjadi seorang yang sukses");
            System.out.println("");
    
            System.out.println("");
            // for(int i=0; i<4; i++) {
            //     System.out.print("\rGenerating Sims Map . . . . . ");
            //     Thread.sleep(1000);
            //     System.out.print("\r                                 ");
            //     Thread.sleep(1000);
            // }
            System.out.println("");
            house = sim.getHouse();
            room = house.getRoom("Living Room");
            room.printRoom();
            
            obj.printMenu();

            while(!exit){
            world.checkIsDead(); //Ini harusnya dimasukkin ke method Sim jadi setiap aksi akan ngecek Sim nya mati atau ngga 
            if(sim.isDead()){
                obj.print("Sim kamu telah Mati !!!");
                obj.print("Silahkan gunakan Sim yang lain");
                if (world.getSimList().isEmpty()) {
                    if(!(world.getIsAddSim())){
                        obj.addSim();
                        obj.changeSim(sim , house , room);
                        world.setIsAddSim(true);
                    }
                    else{
                        obj.print("Tidak ada Sim yang tersedia");
                        obj.print("Game Over");
                        obj.exit();
                    }
                }
                else {
                    obj.changeSim(sim , house , room);
                }
            }
            obj.print("");
            obj.print("Masukkan Help atau Menu untuk menampilkan menu permainan");
            System.out.print("Masukkan Angka atau Aksi yang diiginkan: ");
            menuInput = scanner.nextLine();

            if(menuInput.equals("1")|| menuInput.equalsIgnoreCase("View Sim Info")){
                sim.viewSimInfo();
            }
            else if(menuInput.equals("2")|| menuInput.equalsIgnoreCase("View Current Location")){
                sim.lokasiSIM();
            }
            else if(menuInput.equals("3")|| menuInput.equalsIgnoreCase("View Inventory")){
                sim.printAllInventory();
            }
            else if(menuInput.equals("4")|| menuInput.equalsIgnoreCase("Upgrade House")){
                if(sim.getOwnHouse().getHouseName().equalsIgnoreCase(sim.getHouse().getHouseName())){
                    //Print semua daftar Room yang ada dirumahnya
                    obj.print("Berikut ini adalah ruangan yang sudah ada dirumah ini: ");
                    for(Room houseRoom : sim.getHouse().getRooms()){
                        obj.print("- " + houseRoom.getRoomName());
                    }
                    Boolean existRoom = true;
                    while(existRoom){
                        System.out.print("Masukkan Nama Ruangan yang ingin anda tambahkan : ");
                        menuInput = scanner.nextLine();
                        if(sim.getHouse().isRoomExist(menuInput)){
                            obj.print("Ruangan sudah ada");
                        }
                        else{
                            existRoom = false;
                        }
                    }
                    world.upgradeHouse(sim, menuInput, sim.getIsInHouse());
                    obj.print("");
                    world.printAllUpgradeHouse();
                }
                
            }
            else if(menuInput.equals("5")|| menuInput.equalsIgnoreCase("Move Room")){
                obj.print("Berikut ini adalah pilihan ruangan yang bisa anda kunjungi dirumah ini: ");
                // for(Room houseRoom : house.getRooms()){
                //     obj.print("- " + houseRoom.getRoomName());
                // }
                house.printRooms();
                obj.print("");
                System.out.print("Masukkan ruangan yang ingin anda kunjungi: ");
                menuInput = scanner.nextLine();
                for (int i = 0; i < house.getRooms().size(); i++) {
                    if (house.getRooms().get(i).getRoomName().equals(menuInput) || (menuInput.matches("\\d+") && Integer.parseInt(menuInput) == i + 1) ) {
                        room = house.getRooms().get(i);
                        sim.setRoom(room);
                }
            }
            }
            else if(menuInput.equals("6")|| menuInput.equalsIgnoreCase("Edit Room")){
                //mengecek apakah Sim sedang berada di rumahnya 
                if(sim.getIsInHouse()){
                    System.out.println("Pilih aksi:");
                    System.out.println("1. Place Furniture");
                    System.out.println("2. Edit Furniture");
                    System.out.println("3. Remove Furniture");
                    System.out.print("Masukkan Pilihan Anda : ");
                    menuInput = scanner.nextLine();
                    if (menuInput.equals("1") || menuInput.equalsIgnoreCase("Place Furniture")) {
                    obj.print("Berikut ini adalah list barang anda: ");
                    sim.getInventoryFurniture().printInventory();
                    System.out.print("Masukkan nama Furniture: ");
                    String input = scanner.nextLine();
                    System.out.print("Masukkan Koordinat X : ");
                    int x = scanner.nextInt();
                    System.out.print("Masukkan Koordinat Y : ");
                    int y = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukan Arah Letak Benda (Right/Left/Up/Down) :");
                    String input2 = scanner.nextLine();
                    // Boolean right = false;
                    // Boolean left = false;
                    // Boolean up = false;
                    // Boolean down = false;
                    // if(input2.equalsIgnoreCase("right")){
                    //     right = true;
                    // }
                    // else if(input2.equalsIgnoreCase("left")){
                    //     left = true;
                    // }
                    // else if(input2.equalsIgnoreCase("up")){
                    //     up = true;
                    // }
                    // else if(input2.equalsIgnoreCase("down")){
                    //     down = true;
                    // }
                    // else{
                    //     System.out.println("Input Salah");
                    // }


                    // if(input2.equalsIgnoreCase("y")){
                    //     horizontal = true;
                    // }
                    // else if(input2.equalsIgnoreCase("n")){
                    //     horizontal = false;
                    // }
                    // else{
                    //     System.out.println("Input Salah");
                    // }
                    // System.out.print("Masukan Arah Letak Benda (Right/Left/Up/Down) :");
                    // menuInput = scanner.nextLine().trim();
                    // String direction = menuInput;
                    // if(menuInput.equalsIgnoreCase("Left")){
                    //     direction = "Left";
                    // }
                    // else if(menuInput.equalsIgnoreCase("Right")){
                    //     direction = "Right";
                    // }
                    // else if(menuInput.equalsIgnoreCase("Up")){
                    //     direction = "Up";
                    // }
                    // else if(menuInput.equalsIgnoreCase("Down")){
                    //     direction = "Down";
                    // }
                    // else{
                    //     System.out.println("Input Salah");
                    // }
                    // if(horizontal){
                    //     System.out.println("Horizontal");
                    // }
                    // else{
                    //     System.out.println("Vertical");
                    // }

                    
                        Boolean valid;
                        // System.out.println(direction);
                        Furniture furniture = Furniture.valueOf(input.toUpperCase());
                        // int length = furniture.getDimensi().getLength();
                        // int width = furniture.getDimensi().getWidth();
                        // System.out.println(length + " , " + width);
                        // System.out.println(x + " , " + y);
                        valid = room.placeFurniture(x , y , furniture.getDimensi() ,furniture.getName() , furniture.getNamaInisial() , input2);
                        if(valid){
                            sim.getInventoryFurniture().kurangiStock(furniture,1);
                            room.addFurnitureData(valid, x, y, furniture.getDimensi(),furniture.getName() , furniture.getNamaInisial() , input2);
                            MyObject myObject = new MyObject(furniture.getName());
                            room.addObject(myObject);
                            System.out.println("Furniture berhasil diletakkan di koordinat (" + x + "," + y + ")");
                        }
                        else{
                            System.out.println("Tidak dapat meletakkan furniture di koordinat (" + x + "," + y + ")");
                        }
                        room.printRoom();
                    
                    }

                    else if (menuInput.equals("2") || menuInput.equalsIgnoreCase("Edit Furniture")) {
                    room.printFurnitureData();
                    System.out.print("Masukkan nama furniture yang ingin anda edit : ");
                    menuInput = scanner.nextLine();
                    System.out.print("Masukkan koordinat X : ");
                    int koorX = scanner.nextInt();
                    System.out.print("Masukkan koordinat Y : ");
                    int koorY = scanner.nextInt();
                    Boolean inputValid = room.checkFurnitureData(menuInput , koorX , koorY);

                    
                    
                        if(inputValid) {
                            furnitureData = room.getFurnitureData(menuInput); 
                            System.out.println("Furniture yang ingin anda edit adalah " + furnitureData.getFurnitureName());
                            room.removeFurniture(furnitureData.getStartX(), furnitureData.getStartY(), furnitureData.getDimension(),furnitureData.getFurnitureName(),furnitureData.getInitialName(), furnitureData.getDirection());
                            room.removeFurnitureData(room.getFurnitureDataList() , room.getFurnitureDataWithParameter(furnitureData.getStartX(), furnitureData.getStartY(), furnitureData.getDimension(),furnitureData.getFurnitureName(),furnitureData.getInitialName(), furnitureData.getDirection()));
                            System.out.println("Masukkan koordinat yang baru : ");
                            System.out.print("Masukkan koordinat X : ");
                            koorX = scanner.nextInt();
                            System.out.print("Masukkan koordinat Y : ");
                            koorY = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Masukan Arah Letak Benda (Right/Left/Up/Down) :");
                            String input2 = scanner.nextLine();
                            Boolean validInput = false;

                            validInput = room.placeFurniture(koorX , koorY , furnitureData.getDimension() ,furnitureData.getFurnitureName() , furnitureData.getInitialName() , input2);
                            while(!validInput){
                                System.out.println("Koordinat yang anda masukkan tidak valid");
                                System.out.println("Masukkan koordinat yang baru : ");
                                System.out.print("Masukkan koordinat X : ");
                                koorX = scanner.nextInt();
                                System.out.print("Masukkan koordinat Y : ");
                                koorY = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Masukan Arah Letak Benda (Right/Left/Up/Down) :");
                                input2 = scanner.nextLine();
                                validInput = room.placeFurniture(koorX , koorY , furnitureData.getDimension() ,furnitureData.getFurnitureName() , furnitureData.getInitialName() , input2);
                            }
                            if(validInput){
                                room.addFurnitureData(validInput, koorX, koorY, furnitureData.getDimension(),furnitureData.getFurnitureName() , furnitureData.getInitialName() , input2);
                                System.out.println("Furniture berhasil diletakkan di koordinat (" + koorX + "," + koorY + ")");
                            }
                            else{
                                System.out.println("Tidak dapat meletakkan furniture di koordinat (" + koorX + "," + koorY + ")");
                            }
                            room.printRoom();
                        }
                        else{
                            System.out.println("Furniture tidak ditemukan");
                        }
                    }

                    else if (menuInput.equals("3") || menuInput.equalsIgnoreCase("Remove Furniture")) {
                        room.printFurnitureData();
                        System.out.print("Masukkan nama furniture yang ingin anda hapus : ");
                        menuInput = scanner.nextLine();
                        System.out.print("Masukkan koordinat X : ");
                        int koorX = scanner.nextInt();
                        System.out.print("Masukkan koordinat Y : ");
                        int koorY = scanner.nextInt();
                        // scanner.nextLine();
                        // System.out.println(menuInput);
                        // System.out.println(koorX);
                        // System.out.println(koorY);
        
                        Boolean inputValid = room.checkFurnitureData(menuInput , koorX , koorY);
        
                        // while(!inputValid){
                        //     System.out.println("");
                        //     System.out.println("Furniture tidak ditemukan");
                        //     System.out.print("Masukkan nama furniture yang ingin anda hapus : ");
                        //     scanner.nextLine();
                        //     menuInput = scanner.nextLine();
                        //     System.out.print("Masukkan koordinat X : ");
                        //     koorX = scanner.nextInt();
                        //     System.out.print("Masukkan koordinat Y : ");
                        //     koorY = scanner.nextInt();
                        //     inputValid = room.checkFurnitureData(menuInput , koorX , koorY);
                        // }
                        if(inputValid){
                            Furniture furniture = Furniture.valueOf(menuInput.toUpperCase());
                            furnitureData = room.getFurnitureData(menuInput); 
                            System.out.println("Furniture yang ingin anda hapus adalah " + furnitureData.getFurnitureName());
                            room.removeFurniture(furnitureData.getStartX(), furnitureData.getStartY(), furnitureData.getDimension(),furnitureData.getFurnitureName(),furnitureData.getInitialName(), furnitureData.getDirection());
                            room.removeFurnitureData(room.getFurnitureDataList() , room.getFurnitureDataWithParameter(furnitureData.getStartX(), furnitureData.getStartY(), furnitureData.getDimension(),furnitureData.getFurnitureName(),furnitureData.getInitialName(), furnitureData.getDirection()));
                            MyObject myObject = room.getMyObject(furnitureData.getFurnitureName());
                            room.removeObject(myObject);
                            sim.getInventoryFurniture().tambahStock(furniture,1);
                            System.out.println("Furniture berhasil dihapus");
                            room.printRoom();
                        }
                        else{
                            System.out.println("Furniture tidak ditemukan");
                        }
                    
                    }
                }
                    else{
                        obj.print("Sim tidak sedang berada di rumahnya sendiri , oleh sebab itu tidak bisa menggunakan fitur ini");
                        obj.print("Kembali ke rumah anda untuk mengaktifkan fitur ini dengan menggunakan aksi berkunjung");
                    }
                
                }
                
                   

            
        
            //     else if(menuInput.equals("7")|| menuInput.equalsIgnoreCase("Add Sim")){
            //     System.out.println("Masukkan nama SIM: ");
            //     nama = scanner.nextLine();
            //     Sim newSim = new Sim(nama);
            //     world.addSim(newSim);
            //     world.addWaktuTidakTidur(newSim , 0);
            //     world.addWaktuTidakBuangAir(newSim , 0);
            //     System.out.println("Objek SIM " + nama + " berhasil ditambahkan ke dalam list!");
            //     // for (Sim daftarSim : world.simList) {
            //     //     System.out.println("- " + daftarSim.getNamaLengkap());
            //     // }
            // }

            else if(menuInput.equals("7") || menuInput.equalsIgnoreCase("Add Sim")){
                if(!(world.getIsAddSim())){
                    obj.addSim();
                    world.setIsAddSim(true);
                }
                else{
                    obj.print("Anda sudah menambahkan SIM di hari ini , silahkan mencoba di hari berikutnya");
                }
            }
            
            else if(menuInput.equals("8")|| menuInput.equalsIgnoreCase("Change Sim")){
                if (world.getSimList().isEmpty()) {
                    System.out.println("Tidak ada SIM yang tersedia.");
                } else {
                    System.out.println("Berikut adalah SIM yang tersedia:");
                for ( Sim daftarSim : world.getSimList()) {
                    System.out.println("- " + daftarSim.getNamaLengkap());
                }
                System.out.println("Masukkan nama SIM yang ingin anda gunakan : ");
                nama = scanner.nextLine();
                for(Sim s : world.getSimList()){
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
                System.out.println("Masukkan Koordinat Objek Yang Dituju: ");
                System.out.print("X : ");
                int x = scanner.nextInt();
                System.out.print("Y : ");
                int y = scanner.nextInt();
                sim.moveToObject(x,y);
                scanner.nextLine();
                // Scanner input = new Scanner(System.in);
                //     String objName = sim.getRoom().getLayoutContent(x , y);
                //     //Melakukan Cek apakah masukan melebihi peta layout [5] [5] 
                //     if (x > 5 || y > 5 || x < 0 || y < 0) {
                //         System.out.println("Posisi yang anda masukkan tidak valid");
                //     }
                //     else{
                //         if (objName != "") {
                //             // System.out.println("Moving to " + obj.getName() + " at (" + x + "," + y + ")");
                //             switch(objName) {
                //                 case "KSS":
                //                 //Tidur
                //                 System.out.print("Apakah anda ingin melakukan aksi tiudr ? (y/n)");
                //                 input = new Scanner(System.in);
                //                 String jawaban = input.nextLine();
                //                 while(!(jawaban.equalsIgnoreCase("y")) || !(jawaban.equalsIgnoreCase("n"))){
                //                     if (jawaban.equalsIgnoreCase("y")){
                //                         int number = sim.readInteger(scanner);
                //                         sim.tidur(number);
                //                     } else if(jawaban.equalsIgnoreCase("n")) {
                //                         System.out.println("Anda tidak ingin melakukan aksi tidur");
                //                     }
                //                     else{
                //                         System.out.println("Masukan tidak valid");
                //                     }
                //                 }
                //                     break;

                //                 case "KQS":
                //                     //Tidur
                //                     System.out.print("Apakah anda ingin melakukan aksi tidur ? (y/n)");
                //                     Scanner input2 = new Scanner(System.in);
                //                     String jawaban2 = input2.nextLine();
                //                     while(!(jawaban2.equalsIgnoreCase("y")) || !(jawaban2.equalsIgnoreCase("n"))){
                //                         if (jawaban2.equalsIgnoreCase("y")){
                //                             int number = sim.readInteger(scanner);
                //                             sim.tidur(number);
                //                         } else if(jawaban2.equalsIgnoreCase("n")) {
                //                             System.out.println("Anda tidak ingin melakukan aksi tidur");
                //                         }
                //                         else{
                //                             System.out.println("Masukan tidak valid");
                //                         }
                //                     }
                //                     break;
                //                 case "KKS":
                //                 //Tidur
                //                 System.out.print("Apakah anda ingin melakukan aksi tidur ? (y/n)");
                //                 Scanner input3 = new Scanner(System.in);
                //                 String jawaban3 = input3.nextLine();
                //                 while(!(jawaban3.equalsIgnoreCase("y")) || !(jawaban3.equalsIgnoreCase("n"))){
                //                     if (jawaban3.equalsIgnoreCase("y")){
                //                         int number = sim.readInteger(scanner);
                //                         sim.tidur(number);
                //                     } else if(jawaban3.equalsIgnoreCase("n")) {
                //                         System.out.println("Anda tidak ingin melakukan aksi tidur");
                //                     }
                //                     else{
                //                         System.out.println("Masukan tidak valid");
                //                     }
                //                 }
                //                     break;
                                    
                //                 case "TLT":
                //                     //Buang air
                //                     System.out.print("Apakah anda ingin melakukan aksi buang air ? (y/n)");
                //                     Scanner input4 = new Scanner(System.in);
                //                     String jawaban4 = input4.nextLine();
                //                     while(!(jawaban4.equalsIgnoreCase("y")) || !(jawaban4.equalsIgnoreCase("n"))){
                //                         if (jawaban4.equalsIgnoreCase("y")){
                //                             sim.buangAir();
                //                         } else if(jawaban4.equalsIgnoreCase("n")) {
                //                             System.out.println("Anda tidak ingin melakukan aksi buang air");
                //                         }
                //                         else{
                //                             System.out.println("Masukan tidak valid");
                //                         }
                //                     }
                //                     break;

                //                 case "KMG":
                //                 //Masak
                //                 System.out.print("Apakah anda ingin melakukan aksi masak ? (y/n)");
                //                 Scanner input5 = new Scanner(System.in);
                //                 String jawaban5 = input5.nextLine();
                //                 while(!(jawaban5.equalsIgnoreCase("y")) || !(jawaban5.equalsIgnoreCase("n"))){
                //                     if (jawaban5.equalsIgnoreCase("y")){
                //                         // masak();
                //                     } else if(jawaban5.equalsIgnoreCase("n")) {
                //                         System.out.println("Anda tidak ingin melakukan aksi masak");
                //                     }
                //                     else{
                //                         System.out.println("Masukan tidak valid");
                //                     }
                //                 }
                //                     break;

                //                 case "KML":
                //                     //Masak
                //                     System.out.print("Apakah anda ingin melakukan aksi masak ? (y/n)");
                //                     Scanner input6 = new Scanner(System.in);
                //                     String jawaban6 = input6.nextLine();
                //                     while(!(jawaban6.equalsIgnoreCase("y")) || !(jawaban6.equalsIgnoreCase("n"))){
                //                         if (jawaban6.equalsIgnoreCase("y")){
                //                             // masak();
                //                         } else if(jawaban6.equalsIgnoreCase("n")) {
                //                             System.out.println("Anda tidak ingin melakukan aksi masak");
                //                         }
                //                         else{
                //                             System.out.println("Masukan tidak valid");
                //                         }
                //                     }
                //                     break;

                //                 case "MDK":
                //                     //Makan
                //                     System.out.print("Apakah anda ingin melakukan aksi makan ? (y/n)");
                //                     Scanner input7 = new Scanner(System.in);   
                //                     String jawaban7 = input7.nextLine();
                //                     while(!(jawaban7.equalsIgnoreCase("y")) || !(jawaban7.equalsIgnoreCase("n"))){
                //                         if (jawaban7.equalsIgnoreCase("y")){
                //                             // sim.makan();
                //                         } else if(jawaban7.equalsIgnoreCase("n")) {
                //                             System.out.println("Anda tidak ingin melakukan aksi makan");
                //                         }
                //                         else{
                //                             System.out.println("Masukan tidak valid");
                //                         }
                //                     }
                //                     break;

                //                 case "JAM":
                //                     //Melihat Waktu
                //                     System.out.print("Apakah anda ingin melakukan aksi melihat waktu ? (y/n)");
                //                     Scanner input8 = new Scanner(System.in);
                //                     String jawaban8 = input8.nextLine();
                //                     while(!(jawaban8.equalsIgnoreCase("y")) || !(jawaban8.equalsIgnoreCase("n"))){
                //                         if (jawaban8.equalsIgnoreCase("y")){
                //                             sim.lihatWaktu();
                //                         } else if(jawaban8.equalsIgnoreCase("n")) {
                //                             System.out.println("Anda tidak ingin melakukan aksi melihat waktu");
                //                         }
                //                         else{
                //                             System.out.println("Masukan tidak valid");
                //                         }
                //                     }
                //                     break;
                //             }
                //         }
                //         else{
                //             System.out.println("Object not found at (" + x + "," + y + ")");
                //         }
                //     }
                
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
                        System.out.print("Masukkan waktu kerja: ");
                        int waktuKerja = scanner.nextInt();
                        sim.kerja(waktuKerja);
                        scanner.nextLine();
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
                                    obj.print("");
                                    world.printAllDeliveryItems();
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
                                    world.printAllDeliveryItems();
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

            else if(menuInput.equals("14")|| menuInput.equalsIgnoreCase("Print Room")){
                room.printRoom();
            }

            else if(menuInput.equals("15")|| menuInput.equalsIgnoreCase("Print Map")){
                world.printWorld();;
            }
            else if(menuInput.equals("16")|| menuInput.equalsIgnoreCase("Lihat Waktu")){
                sim.lihatWaktu();
            }

            else if(menuInput.equalsIgnoreCase("Help") || menuInput.equalsIgnoreCase("Menu")){
                obj.printMenu();
            }
            else{
                System.out.println("Input tidak valid.");
            }
        }
    }
            
        else if(menuInput.equals("2") || menuInput.equalsIgnoreCase("Help")){
            obj.help();
        }

        else if(menuInput.equals("3") || menuInput.equalsIgnoreCase("Exit")){
            obj.exit();
        }

        else{
            System.out.println("Input tidak valid.");
        }
    }
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
        //         world.simList.add(sim);
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
        //         if (world.simList.isEmpty()) {
        //             System.out.println("Tidak ada SIM yang tersedia.");
        //         } else {
        //             System.out.println("Berikut adalah SIM yang tersedia:");
        //         for (Sim sim : world.simList) {
        //             System.out.println("- " + sim.getNamaLengkap());
        //         }
                
        //         System.out.println("Masukkan nama SIM yang ingin anda lihat informasinya : ");
        //         String nama = scanner.nextLine();
        //         Sim sim = null;
        //         for(Sim s : world.simList){
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



