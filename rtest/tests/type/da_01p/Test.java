// Test that a blank final instance variable can be declared after its initialization
// without causing definite assignment error.
// https://bitbucket.org/extendj/extendj/issues/128/definitive-assignment-of-a-final-field-in
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
