// Underscores do not change the literal's value.
// .result=COMPILE_PASS
public class Test {
  void foo(int i) {
    switch (i) {
      case 0:
      case ((10 == 1_0) ? 1 : 0):
      case ((10 == 1__0) ? 2 : 0):
      case ((10 == 1______0) ? 3 : 0):
      case ((0 == 0_0_0) ? 4 : 0):
    }
  }
}
