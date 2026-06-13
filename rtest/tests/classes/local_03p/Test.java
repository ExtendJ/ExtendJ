// A local class name can be reused in an outer block.
// .result=COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    if (args.length == 1) {
      class Local {}
    }
    class Local {}
  }
}
