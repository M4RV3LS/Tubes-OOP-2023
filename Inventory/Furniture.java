package Inventory;
import Sim.*;
import java.util.*;
import Inventory.*;
import Fitur.*;
import Map.*;


public enum Furniture {
    KASUR_SINGLE("Kasur Single", new Dimension(4, 1), 50, "Tidur","KSS"),
    KASUR_QUEEN_SIZE("Kasur Queen Size", new Dimension(4, 2), 100, "Tidur" , "KQS"),
    KASUR_KING_SIZE("Kasur King Size", new Dimension(5, 2), 150, "Tidur" , "KKS"),
    TOILET("Toilet", new Dimension(1, 1), 50, "Buang air" , "TLT"),
    KOMPOR_GAS("Kompor Gas", new Dimension(2, 1), 100, "Memasak" , "KMG"),
    KOMPOR_LISTRIK("Kompor Listrik", new Dimension(1, 1), 200, "Memasak" , "KML"),
    MEJA_KURSI("Meja dan Kursi", new Dimension(3, 3), 50, "Makan" , "MDK"),
    JAM("Jam", new Dimension(1, 1), 10, "Melihat Waktu" , "JAM"),

    // TAMBAHAN
    KOMPUTER("Komputer", new Dimension(1, 1), 350, "Main Game", "KOM"),
    BOLA_KRISTAL("Bola Kristal", new Dimension(1, 1), 200, "Santet", "KRS"),
    KOTAK_OBAT("Kotak Obat", new Dimension(2, 2), 100, "Berobat", "MED"),
    MICROPHONE("Microphone", new Dimension(1, 1), 50, "Karaoke", "MIC"),
    KITAB_SUCI("Kitab Suci", new Dimension(1, 1), 50, "Puasa", "KTB"),
    SAPU("Sapu", new Dimension(1, 1), 20, "Bersih-Bersih", "SPU"),
    SOFA("Sofa", new Dimension(3, 3), 150, "Melawak", "SOF");

    private final String name;
    private final Dimension dimensi;
    private final int harga;
    private final String aksi;
    private final String namaInisial;

    Furniture(String name, Dimension dimensi, int harga, String aksi , String namaInisial) {
        this.name = name;
        this.dimensi = dimensi;
        this.harga = harga;
        this.aksi = aksi;
        this.namaInisial = namaInisial;
    }

    public String getName() {
        return name;
    }

    public Dimension getDimensi() {
        return dimensi;
    }

    public int getHarga() {
        return harga;
    }

    public String getAksi() {
        return aksi;
    }

    public String getNamaInisial(){
        return namaInisial;
    }
}
