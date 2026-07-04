import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TWR {

    public static void main(String[] args) {

        try (FileOutputStream out = new FileOutputStream("out"); FileInputStream in = new FileInputStream("in")) {
            out.write(in.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
