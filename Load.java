import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Load {
    private String filename;
    private String data;

    public Load(String filename) {
        this.filename = filename;
    }

    public void load(String path) {
        try {
            File file = new File(path + "\\" + filename);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();
            bufferedReader.close();
            reader.close();
            System.out.println("Data berhasil dimuat dari file " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan dalam membaca file " + filename);
            e.printStackTrace();
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
