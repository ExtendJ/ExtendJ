// Test using the Enum.valueOf(String) method on a standard library type.
// See issue 282.
import java.net.Proxy;
public class Test {
  public static void main(String[] args) {
    System.out.println(Proxy.Type.valueOf("HTTP"));
  }
}
