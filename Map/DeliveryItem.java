package Map;
import Sim.*;
import Map.*;
import Fitur.*;
import java.util.*;
import Inventory.*;


public class DeliveryItem<T> {
    private Sim sim;
    private int waktu;
    private T tipeObjek;
    private String namaObjek;
    private int quantity;

    public DeliveryItem(Sim sim, int waktu, T tipeObjek, String namaObjek , int quantity) {
        this.sim = sim;
        this.waktu = waktu;
        this.tipeObjek = tipeObjek;
        this.namaObjek = namaObjek;
        this.quantity = quantity;
    }

    public Sim getSim() {
        return sim;
    }

    public void setSim(Sim sim) {
        this.sim = sim;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }

    public T getTipeObjek() {
        return tipeObjek;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setObjek(T tipeObjek) {
        this.tipeObjek = tipeObjek;
    }

    public String getNamaObjek() {
        return namaObjek;
    }

    public void setNamaObjek(String namaObjek) {
        this.namaObjek = namaObjek;
    }

    //setter quantity
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    //method kurangi waktu delivery item
    public void kurangiWaktu(int waktu){
        this.waktu -= waktu;
    }
}
