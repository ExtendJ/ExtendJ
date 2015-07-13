// .result=COMPILE_PASS
public class Test {
  Object f = cond()
      ? new Object()
      : o==null ? null : o;

  boolean cond() {
    return System.currentTimeMillis() % 2 == 1;
  }
}
