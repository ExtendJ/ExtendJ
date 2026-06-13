// Initializer cannot reference the varaible being initilized.
// .result=COMPILE_FAIL
public class Test {
  public static void main (String[] args) {
    var x = (x=2)+1;
  }
}
