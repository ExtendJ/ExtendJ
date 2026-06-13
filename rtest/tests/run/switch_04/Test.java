// Test fallthrough in lookupswitch.
public class Test {
  public static void main(String[] args) {
    new Test().run();
  }

  void run() {
    for (short i = 40; i < 1000; ++i) {
      bamboozle(i);
    }
  }

  void bamboozle(short i) {
    switch (i) {
      case 730:
        System.out.print("x");
      case 364:
        System.out.print("y");
      case 318:
        System.out.print("z");
      case 129:
        System.out.println("? marks the spot");
        break;
      case 5:
        System.out.print("v");
        break;
      case 1:
        System.out.print("w");
        break;
    }
  }
}
