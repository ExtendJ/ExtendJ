// .result=EXEC_PASS
/*
 * Allow effectively final variables to be used 
 * as resources in the try-with-resources statement
 */

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.Reader;
 import java.io.StringReader;

public class Test{

    public static void main(String[] args) {
        try{
            testEffFinal();

        }catch(Exception e){

        }
    }
    // This only works in java9 and onwards since scanner is final
    static void testEffFinal() throws Exception{
        Reader inputString = new StringReader("Hello World");
        final BufferedReader br = new BufferedReader(inputString);
        try(br) {
            br.readLine();
        }
    }
}