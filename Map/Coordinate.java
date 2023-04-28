package Map;
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Coordinate coordinate) {
        return this.x == coordinate.getX() && this.y == coordinate.getY();
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void shiftLeft(int digeserSejauh){
        this.x -= digeserSejauh;
    }

    public void shiftRight(int digeserSejauh){
        this.x += digeserSejauh;
    }

    public void shiftUp(int digeserSejauh){
        this.y -= digeserSejauh;
    }

    public void shiftDown(int digeserSejauh){
        this.y += digeserSejauh;
    }

    public void shift(int x, int y){
        this.x += x;
        this.y += y;
    }

    
}
