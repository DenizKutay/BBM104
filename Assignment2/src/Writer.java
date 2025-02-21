import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    static BufferedWriter writer;
    public static String file;

    static void open() {
        try {
            writer = new BufferedWriter(new FileWriter(file, false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Print(String string, boolean append) {
        try {

            writer.write(string);
            writer.newLine();
            if (!append) {
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

}

