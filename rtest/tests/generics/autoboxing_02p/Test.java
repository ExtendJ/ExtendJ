// Test that Integer unboxing works in runtime.
// https://bitbucket.org/extendj/extendj/issues/257/missing-checkcast-before-unboxing
import java.util.*;

public class Test {
  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(10);
    stack.push(-20);
    stack.push(50);
    stack.pop();
    System.out.println(peek(stack));
  }

  static int peek(Stack<Integer> stack) {
    int res = 0;
    res += stack.peek(); // Conversion: Integer -> int.
    return res;
  }
}
