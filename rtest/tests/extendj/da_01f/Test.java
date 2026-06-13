// .result=COMPILE_ERR_OUTPUT
class Test {
  final int i;

  Test() {
    int j = i;
    i = 3;
  }
}
