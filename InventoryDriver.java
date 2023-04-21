import Inventory.Furniture;
import Inventory.BahanMakanan;
import Inventory.Inventory;
import Inventory.Masakan;

public class InventoryDriver {
    public static void main(String[] args) {
        // membuat objek Inventory
        Inventory inventory = new Inventory();
        
        // menambahkan stock bahan makanan
        inventory.tambahStock(BahanMakanan.AYAM, 10);
        inventory.tambahStock(BahanMakanan.NASI, 20);
        
        // menambahkan stock masakan
        inventory.tambahStock(Masakan.NASI_AYAM, 5);
        inventory.tambahStock(Masakan.TUMIS_SAYUR, 3);
        
        // menambahkan stock furniture
        inventory.tambahStock(Furniture.MEJA_KURSI, 2);
        inventory.tambahStock(Furniture.JAM, 4);
        
        // menampilkan stock bahan makanan, masakan, dan furniture
        System.out.println("Stock Bahan Makanan:");
        System.out.println("Ayam: " + inventory.getStock(BahanMakanan.AYAM));
        System.out.println("Nasi: " + inventory.getStock(BahanMakanan.NASI));
        
        System.out.println("\nStock Masakan:");
        System.out.println("Nasi Ayam: " + inventory.getStock(Masakan.NASI_AYAM));
        System.out.println("Tumis Sayur: " + inventory.getStock(Masakan.TUMIS_SAYUR));
        
        System.out.println("\nStock Furniture:");
        System.out.println("Meja_Kursi: " + inventory.getStock(Furniture.MEJA_KURSI));
        System.out.println("Jam: " + inventory.getStock(Furniture.JAM));
        System.out.print("\n");
        // mengurangi stock bahan makanan
        System.out.println("Mengurangi stock bahan makanan:");
        inventory.kurangiStock(BahanMakanan.NASI, 2);
        System.out.println("Sisa stock nasi: " + inventory.getStock(BahanMakanan.NASI));
        inventory.kurangiStock(BahanMakanan.AYAM, 3);
        System.out.println("Sisa stock ayam: " + inventory.getStock(BahanMakanan.AYAM));

        // mengurangi stock masakan
        System.out.println("\nMengurangi stock masakan:");
        inventory.kurangiStock(Masakan.NASI_AYAM, 1);
        System.out.println("Sisa stock nasi ayam: " + inventory.getStock(Masakan.NASI_AYAM));
        inventory.kurangiStock(Masakan.TUMIS_SAYUR, 2);
        System.out.println("Sisa stock tumis sayur: " + inventory.getStock(Masakan.TUMIS_SAYUR));

        // mengurangi stock furniture
        System.out.println("\nMengurangi stock furniture:");
        inventory.kurangiStock(Furniture.MEJA_KURSI, 4);
        System.out.println("Sisa stock Meja_Kursi: " + inventory.getStock(Furniture.MEJA_KURSI));
        inventory.kurangiStock(Furniture.JAM, 2);
        System.out.println("Sisa stock jam: " + inventory.getStock(Furniture.JAM));

    }
}