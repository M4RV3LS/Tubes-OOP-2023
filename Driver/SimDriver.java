import java.util.*;
import Sim.*;

public class SimDriver {
    public static void main(String[] args) {
        Sim sim = new Sim("John Doe");
        System.out.println("Nama Lengkap: " + sim.getNamaLengkap());
        System.out.println("Pekerjaan: " + sim.getPekerjaan().toString());
        System.out.println("Uang: " + sim.getUang());
        System.out.println("Kekenyangan: " + sim.getKekenyangan());
        System.out.println("Mood: " + sim.getMood());
        System.out.println("Kesehatan: " + sim.getKesehatan());
        System.out.println("Status: " + sim.getStatus());
        // System.out.println("Inventory: " + sim.getInventory().toString());
    }
}

