// .result=EXEC_PASS
/*
 * Allow effectively final variables to be used 
 * as resources in the try-with-resources statement
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Test {

    public static void main(String[] args) throws Exception {
        testManyResources();
        testManyStdResources();
    }

    // This only works in java9 and onwards
    static void testManyResources() throws Exception {
        Reader inputString = new StringReader("Hello World");
        final BufferedReader br1 = new BufferedReader(inputString);
        BufferedReader br2 = new BufferedReader(inputString);

        try (BufferedReader br3 = new BufferedReader(inputString);
                BufferedReader br4 = new BufferedReader(inputString);
                BufferedReader br5 = new BufferedReader(inputString);
                br1;
                BufferedReader br6 = new BufferedReader(inputString);
                BufferedReader br7 = new BufferedReader(inputString);
                BufferedReader br8 = new BufferedReader(inputString);
                br2) {
            br1.readLine();
        }
    }

    // This also works before Java 9
    static void testManyStdResources() throws Exception {
        Reader inputString = new StringReader("Hello World");
        try (BufferedReader br1 = new BufferedReader(inputString);
                BufferedReader br2 = new BufferedReader(inputString);
                BufferedReader br3 = new BufferedReader(inputString);
                BufferedReader br4 = new BufferedReader(inputString);
                BufferedReader br5 = new BufferedReader(inputString);
                BufferedReader br6 = new BufferedReader(inputString);
                BufferedReader br7 = new BufferedReader(inputString);
                BufferedReader br8 = new BufferedReader(inputString);) {
            br1.readLine();
        } catch (Exception e) {
        }
    }
}