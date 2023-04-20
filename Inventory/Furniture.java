package Inventory;
import java.util.*;

// blm ada coordinate sama interact

public enum Furniture {
    KASUR_SINGLE("Kasur Single", new Dimension(4, 1), 50, "Tidur"),
    KASUR_QUEEN_SIZE("Kasur Queen Size", new Dimension(4, 2), 100, "Tidur"),
    KASUR_KING_SIZE("Kasur King Size", new Dimension(5, 2), 150, "Tidur"),
    TOILET("Toilet", new Dimension(1, 1), 50, "Buang air"),
    KOMPOR_GAS("Kompor Gas", new Dimension(2, 1), 100, "Memasak"),
    KOMPOR_LISTRIK("Kompor Listrik", new Dimension(1, 1), 200, "Memasak"),
    MEJA_KURSI("Meja dan Kursi", new Dimension(3, 3), 50, "Makan"),
    JAM("Jam", new Dimension(1, 1), 10, "Melihat Waktu");

    private final String name;
    private final Dimension dimensi;
    private final int harga;
    private final String aksi;

    Furniture(String name, Dimension dimensi, int harga, String aksi) {
        this.name = name;
        this.dimensi = dimensi;
        this.harga = harga;
        this.aksi = aksi;
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
}