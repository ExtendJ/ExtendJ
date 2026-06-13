// .result=EXEC_PASS
/*
 * Allow final field variables to be used 
 * as resources in the try-with-resources statement
 */

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.Reader;
 import java.io.StringReader;

public class Test {
    final BufferedReader br1;
    final BufferedReader br2;

    public Test() throws Exception {
        Reader inputString = new StringReader("Hello World");
        br1 = new BufferedReader(inputString);
        br2 = new BufferedReader(inputString);
    }

    public static void main(String[] args) throws Exception {
            Test test = new Test();
            test.test();
    }

    public void test() throws Exception {
        try (br1; br2;) {
        } catch (Exception e) {
        }
    }
}