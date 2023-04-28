package Map;
import java.util.HashMap;
import java.util.Set;

public class World {
    private static World instance = null;
    private House[][] grid = new House[64][64];
    private HashMap<House, int[]> houseLocations = new HashMap<>();

    private World() {}

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public House getHouse(int x, int y) {
        return grid[x][y];
    }

    public void setHouse(int x, int y, House house) {
        grid[x][y] = house;
        houseLocations.put(house, new int[]{x, y});
    }

    public void printWorld() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                if (grid[i][j] != null) {
                    System.out.print(this.getHouse(i,j).getHouseName());
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }

        // int maxWordLength = 3; // maksimum panjang kata 3

        // int boxWidth = maxWordLength + 2; // lebar kotak disesuaikan dengan panjang kata maksimum
        

        // // print baris pertama
        // for (int allMap = 0 ; allMap < 64 ; allMap++){
        //     for (int i = 0; i < 64; i++) {
        //         System.out.print("+");
        //         for (int j = 0; j < boxWidth - 1; j++) {
        //             System.out.print("-");
        //         }
        //     }
        //     System.out.println("+");

        //     // print baris kedua sampai satu sebelum terakhir
            
        //         for (int i = 0; i < 64; i++) {
        //             System.out.print("|");
        //             // Coordinate coordinate = new Coordinate(allMap, i);
        //             // String value = this.mapData.getOrDefault(coordinate, "");
        //             // int valueLength = value.length();
        //             int spaces = (4 - 3) / 2;
        //             for (int j = 0; j < spaces; j++) {
        //                 System.out.print(" ");
        //             }
        //             if(this.getHouse(allMap , i) != null){
        //                 System.out.print(this.getHouse(allMap , i).getHouseName());
        //             }
        //             else{
        //                 System.out.print("   ");

        //             }
                    
                    
        //             for (int j = 0; j < 4 - spaces - 3; j++) {
        //                 System.out.print(" ");
        //             }
                    
        //         }
        //         System.out.print("|");
        //         System.out.println();
                
            
        // }
        // // print baris terakhir
        // for (int i = 0; i < 64; i++) {
        //     System.out.print("+");
        //     for (int j = 0; j < boxWidth - 1; j++) {
        //         System.out.print("-");
        //     }
        // }
        // System.out.println("+");
    }

    public int[] getHouseLocation(House house) {
        return houseLocations.get(house);
    }

    public Set<House> getHouses() {
        return houseLocations.keySet();
    }

    public HashMap<House, int[]> getHouseList() {
        return houseLocations;
    }
    

}
