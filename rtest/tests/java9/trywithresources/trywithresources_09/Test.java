// .result=EXEC_PASS
/*
 * Allow effectively final variables to be used 
 * as resources in the try-with-resources statement
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Test extends Super {
    public Test() throws Exception {
        super();
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.test();
    }

    // This only works in java9 and onwards
    void test() throws Exception {
        Reader inputString = new StringReader("Hello World");
        try (br1;
                BufferedReader br2 = new BufferedReader(inputString);) {
            br1.readLine();
        }
    }
}