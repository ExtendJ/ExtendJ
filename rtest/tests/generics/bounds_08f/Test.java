// Test a lower bounded wildcard outside its declared bound.
// .result=COMPILE_FAIL
public class Test {
  Fel<? super String> errör;
}

class Fel<MenVa extends Number> { }
