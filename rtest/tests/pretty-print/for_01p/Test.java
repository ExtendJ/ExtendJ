// .result=COMPILE_OUT
// .options=XprettyPrint
import java.util.Enumeration;
import java.util.Vector;

class Test {
  void f(Vector<Object> vector) {
    for (Enumeration<?> e = vector.elements(); ; ) {
    }
  }
}
