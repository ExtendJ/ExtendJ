// Test duplicate field declaration error message.
// .result=COMPILE_ERR_OUTPUT
class Test {
  int lgr;
  float lgr;

  int lgr() {
    return lgr;
  }
}
