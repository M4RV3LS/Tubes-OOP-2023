package Sim;

public interface Aksi {
    public int kerja(int lamaKerja);
    public void olahraga();
    public void tidur();
    public void masak();
    public void makan();
    public int buangAir();
    public void lihatWaktu();
    public void mainGame(int lamaMain);
    public void santet(Sim simLain);
    public void berobat(int lamaBerobat);
    public void karaoke(int lamaKaraoke);
    public void puasa();
    public void bersihBersih(int lamaBersihBersih);
    public void melawak();
    public void moveToObject(int x, int y);
}
