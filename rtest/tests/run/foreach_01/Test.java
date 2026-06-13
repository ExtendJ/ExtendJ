// Test that a foreach loop iterating over a byte array runs the correct number of iterations.
public class Test {
  public static void main(String[] args) {
    int count = 0;
    for (int i : new byte[131]){
      ++count;
    }
    System.out.println(count);
  }
}
