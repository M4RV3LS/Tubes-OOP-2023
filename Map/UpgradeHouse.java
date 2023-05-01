package Map;
import Sim.*;
import Inventory.*;
import Fitur.*;
import java.util.*;

public class UpgradeHouse {
    private Sim sim ;
    private int waktuUpgrade;
    private House house;
    private Room roomAcuan;
    private String newRoom;
    private Boolean isUpgraded;
    private String direction;

    //constructor
    public UpgradeHouse(Sim sim , int waktuUpgrade , House house , Room roomAcuan , String newRoom , Boolean isUpgraded , String direction){
        this.sim = sim;
        this.waktuUpgrade = waktuUpgrade;
        this.house = house;
        this.roomAcuan = roomAcuan;
        this.newRoom = newRoom;
        this.isUpgraded = false;
        this.direction = direction;
    }

    //getter sim
    public Sim getSim(){
        return sim;
    }

    //getter waktuUpgrade
    public int getWaktuUpgrade(){
        return waktuUpgrade;
    }

    //getter House
    public House getHouse(){
        return house;
    }

    //getter roomAcuan
    public Room getRoomAcuan(){
        return roomAcuan;
    }

    //getter roomAcuan dengan parameter string roomName
    public Room getRoomUsingString(String roomName){
        for(Room simRoom : this.house.getRooms()) {
            if(simRoom.getRoomName().equals(roomName)) {
                return simRoom;
            }
        }
        return null;
    }

    //getter newRoom
    public String getNewRoom(){
        return newRoom;
    }

    //getter isUpgraded
    public Boolean getIsUpgraded(){
        return isUpgraded;
    }

    //getter direction
    public String getDirection(){
        return direction;
    }

    //setter house
    public void setHouse(House house){
        this.house = house;
    }

    //setter room
    public void setRoom(Room room){
        this.roomAcuan = room;
    }

    //Setter waktuUpgrade
    public void setWaktuUpgrade(int waktuUpgrade){
        this.waktuUpgrade = waktuUpgrade;
    }

    //Setter Sim
    public void setSim(Sim sim){
        this.sim = sim;
    }

    //setter isUpgraded
    public void setIsUpgraded(Boolean isUpgraded){
        this.isUpgraded = isUpgraded;
    }

    //setter direction
    public void setDirection(String direction){
        this.direction = direction;
    }

    //Setter roomAcuan
    public void setRoomAcuan(Room roomAcuan){
        this.roomAcuan = roomAcuan;
    }

    //Setter newRoom
    public void setNewRoom(String newRoom){
        this.newRoom = newRoom;
    }

    //kurangin waktu upgrade
    public void kurangWaktuUpgrade(int waktu){
        this.waktuUpgrade -= waktu;
    }
}



