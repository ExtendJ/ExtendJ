// This test adds a dead bytecode block before the switch instruction to
// test that the switch alignment is correctly adjusted after removing the
// dead block.
public class Test {
  public static void main(String[] args) {
    int x;
    for (int i = 1; true; ++i) { // Dead update statement.
      x = i;
      break;
    }
    System.out.println(x);
    int y = 0, z;
    switch (f()) {
      case 0:
        x += 5;
        y += 3;
        break;
      case 1:
        x += 10;
        y += 1;
      default:
        x += 20;
        y += 2;
        z = 3;
        break;
    }
    System.out.println(x + y);
  }

  static int f() {
    return 1;
  }
}
