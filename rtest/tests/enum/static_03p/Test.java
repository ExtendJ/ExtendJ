// Test that static initializers for fields in an enum type are run
// after the enum $VALUES field has been initialized.
// https://bitbucket.org/extendj/extendj/issues/291/enum-initializer-fails-when-a-static
public class Test {
  public static void main(String[] args) {
    for (EnumInitTest v : EnumInitTest.values) {
      System.out.println(v);
    }
  }
}

enum EnumInitTest {
  HI,
  MOM;

  static EnumInitTest[] values = values();
}
