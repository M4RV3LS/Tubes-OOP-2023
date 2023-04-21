package Bagian;

import java.awt.*;

public class simLoc {
    private House house;
    private Room room;

    public simLoc(House house) 
    {
        this.house = house;
        // this.room = room;
    }

    public Room getRuangan() {
        return room;
    }

    public House getRumah() {
        return house;
    }

    public void setRuangan(Room room) {
        this.room = room;
    }

    public void setRumah(House house) {
        this.house = house;
    }
}