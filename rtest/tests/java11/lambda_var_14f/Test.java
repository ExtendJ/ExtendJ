// .result=COMPILE_FAIL
public class Test {
  {
    Hair ball = (var hair, ball) -> { }; // Parse error: cannot mix var and implicitly typed parameters.
  }
}
interface Hair {
  void hairball(float uu, float vv);
}
