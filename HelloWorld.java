// Online Java Compiler
// Use this editor to write, compile and run your Java code online

class HelloWorld {
    public static void printBukuResep(){
        System.out.printf("-----------------------------------------------%n");
        System.out.printf("|          BUKU RESEP SIMPLICITY              |%n");

        System.out.printf("-----------------------------------------------%n");
        System.out.printf("| %-15s | %-25s |%n", "MAKANAN", "BAHAN MAKANAN");
        System.out.printf("-----------------------------------------------%n");

        System.out.printf("| %-15s | %-25s |%n", "Nasi Ayam", "Nasi, Ayam");
        System.out.printf("| %-15s | %-25s |%n", "Nasi Kari", "Nasi, Kentang, Wortel");
        System.out.printf("| %-15s | %-25s |%n", "Susu Kacang", "Susu, Kacang");
        System.out.printf("| %-15s | %-25s |%n", "Tumis Sayur", "Wortel, Bayam");
        System.out.printf("| %-15s | %-25s |%n", "Bistik", "Kentang, Sapi");
        

        System.out.printf("-----------------------------------------------%n");
    }

    public static void printFurnitureAksi(){
        System.out.printf("-----------------------------------------------%n");
        System.out.printf("|       AKSI DAN FURNITUR SIMPLICITY          |%n");

        System.out.printf("-----------------------------------------------%n");
        System.out.printf("| %-15s | %-25s |%n", "AKSI", "FURNITURE");
        System.out.printf("-----------------------------------------------%n");

        System.out.printf("| %-15s | %-25s |%n", "Tidur", "Kasur Single (KSS)");
        System.out.printf("| %-15s | %-25s |%n", "Tidur", "Kasur Queen Size (KQS)");
        System.out.printf("| %-15s | %-25s |%n", "Tidur", "Kasur King Size (KSS)");
        System.out.printf("| %-15s | %-25s |%n", "Buang Air", "Toilet (TLT)");
        System.out.printf("| %-15s | %-25s |%n", "Memasak", "Kompor Gas (KMG)");
        System.out.printf("| %-15s | %-25s |%n", "Memasak", "Kompor Listrik (KML)");
        System.out.printf("| %-15s | %-25s |%n", "Makan", "Meja dan Kursi (MDK)");
        System.out.printf("| %-15s | %-25s |%n", "Melihat Waktu", "Jam (JAM)");

        System.out.printf("| %-15s | %-25s |%n", "Main Game", "Komputer (KOM)");
        System.out.printf("| %-15s | %-25s |%n", "Santet", "Bola Kristal (KRS)");
        System.out.printf("| %-15s | %-25s |%n", "Berobat", "Kotak Obat (MED)");
        System.out.printf("| %-15s | %-25s |%n", "Karaoke", "Microphone (MIC)");
        System.out.printf("| %-15s | %-25s |%n", "Puasa", "Kitab Suci (KTB)");
        System.out.printf("| %-15s | %-25s |%n", "Bersih-Bersih", "Sapu (SPU)");
        System.out.printf("| %-15s | %-25s |%n", "Melawak", "Sofa (SOF)");
        

        System.out.printf("-----------------------------------------------%n");
    }

    public static void printMenu(){
        System.out.println("");
        System.out.println("=================== MENU GAME ======================================================");
        System.out.println("1.View Sim Info         : Menampilkan informasi kondisi Sim saat ini");
        System.out.println("2.View Current Location : Menampilkan informasi posisi Sim saat ini");
        System.out.println("3.View Inventory        : Menampilkan isi inventory yang dimiliki oleh Sim");
        System.out.println("4.Upgrade House         : Melakukan perbaikan rumah berupa penambahan ruangan");
        System.out.println("5.Move Room             : Berpindah menuju ruangan lain");
        System.out.println("6.Edit Room             : Melakukan perubahan terhadap ruangan saat ini");
        System.out.println("7.Add Sim               : Menciptakan karakter Sim baru");
        System.out.println("8.Change Sim            : Mengganti karakter Sim yang dimainkan");
        System.out.println("9.List Object           : Menampilkan daftar objek yang terdapat di dalam ruangan");
        System.out.println("10.Go To Object         : Pergi menuju objek yang terdapat di dalam ruangan");
        System.out.println("11.Action               : Melakukan aksi pada suatu objek");
        System.out.println("12.Change Job           : Melakukan penggantian pekerjaan Sim");
        System.out.println("13.Beli barang          : Melakukan pembelian barang pada Shop");
        System.out.println("14.Print Room           : Menampilkan informasi terkait ruangan");
        System.out.println("15.Print Map            : Menampilkan informasi peta pada Sim-Plicity");
        System.out.println("16.Exit                 : Keluar dari permainan");
        System.out.println("17.Lihat Waktu          : Menampilkan waktu pada dunia Sim-Plicity");
    }

    public static void printASCII(){
        // 1. main game
        System.out.println(" ██████   █████  ███    ███ ███████      ██████  ███    ██ ██ ");
        System.out.println("██       ██   ██ ████  ████ ██          ██    ██ ████   ██ ██ ");
        System.out.println("██   ███ ███████ ██ ████ ██ █████       ██    ██ ██ ██  ██ ██ ");
        System.out.println("██    ██ ██   ██ ██  ██  ██ ██          ██    ██ ██  ██ ██    ");
        System.out.println(" ██████  ██   ██ ██      ██ ███████      ██████  ██   ████ ██ ");
        System.out.println("");

        // 2. santet
        System.out.println("██████   █████   █████  ██     ██ ██████  ██████  ");
        System.out.println("██   ██ ██   ██ ██   ██ ██     ██ ██   ██ ██   ██ ");
        System.out.println("██████  ███████ ███████ ██  █  ██ ██████  ██████  ");
        System.out.println("██   ██ ██   ██ ██   ██ ██ ███ ██ ██   ██ ██   ██ ");
        System.out.println("██   ██ ██   ██ ██   ██  ███ ███  ██   ██ ██   ██ ");
        System.out.println("");

        // 3. berobat
        System.out.println("██   ██ ███████  █████  ██      ██ ███    ██  ██████           ");
        System.out.println("██   ██ ██      ██   ██ ██      ██ ████   ██ ██                ");
        System.out.println("███████ █████   ███████ ██      ██ ██ ██  ██ ██   ███          ");
        System.out.println("██   ██ ██      ██   ██ ██      ██ ██  ██ ██ ██    ██          ");
        System.out.println("██   ██ ███████ ██   ██ ███████ ██ ██   ████  ██████  ██ ██ ██");
        System.out.println("");

        // 4. karaoke
        System.out.println("██   ██  █████  ██████   █████   ██████  ██   ██ ███████ ██");
        System.out.println("██  ██  ██   ██ ██   ██ ██   ██ ██    ██ ██  ██  ██      ██ ");
        System.out.println("█████   ███████ ██████  ███████ ██    ██ █████   █████   ██ ");
        System.out.println("██  ██  ██   ██ ██   ██ ██   ██ ██    ██ ██  ██  ██         ");
        System.out.println("██   ██ ██   ██ ██   ██ ██   ██  ██████  ██   ██ ███████ ██ ");
        System.out.println("");

        // 5. puasa
        System.out.println("██████  ██    ██  █████  ███████  █████           ");
        System.out.println("██   ██ ██    ██ ██   ██ ██      ██   ██          ");
        System.out.println("██████  ██    ██ ███████ ███████ ███████          ");
        System.out.println("██      ██    ██ ██   ██      ██ ██   ██          ");
        System.out.println("██       ██████  ██   ██ ███████ ██   ██ ██ ██ ██ ");
        System.out.println("");

        // 6. bersih-bersih
        System.out.println(" ██████ ██      ███████  █████  ███    ██ ██ ███    ██  ██████           ");
        System.out.println("██      ██      ██      ██   ██ ████   ██ ██ ████   ██ ██                ");
        System.out.println("██      ██      █████   ███████ ██ ██  ██ ██ ██ ██  ██ ██   ███          ");
        System.out.println("██      ██      ██      ██   ██ ██  ██ ██ ██ ██  ██ ██ ██    ██          ");
        System.out.println(" ██████ ███████ ███████ ██   ██ ██   ████ ██ ██   ████  ██████  ██ ██ ██ ");
        System.out.println("");

        // 7. melawak
        System.out.println("██   ██  █████  ██   ██  █████  ██   ██  █████   ██ ");
        System.out.println("██   ██ ██   ██ ██   ██ ██   ██ ██   ██ ██   ██  ██ ");
        System.out.println("███████ ███████ ███████ ███████ ███████ ███████  ██ ");
        System.out.println("██   ██ ██   ██ ██   ██ ██   ██ ██   ██ ██   ██     ");
        System.out.println("██   ██ ██   ██ ██   ██ ██   ██ ██   ██ ██   ██  ██ ");
        System.out.println("");
    }
    public static void main(String[] args) {
        printASCII();
    }
}