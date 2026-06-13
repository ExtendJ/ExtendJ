// Annotations cannot have a multi-dimensional array component.
// .result=COMPILE_FAIL

@interface Annot {
  String[][] names(); // Invalid annotation member type.
}

public class Test {
  public void f() {
  }
}
