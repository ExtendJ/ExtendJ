// .result=COMPILE_FAIL
/*
 * Allow effectively final variables to be used 
 * as resources in the try-with-resources statement
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Test {

    public static void main(String[] args) {
        try {
            Test test = new Test();
            test.testMethodAccess();
        } catch (Exception e) {

        }
    }

    private BufferedReader methodAccess() throws Exception {
        Reader inputString = new StringReader("Hello World");
        return new BufferedReader(inputString);
    }

    public void testMethodAccess() throws Exception{
        Reader inputString = new StringReader("Hello World");
        final BufferedReader br1 = new BufferedReader(inputString);
        try(methodAccess();
        BufferedReader br2 = methodAccess();
        BufferedReader br3 = new BufferedReader(inputString);
        ) {
            br1.readLin();
        }
    }
}