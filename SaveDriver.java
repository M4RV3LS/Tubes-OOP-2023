public class SaveDriver {
    public static void main(String[] args) {
        String filename = "data.txt";
        String data = "Ini adalah contoh data yang akan disimpan dalam file.";

        // membuat objek Save
        Save save = new Save(filename, data);

        // menyimpan data ke dalam file dengan path yang telah ditentukan
        String path = "C:\\Users\\mirac\\Downloads\\test";
        save.save(path);
    }
}

