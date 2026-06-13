import java.io.*;

public class Test {
  public static void main(String[] args) throws IOException {
    new Test().run();
  }

  void run() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (DataOutputStream ds = new DataOutputStream(bytes);
        PrintStream out = new PrintStream(ds)) {
      int i = 0;
      for (; i < 2; ++i) {
        out.print("xy");
      }
      out.print(" ");
      out.print("marks the spot");
    }
    String res = new String(bytes.toByteArray());
    System.out.println(res);
  }
}
