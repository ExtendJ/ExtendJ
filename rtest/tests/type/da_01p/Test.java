// Test that a blank final instance variable can be declared after its initialization
// without causing definite assignment error.
// See issue 128.
// .result=COMPILE_PASS
class Test {
    {
        b = 0;
    }

    Test() {
        int x = b;
    }

    final int b;
}
