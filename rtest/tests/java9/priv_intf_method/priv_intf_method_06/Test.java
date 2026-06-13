// .result=EXEC_PASS

// test name shadowing a private interface method in implementing class and extending interface
public class Test implements Intf{
 public static void main(String[] args) {
      Test test = new Test();
      test.concreteMethod();
  }

  public void helperSuper(){
    //
  }
}