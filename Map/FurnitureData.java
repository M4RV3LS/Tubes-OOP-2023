package Map;
import Inventory.*;
public class FurnitureData {
    private String furnitureName;
    private String initialName;
    private int startX;
    private int startY;
    private Dimension dimension;
    private String direction;

    public FurnitureData(String furnitureName , String initialName , int startX, int startY, Dimension dimension, String direction) {
        this.furnitureName = furnitureName;
        this.initialName = initialName;
        this.startX = startX;
        this.startY = startY;
        this.dimension = dimension;
        this.direction = direction;
    }

    // getters and setters
    public String getFurnitureName() {
        return furnitureName;
    }

    public String getInitialName() {
        return initialName;
    }

    public void setFurnitureName(String furnitureName) {
        this.furnitureName = furnitureName;
    }

    public void setInitialName(String initialName) {
        this.initialName = initialName;
    }
    
    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
