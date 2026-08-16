// Test that an array type is not assignable to a type variable.
// .result=COMPILE_FAIL
public class Test {
  <Tv extends Object> void test() {
    Tv t = new C[0]; // Error: C[] is not a subtype of Tv.
  }
}

class C { }
