
import java.util.Random;

public class SIM implements Runnable {
    private String nama;
    private int uang;
    private int mood;
    private int kesehatan;

    public SIM(String nama) {
        this.nama = nama;
        Random random = new Random();
        this.uang = random.nextInt(100) + 1;
        this.mood = random.nextInt(100) + 1;
        this.kesehatan = random.nextInt(100) + 1;
    }

    public synchronized int ngelawak() {
        //ascii
        System.out.println(nama + " sedang makan...");
        try {
            Thread.sleep(20000);
        int waktuDibutuhkan = 20;
        int moodNaik = getMood() + 10;
        setMood(moodNaik);
        int kesehatanNaik = getKesehatan() + 5;
        setKesehatan(kesehatanNaik);
        int kekenyanganTurun = getKekenyangan() - 3;
        setKekenyangan(kekenyanganTurun);

        setStatus("Melawak");

        // print stats
        printStat();
        
        return waktuDibutuhkan;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kesehatan += 20;
        System.out.println(nama + " selesai makan, kesehatan bertambah menjadi " + kesehatan);
    }

    public synchronized void bermain() {
        System.out.println(nama + " sedang bermain game...");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mood += 20;
        System.out.println(nama + " selesai bermain game, mood bertambah menjadi " + mood);
    }

    public synchronized void bekerja() {
        System.out.println(nama + " sedang bekerja...");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        uang += 50;
        System.out.println(nama + " selesai bekerja, uang bertambah menjadi " + uang);
    }

    public synchronized void aksiLainnya() {
        System.out.println(nama + " melakukan aksi lainnya...");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(nama + " selesai melakukan aksi lainnya.");
    }

    public void run() {
        makan();
        bermain();
        bekerja();
        aksiLainnya();
    }
}

public class Main {
    public static void main(String[] args) {
        SIM sim = new SIM("Sim 1");

        Thread thread = new Thread(sim);
        thread.start();
    }
}