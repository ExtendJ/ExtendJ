// .result=EXEC_PASS

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
/* Baseline test that should work before and after java 9 */
public class Test{
    public static void main(String[] args) throws Exception {
        testBasic();
    }
   // A normal try-with-resources stmt
   static void testBasic() throws Exception{
    Reader inputString = new StringReader("Hello World");
    
       try(BufferedReader br = new BufferedReader(inputString)) {}
   }
}