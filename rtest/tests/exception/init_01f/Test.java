// Class instance initializers must be able to complete normally.
// .result=COMPILE_FAIL
public class Test {
  {
    throw null; // Error: always throws.
  }
}
