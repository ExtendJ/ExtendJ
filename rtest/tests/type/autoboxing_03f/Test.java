// .result=COMPILE_FAIL
class Test {
  boolean b = true;
  Test t = (Test) b; // Error: bad cast. Not an autoboxing conversion.
}
