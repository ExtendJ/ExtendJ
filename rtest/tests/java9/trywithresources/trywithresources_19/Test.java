// .result=EXEC_PASS


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Test {
   public static void main(String[] args) throws IOException {
      readData("test");
   } 
   static String readData(String message) throws IOException {
      Reader inputString = new StringReader(message);
      BufferedReader br = new BufferedReader(inputString);
      try (BufferedReader br1 = br; BufferedReader br2 = br1; BufferedReader br3 = br2; BufferedReader br4 = br3) {
         return br4.readLine();
      }
   }
}