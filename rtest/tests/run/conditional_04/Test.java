// Test that the correct verification types are generated for a conditional
// expression with two different array types.
// https://bitbucket.org/extendj/extendj/issues/236/bad-stack-map-frame-when-using-conditional
public class Test {
  public static void main(String[] args) {
    new Test().run(true);
    new Test().run(false);
  } 
 
  void run(boolean b) {
    Float[] floats = new Float[3];
    System.out.println(process(b ? new Number[20] : floats));
  }

  int process(Object o) {
    return -1;
  }

  int process(Number[] n) {
    return 20;
  }
}
