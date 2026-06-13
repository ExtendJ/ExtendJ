// .result=EXEC_PASS

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Test {
    public static void main(String[] args) throws Exception {
        Reader inputString = new StringReader("Hello World");
        BufferedReader br = new BufferedReader(inputString);
        test(br);
    }

    static void test(BufferedReader br1) throws Exception {
        Reader inputString = new StringReader("Hello World");
        try (br1;
        BufferedReader br2 = new BufferedReader(inputString)) {
            br1.readLine();
        }
    }
}