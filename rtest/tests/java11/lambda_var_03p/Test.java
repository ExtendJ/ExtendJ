// .result=EXEC_PASS
import java.util.ArrayList;
import java.util.function.Consumer;

public class Test {
  public static void main(String[] args) {
    ArrayList<Integer> numbers = new ArrayList<>();
    numbers.add(5);
    numbers.add(9);
    numbers.add(8);
    numbers.add(1);
    Consumer<Integer> method = (var n) -> System.out.println(n);
    numbers.forEach(method);
  }
}
