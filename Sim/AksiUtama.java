package Sim;

import java.util.*;
import Inventory.*;
import Map.*;
import Fitur.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AksiUtama {
    public abstract void lihatWaktu();
    public abstract void olahraga(int lamaOlahraga);
    public abstract void tidur(int lamaTidur);
    public abstract void masak();
    public abstract void makan();
    public abstract void Berkunjung();
    public abstract void buangAir();
    
    public void kerja(int lamaKerja){
        System.out.println("Anda tidak memiliki pekerjaan :((");
    }
}