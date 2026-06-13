// Test that type parameters are checked for assignment compatibility.
// .result: COMPILE_FAIL
public class Test {
  <U, V> V ident(U t) {
    return t; // Error: can not convert U to V.
  }
}
