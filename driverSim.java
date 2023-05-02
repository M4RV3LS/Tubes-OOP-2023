// import Bagian.*;
// import SIM.*;

public class driverSim {
    public static void main(String[] args) {
        SIM sim = new SIM("John Doe");
        sim.viewStats();
        System.out.println();

        // lihat inventory 
        // sim.lihatInventory();

        // beli barang
        sim.showSaledItem();
        sim.pembelian("Nasi", 4);
        // sim.pembelian("Toilet", 1);
        sim.pembelian("Kasur Single", 1);
        sim.lihatInventory();

        // sim.lihatInventory();

        // sim.pembelian("Kasur King Size", 1);
        // sim.lihatInventory();

        // // Test pekerjaan
        // System.out.println("Pekerjaan sekarang: " + sim.getPekerjaan());
        // sim.setPekerjaan(Job.POLISI);
        // System.out.println("Pekerjaan setelah berganti: " + sim.getPekerjaan());
        // System.out.println();

        // // Test kerja
        // sim.kerja(480);
        // sim.viewStats();
        // System.out.println();

        // // Test beli barang
        // sim.getInventory().tambahBarang(new Barang("Baju", 2));
        // sim.viewStats();
        // System.out.println();

        // // Test buang air
        // sim.buangAir();
        // sim.viewStats();
        // System.out.println();

        // // Test tidur
        // sim.tidur(720);
        // sim.viewStats();
        // System.out.println();

        // // Test pindah rumah
        // sim.getRumah().setLokasi(new Coordinate(10, 10));
        // sim.pindahRumah();
        // sim.viewStats();
        // System.out.println();
    }
}
