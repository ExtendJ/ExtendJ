// Test using the Enum.valueOf(String) method on a standard library type.
// https://bitbucket.org/extendj/extendj/issues/282/enumvalueof-call-fails-with-ambiguous
import java.net.Proxy;
public class Test {
  public static void main(String[] args) {
    System.out.println(Proxy.Type.valueOf("HTTP"));
  }
}
