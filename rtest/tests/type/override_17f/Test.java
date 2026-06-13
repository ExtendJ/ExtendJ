// Non-abstract class must implement interface method, even through deep inheritance
// .result=COMPILE_FAIL
interface I1 {
    long f(int a, float b);
}

interface I2 extends I1 {
}

interface I3 extends I2 {
}

class Test implements I3 {
}
