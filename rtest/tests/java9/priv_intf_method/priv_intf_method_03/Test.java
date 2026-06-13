// .result=COMPILE_FAIL
public class Test implements Intf{
 public static void main(String[] args) {
      Test test = new Test();
      test.helper(); // Cannot call a private method
  }
}