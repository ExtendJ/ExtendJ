// Test nested try statement control flow.
// https://bitbucket.org/extendj/extendj/issues/241/broken-bytecode-in-nested-try-statements
public class Test {

  public static void main(String[] args) {
    run(1);
    run(2);
  }

  static void run(int i) {
    int target = 0;
Outer:
    {
      target |= 1;
      try {
        try {
          target |= 2;
          if (i < 2)
            break Outer;
          target |= 4;
        } finally {
          target |= 8;
          break Outer;
        }
      } finally {
        target |= 16;
      }
    }
    target |= 32;
    System.out.println(Integer.toString(target, 2));
  }
}
