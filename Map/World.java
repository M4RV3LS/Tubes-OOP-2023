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
                    System.out.print("H ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public int[] getHouseLocation(House house) {
        return houseLocations.get(house);
    }

    public Set<House> getHouses() {
        return houseLocations.keySet();
    }

}
