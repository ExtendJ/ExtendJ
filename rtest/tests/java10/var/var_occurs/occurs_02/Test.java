// Variable being declared as a var cannot be referenced in the initializer.
// .result=COMPILE_FAIL

public class Test {
  public void f(){
    var x = h(g(2, h((x = 2))));
  }

  public int h(int a){
    return 1;
  }
  public int g(int b, int a){
    return 1;
  }
}
