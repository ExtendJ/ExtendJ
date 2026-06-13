// Regression test for exception handling issue.
// Exception checking interacts in intricate ways with type inference.
// https://bitbucket.org/extendj/extendj/issues/308/error-in-uncaught-exception-checking-for
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    new Test().m();
  }

  void m() {
    try {
      doExceptionally(() -> f());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  void f() throws Exception {
    throw new Exception("it works");
  }

  <E extends Throwable> void doExceptionally(ExceptionalListener<E> fun) throws E {
    fun.apply();
  }
}

interface ExceptionalListener<E extends Throwable> {
  void apply() throws E;
}
