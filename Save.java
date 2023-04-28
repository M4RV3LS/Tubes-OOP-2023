import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
    private String filename;
    private String data;

    public Save(String filename, String data) {
        this.filename = filename;
        this.data = data;
    }

    public void save(String path) {
        try {
            // membuat objek file dengan path dan nama file
            File file = new File(path + "\\" + filename);

            // membuat file baru jika file tidak ditemukan
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
            System.out.println("Data telah berhasil disimpan ke dalam file " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan dalam menyimpan data ke dalam file " + filename);
            e.printStackTrace();
        }
    }

    // getter dan setter untuk atribut filename dan data
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
