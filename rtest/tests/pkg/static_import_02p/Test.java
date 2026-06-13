// .result=COMPILE_PASS
package pkg.test;

import static pkg.test.Test.VAL;

public final class Test implements Runnable {
  public static final int VAL = 8;

  Character.Subset f;

  @Override
  public void run() { }
}
