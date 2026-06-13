// Variable being declared as a var cannot be referenced in the initializer.
// .result=COMPILE_FAIL
public class Test {
  public void f(){
    var x = (x = 2);
  }
}
