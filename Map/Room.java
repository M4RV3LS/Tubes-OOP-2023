package Map;
import java.util.ArrayList;
import java.util.*;
import Inventory.*;

public class Room {
    private int width = 6;
    private int height = 6;
    private String roomName;
    private HashMap<MyObject, Integer> objectCounts; // perubahan ini
    private Room roomUp;
    private Room roomDown;
    private Room roomLeft;
    private Room roomRight;
    private String[][] layout;
    


    public Room(String roomName , Room roomUp , Room roomRight , Room roomDown , Room roomLeft) {
        this.roomName = roomName;
        this.roomUp = roomUp;
        this.roomRight = roomRight;
        this.roomDown = roomDown;
        this.roomLeft = roomLeft;
        layout = new String[6][6];
        for (int i = 0; i < layout.length; i++) {
            Arrays.fill(layout[i], "");
        }
    //     this.layout = new String[][]{
    //         {"KKS", "KKS", "KKS", "", "", ""},
    //         {"KKS", "KKS", "KKS", "", "", ""},
    //         {"", "", "", "", "", ""},
    //         {"", "", "", "", "", ""},
    //         {"", "", "", "", "", ""},
    //         {"", "", "", "", "", ""}
    // };
        // this.mapData = new HashMap<>();
        // for (int i = 0; i < layout.length; i++) {
        //     for (int j = 0; j < layout[i].length; j++) {
        //         if (!layout[i][j].equals("")) {
        //             Coordinate coord = new Coordinate(i, j);
        //             mapData.put(coord, layout[i][j]);
        //         }
        //     }
        // }
        objectCounts = new HashMap<MyObject, Integer>(); // inisialisasi HashMap
    }

    public String[][] getLayout() {
        return layout;
    }

    public String getLayoutContent(int i , int j){
        return layout[i][j];
    }

    public String[] getLayoutRow(int i){
        return layout[i];
    }

    // public boolean addObject(MyObject obj) {
    //     if (objects.size() < width * height) {
    //         objects.add(obj);
    //         return true;
    //     }
    //     return false;
    // }

    public void addObject(MyObject object) {
        if (objectCounts.containsKey(object)) { // jika objek sudah ada di dalam HashMap
            int count = objectCounts.get(object);
            objectCounts.put(object, count + 1); // tambahkan jumlah barang
        } else {
            objectCounts.put(object, 1); // tambahkan objek baru ke HashMap dengan jumlah 1
        }
    }


    public boolean removeObject(MyObject obj) {
        if (objectCounts.containsKey(obj)) {
            int jumlahBarang = objectCounts.get(obj);
            if (jumlahBarang > 1) {
                objectCounts.put(obj, jumlahBarang - 1);
            } else {
                objectCounts.remove(obj);
            }
            return true;
        } else {
            return false;
        }
    }
    
    public HashMap<MyObject, Integer> getObjectCounts() {
        return objectCounts;
    }
    
    //Print HashMap
    public void printObjectCounts() {
        for (Map.Entry<MyObject, Integer> entry : objectCounts.entrySet()) {
            System.out.println(entry.getKey().toString() + ": " + entry.getValue());
        }
    }
    

    public Room getRoomUp() {
        return roomUp;
    }

    public void setRoomUp(Room roomUp) {
        this.roomUp = roomUp;
    }

    public Room getRoomDown() {
        return roomDown;
    }

    public void setRoomDown(Room roomDown) {
        this.roomDown = roomDown;
    }

    public Room getRoomLeft() {
        return roomLeft;
    }

    public void setRoomLeft(Room roomLeft) {
        this.roomLeft = roomLeft;
    }

    public Room getRoomRight() {
        return roomRight;
    }

    public void setRoomRight(Room roomRight) {
        this.roomRight = roomRight;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isConnected(Room room) {
        if (this.roomUp == room || this.roomDown == room || this.roomLeft == room || this.roomRight == room) {
            return true;
        }
        return false;
    }

    // public void updateMapData(){
    //     HashMap<Coordinate, String> newMapData = new HashMap<>();
    //     for (int i = 0; i < this.layout.length; i++) {
    //         for (int j = 0; j < this.layout[i].length; j++) {
    //             if (!(this.layout[i][j].equals(""))) {
    //                 Coordinate coord = new Coordinate(i, j);
    //                 newMapData.put(coord, layout[i][j]);
    //             }
    //         }
    //     }
    //     this.mapData = newMapData;
    // }

    public void printRoom() {
        int maxWordLength = 3; // maksimum panjang kata 3

        int boxWidth = maxWordLength + 2; // lebar kotak disesuaikan dengan panjang kata maksimum
        

        // print baris pertama
        for (int allMap = 0 ; allMap < 6 ; allMap++){
            for (int i = 0; i < 6; i++) {
                System.out.print("+");
                for (int j = 0; j < boxWidth - 1; j++) {
                    System.out.print("-");
                }
            }
            System.out.println("+");

            // print baris kedua sampai satu sebelum terakhir
            
                for (int i = 0; i < 6; i++) {
                    System.out.print("|");
                    // Coordinate coordinate = new Coordinate(allMap, i);
                    // String value = this.mapData.getOrDefault(coordinate, "");
                    // int valueLength = value.length();
                    int spaces = (4 - 3) / 2;
                    for (int j = 0; j < spaces; j++) {
                        System.out.print(" ");
                    }
                    if(this.getLayoutContent(allMap , i) == ""){
                        System.out.print("   ");
                    }
                    else{
                        System.out.print(this.getLayoutContent(allMap , i));
                    }
                    
                    for (int j = 0; j < 4 - spaces - 3; j++) {
                        System.out.print(" ");
                    }
                    
                }
                System.out.print("|");
                System.out.println();
                
            
        }
        // print baris terakhir
        for (int i = 0; i < 6; i++) {
            System.out.print("+");
            for (int j = 0; j < boxWidth - 1; j++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    // public Boolean placeFurniture(int startX, int startY, Dimension dimension, String furniture, Boolean horizontal) throws Exception {
    //     Boolean valid = true;
    //     if (startX < 0 || startX >= 6 || startY < 0 || startY >= 6) {
    //         valid = false;
    //         throw new Exception("Input coordinates are out of bounds");
    //     }
    //     if (!horizontal) {
    //         if (startX + dimension.getLength() - 1 > 5 || startY + dimension.getWidth() - 1 > 5) {
    //             valid = false;
    //             throw new Exception("Furniture dimensions are too large");
    //         }
    //     } else {
    //         if (startX + dimension.getWidth() - 1 > 5 || startY + dimension.getLength() - 1 > 5) {
    //             valid = false;
    //             throw new Exception("Furniture dimensions are too large");
    //         }
    //     }
    
    //     if (horizontal && valid) {
    //         for (int i = startX; i < startX + dimension.getWidth(); i++) {
    //             for (int j = startY; j < startY + dimension.getLength(); j++) {
    //                 if (layout[i][j] != "") {
    //                     valid = false;
    //                     throw new Exception("Furniture placement overlaps with existing furniture");
    //                 }
    //             }
    //         }
    //         if (valid) {
    //             for (int i = startX; i < startX + dimension.getWidth(); i++) {
    //                 for (int j = startY; j < startY + dimension.getLength(); j++) {
    //                     layout[i][j] = furniture;
    //                 }
    //             }
    //         }
    //     } else if (!horizontal && valid) {
    //         for (int i = startX; i < startX + dimension.getLength(); i++) {
    //             for (int j = startY; j < startY + dimension.getWidth(); j++) {
    //                 if (layout[i][j] != "") {
    //                     valid = false;
    //                     throw new Exception("Furniture placement overlaps with existing furniture");
    //                 }
    //             }
    //         }
    //         if (valid) {
    //             for (int i = startX; i < startX + dimension.getLength(); i++) {
    //                 for (int j = startY; j < startY + dimension.getWidth(); j++) {
    //                     layout[i][j] = furniture;
    //                 }
    //             }
    //         }
    //     }
    
    //     // if(valid){
    //     //     updateMapData();
    //     // }
    //     return valid;
    // }

    // public Boolean placeFurniture(int startX, int startY, Dimension dimension, String furniture, Boolean right , Boolean left , Boolean up , Boolean down) throws Exception {
    //     Boolean valid = true;
    //     if (startX < 0 || startX >= 6 || startY < 0 || startY >= 6) {
    //         valid = false;
    //         throw new Exception("Input coordinates are out of bounds");
            
    //     }
    //     if (right) {
    //         System.out.println("Masuk Right");
    //         if (startX + dimension.getLength() - 1 > 5 || startY + dimension.getWidth() - 1 > 5) {
    //             valid = false;
    //             throw new Exception("Furniture dimensions are too large");
                
    //         }
    //     else if(left){
    //         System.out.println("Masuk Left");
    //         if (startX - dimension.getLength() + 1 < 0 || startY + dimension.getWidth() - 1 > 5) {
    //             valid = false;
    //             throw new Exception("Furniture dimensions are too large");
                
    //     }
    //     } else if (down) {
    //         System.out.println("Masuk Down");
    //         if (startX + dimension.getWidth() - 1 > 5 || startY + dimension.getLength() - 1 > 5) {
    //             valid = false;
    //             throw new Exception("Furniture dimensions are too large");
                
    //         }
    //     } else if (up) {
    //         System.out.println("Masuk Up");
    //         if (startX + dimension.getWidth() - 1 > 5 || startY - dimension.getLength() + 1 < 0 ) {
    //             valid = false;
    //             throw new Exception("Furniture dimensions are too large");
                
    //         }
    //     } else {
    //         valid = false;
    //         throw new Exception("Invalid direction specified");
    //     }
    
        // if (right && valid) {
        //     System.out.println("Masuk Right 2");
        //     for (int i = startY; i < startY + dimension.getWidth(); i++) {
        //         for (int j = startX; j < startX + dimension.getLength(); j++) {
        //             if (layout[i][j] != "") {
        //                 valid = false;
        //                 throw new Exception("Furniture placement overlaps with existing furniture");
        //             }
        //         }
        //     }
        //     if (valid) {
        //         for (int i = startY; i < startY + dimension.getWidth(); i++) {
        //             for (int j = startX; j < startX + dimension.getLength(); j++) {
        //                 layout[i][j] = furniture;
        //             }
        //         }
        //     }
        // } else if (left && valid) {
        //     System.out.println("Masuk Left 2");
        //     for (int i = startY; i < startY + dimension.getWidth(); i++) {
        //         for (int j = startY; j > startY - dimension.getLength() + 1; j--) {
        //             if (layout[i][j] != "") {
        //                 valid = false;
        //                 throw new Exception("Furniture placement overlaps with existing furniture");
        //             }
        //         }
        //     }
        //     if (valid) {
        //         for (int i = startY; i < startY + dimension.getWidth(); i++) {
        //             for (int j = startY; j > startY - dimension.getLength() + 1; j--) {
        //                 layout[i][j] = furniture;
        //             }
        //         }
        //     }
        // } else if (down && valid) {
        //     System.out.println("Masuk Down 2");
        //     for (int i = startY; i < startY + dimension.getLength(); i++) {
        //         for (int j = startX; j < startX + dimension.getWidth(); j++) {
        //             if (layout[i][j] != "") {
        //                 valid = false;
        //                 throw new Exception("Furniture placement overlaps with existing furniture");
        //             }
        //         }
        //     }
        //     if (valid) {
        //         for (int i = startY; i < startY + dimension.getLength(); i++) {
        //             for (int j = startX; j < startX + dimension.getWidth(); j++) {
        //                 layout[i][j] = furniture;
        //             }
        //         }
        //     }
        // } else if (up && valid) {
        //     System.out.println("Masuk Up 2");
        //     for (int i = startY; i > startY - dimension.getLength(); i--) {
        //         for (int j = startX; j < startX + dimension.getWidth(); j++) {
        //             if (layout[i][j] != "") {
        //                 valid = false;
        //                 throw new Exception("Furniture placement overlaps with existing furniture");
        //             }
        //         }
        //     }
        //     if (valid) {
        //         for (int i = startY; i > startY - dimension.getLength(); i--) {
        //             for (int j = startX; j < startX + dimension.getWidth(); j++) {
        //                 layout[i][j] = furniture;
        //             }
        //         }
        //     }
        // }

    // }
    //     return valid;
    // }

    public boolean placeFurniture(int startX, int startY, Dimension dimension, String furniture, String direction) throws Exception {
        boolean valid = true;
        if (startX < 0 || startY < 0 || startX > 5 || startY > 5) {
            valid = false;
            throw new Exception("Invalid starting position");
        }
        int endX = startX;
        int endY = startY;
        if (direction.equalsIgnoreCase("Right")) {
            endX = startX + dimension.getLength();
            endY = startY + dimension.getWidth() - 1;
        } else if (direction.equalsIgnoreCase("Left")) {
            endX = startX - dimension.getLength() + 1;
            endY = startY + dimension.getWidth() - 1;
        } else if (direction.equalsIgnoreCase("Up")) {
            endX = startX + dimension.getWidth() - 1;
            endY = startY - dimension.getLength() + 1;
        } else if (direction.equalsIgnoreCase("Down")) {
            endX = startX + dimension.getWidth() - 1;
            endY = startY + dimension.getLength() - 1;
        }
        if (endX < 0 || endY < 0 || endX > 5 || endY > 5) {
            valid = false;
            throw new Exception("Furniture dimensions are too large");
        }
        if (direction.equalsIgnoreCase("Right") && valid) {
            System.out.println("Masuk Right 2");
            for (int i = startY; i < startY + dimension.getWidth(); i++) {
                for (int j = startX; j < startX + dimension.getLength(); j++) {
                    if (layout[i][j] != "") {
                        valid = false;
                        throw new Exception("Furniture placement overlaps with existing furniture");
                    }
                }
            }
            if (valid) {
                for (int i = startY; i < startY + dimension.getWidth(); i++) {
                    for (int j = startX; j < startX + dimension.getLength(); j++) {
                        layout[i][j] = furniture;
                    }
                }
                return true;
            }
        } else if (direction.equalsIgnoreCase("Left") && valid) {
            System.out.println("Masuk Left 2");
            for (int i = startY; i < startY + dimension.getWidth(); i++) {
                for (int j = startX; j > startX - dimension.getLength(); j--) {
                    if (layout[i][j] != "") {
                        valid = false;
                        throw new Exception("Furniture placement overlaps with existing furniture");
                    }
                }
            }
            if (valid) {
                for (int i = startY; i < startY + dimension.getWidth(); i++) {
                    for (int j = startX; j > startX - dimension.getLength() ; j--) {
                        layout[i][j] = furniture;
                    }
                }
                return true;
            }
        } else if (direction.equalsIgnoreCase("Down") && valid) {
            System.out.println("Masuk Down 2");
            for (int i = startY; i < startY + dimension.getLength(); i++) {
                for (int j = startX; j < startX + dimension.getWidth(); j++) {
                    if (layout[i][j] != "") {
                        valid = false;
                        throw new Exception("Furniture placement overlaps with existing furniture");
                    }
                }
            }
            if (valid) {
                for (int i = startY; i < startY + dimension.getLength(); i++) {
                    for (int j = startX; j < startX + dimension.getWidth(); j++) {
                        layout[i][j] = furniture;
                    }
                }
                return true;
            }
        } else if (direction.equalsIgnoreCase("Up") && valid) {
            System.out.println("Masuk Up 2");
            for (int i = startY; i > startY - dimension.getLength(); i--) {
                for (int j = startX; j < startX + dimension.getWidth(); j++) {
                    if (layout[i][j] != "") {
                        valid = false;
                        throw new Exception("Furniture placement overlaps with existing furniture");
                    }
                }
            }
            if (valid) {
                for (int i = startY; i > startY - dimension.getLength(); i--) {
                    for (int j = startX; j < startX + dimension.getWidth(); j++) {
                        layout[i][j] = furniture;
                    }
                }
                return true;
            }
        }
        else {
            return false;
        }
        return valid;
    }
    } 
    

