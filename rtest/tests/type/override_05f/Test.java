// .result=COMPILE_FAIL
abstract class A {
	abstract void m(int i);
}

interface I {
	int m(int i);
}

abstract class Test extends A implements I {
}
