package Map;
import java.util.ArrayList;

public class PrintRoom {
    private final int mapSize;
    private final ArrayList<String> mapData;

    public PrintRoom(int size, ArrayList<String> data) {
        this.mapSize = size;
        this.mapData = data;
    }

    public void printMap() {
        int maxWordLength = 4; // maksimum panjang kata 4

        int boxWidth = maxWordLength + 2; // lebar kotak disesuaikan dengan panjang kata maksimum
        int boxHeight = 3; // setiap kotak memiliki tinggi 3 karakter

        // print baris pertama
        for (int allMap = 0 ; allMap < mapSize ; allMap++){
            for (int i = 0; i < mapSize; i++) {
                System.out.print("+");
                for (int j = 0; j < boxWidth - 1; j++) {
                    System.out.print("-");
                }
            }
            System.out.println("+");
    
            // print baris kedua sampai satu sebelum terakhir
            for (int i = 0; i <= mapSize; i++) {
                System.out.print("|");
                for (int j = 0; j < boxWidth - 1; j++) {
                    System.out.print(" ");
                }
                            }
            System.out.println();
        }
        // print baris terakhir
        for (int i = 0; i < mapSize; i++) {
            System.out.print("+");
            for (int j = 0; j < boxWidth - 1; j++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
        
    }
}
