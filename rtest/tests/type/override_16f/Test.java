// Incompatible interface methods
// .result=COMPILE_FAIL
interface I1 {
    long f(int a);
}

interface I2 {
    void f(int a);
}

abstract class Test implements I1, I2 {
}
