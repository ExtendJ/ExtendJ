// Test error when missing implementation of interface method
// .result=COMPILE_FAIL
interface I {
    int f();
}
class Test implements I {
}
