// Okay not to implement interface methods in abstract class
// .result=COMPILE_PASS
interface I {
    int f();
}
abstract class Test implements I {
}
