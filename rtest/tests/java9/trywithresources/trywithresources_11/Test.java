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

    // This only works in java9 and onwards since scanner is effectively final
    public void testEffFinal() throws Exception {
        Reader inputString = new StringReader("Hello World");
        BufferedReader br;
        br = new BufferedReader(inputString);
        try (br) {
            br.readLine();
        } catch (Exception e) {
            System.out.println("testBasic Error");
        }
    }

    public static void main(String[] args) throws Exception {
            Test test = new Test();
            test.testEffFinal();
    }
}