// .result=COMPILE_FAIL

import java.util.ArrayList;
import java.util.function.Consumer;

public class Test {
  public static void main(String[] args) {
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(5);
    numbers.add(9);
    numbers.add(8);
    numbers.add(1);
    var method = (n) -> {};
    numbers.forEach( method );
  }
}

