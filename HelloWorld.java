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
    public static void main(String[] args) {
        printFurnitureAksi();
    }
}