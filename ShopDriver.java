
import Fitur.*;
import Inventory.*;
import Sim.*;
import java.util.*;

public class ShopDriver {
    public static void main(String[] args) {
        PrintASCII ascii = new PrintASCII();
        ascii.printShop();
        Sim sim = new Sim("Marvel");
        Shop shop = new Shop(sim);
        Inventory inventory = sim.getInventory();

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
                sim.getInventory().printInventory();
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
}











