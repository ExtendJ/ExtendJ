// .result=COMPILE_FAIL
import java.util.*;

interface X { void execute(Number a); }
interface Y { void execute(Integer a); }

public class Test {
  @FunctionalInterface
  interface Exec extends X, Y { }
}
