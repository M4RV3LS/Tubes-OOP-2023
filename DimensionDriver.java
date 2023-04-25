public class DimensionDriver {
    public static void main(String[] args) {
        Dimension dimension = new Dimension(5, 10);

        System.out.println("Dimension: " + dimension.Dimension());
        System.out.println("Width: " + dimension.getWidth());
        System.out.println("Length: " + dimension.getLength());

        dimension.setWidth(8);
        dimension.setLength(15);

        System.out.println("New dimension: " + dimension.Dimension());
    }
}
