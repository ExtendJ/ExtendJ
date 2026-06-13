// Variable being declared as a var cannot be referenced in the initializer.
// Arithmetic tests
// .result=COMPILE_FAIL
public class Test {
  public void f(){
    var x = ++x;
    var xx = xx++;
    var y = --y;
    var z = z & 1;
    var t = t + 1;
    var r = 1 + (r=1);
    var e = e;
    var w = 1+(1-w)+2;
    var q = 1/q;
    var a = 2*a*2*1;
  }
}
