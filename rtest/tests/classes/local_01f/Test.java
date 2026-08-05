// A local class declaration is not accessible outside its immediately
// enclosing block.
// See issue 195.
// .result=COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    if (args.length == 2) {
      class Inner {
        public int i;

        public String toString() {
          return "i: " + i;
        }
      }
      Inner inner = new Inner();
      inner.i = args.length;
      System.out.println(inner);
    }

    // Error: Outside the scope where Inner was declared.
    Inner outer = new Inner();
  }
}
