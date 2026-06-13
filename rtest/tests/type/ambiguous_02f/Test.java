// .result=COMPILE_FAIL
import pkg.A;

class Test {
	A a;
}

// ambiguous type - conflicts with import
class A {
}
