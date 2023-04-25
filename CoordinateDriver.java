public class CoordinateDriver {
    public static void main(String[] args) {
        Coordinate coordinate1 = new Coordinate(2, 3);
        Coordinate coordinate2 = new Coordinate(5, 7);

        System.out.println("Coordinate 1: (" + coordinate1.getX() + ", " + coordinate1.getY() + ")");
        System.out.println("Coordinate 2: (" + coordinate2.getX() + ", " + coordinate2.getY() + ")");

        coordinate1.setX(4);
        coordinate1.setY(6);

        System.out.println("New coordinate 1: (" + coordinate1.getX() + ", " + coordinate1.getY() + ")");
    }
}

