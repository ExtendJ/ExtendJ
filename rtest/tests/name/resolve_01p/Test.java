// Test for nested name resolution with class instance expression
// as qualifying expression.
// .result: COMPILE_PASS

public class Test {
  int len1() {
    return new Other().thing.length;
  }
  int len2() {
    return new Other().name.length();
  }
}

class Other {
  public String name = "Burt";
  public Thing thing;
}

class Thing {
  public int length = -1;
}

