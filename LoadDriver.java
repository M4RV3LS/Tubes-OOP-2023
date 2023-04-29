public class LoadDriver {
    public static void main(String[] args) {
        String filename = "data.txt";

        // membuat objek Load
        Load load = new Load(filename);

        // memuat data dari file pada path tertentu
        String path = "C:\\Users\\mirac\\Downloads\\test";
        load.load(path);

        // mencetak data yang telah dibaca dari file
        System.out.println(load.getData());
    }
}

