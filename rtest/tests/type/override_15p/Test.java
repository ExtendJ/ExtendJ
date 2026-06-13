// .result=COMPILE_PASS
interface I1 {
    Throwable f();
}

interface I2 {
    Exception f();
}

abstract class Test implements I1, I2 {
}
