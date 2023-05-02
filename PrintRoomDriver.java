import java.util.*;
import Map.*;

public class PrintRoomDriver{
    public static void main(String[] args) {
        ArrayList<String> data = new ArrayList<>();
        data.add("MATA");
        data.add("AIR");
        data.add("KAYU");
        data.add("BATU");
        data.add("TANAH");
        PrintRoom map = new PrintRoom(6, data);
        map.printMap();
    }
}