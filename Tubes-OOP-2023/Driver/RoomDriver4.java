
import Map.*;
import Inventory.*;
public class RoomDriver4 {
    public static void main(String[] args) {
        Room room1 = new Room("Living Room", null, null, null, null);
        
        try {
            // Place a sofa in the living room
            Boolean sofa = room1.placeFurniture(0, 0, new Dimension(2, 1), "sof", "right");
            if (sofa){
                System.out.println("Sofa berhasil diletakkan di ruangan");
            }

            
            // // Place a coffee table in the living room
            // Dimension coffeeTableDim = new Dimension(1, 1);
            // room1.placeFurniture(2, 0, coffeeTableDim, "Cft", "Right");
            // System.out.println("Successfully placed coffee table in the living room");
            
            // // Place a lamp in the living room
            // Dimension lampDim = new Dimension(1, 1);
            // room1.placeFurniture(0, 2, lampDim, "Lam", "Down");
            // System.out.println("Successfully placed lamp in the living room");
            
            // // Attempt to place a bookcase in the living room that is too large
            // Dimension bookcaseDim = new Dimension(3, 1);
            // room1.placeFurniture(3, 0, bookcaseDim, "Boo", "Right");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Living room layout:");
        room1.printRoom();
    }
}
