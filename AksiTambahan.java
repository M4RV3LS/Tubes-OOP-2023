package Sim;

import java.util.*;
import Inventory.*;
import Map.*;
import Fitur.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface AksiTambahan {
    public void mainGame(int lamaMain);
    public void santet(Sim simLain);
    public void berobat(int lamaBerobat);
    public void karaoke(int lamaKaraoke);
    public void puasa();
    public void bersihBersih(int lamaBersihBersih);
    public void melawak();
}
