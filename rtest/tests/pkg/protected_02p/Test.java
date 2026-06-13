// Test using protected inner class outside this package.
// It works if we extend the outer class.
// .result=COMPILE_PASS
class Test extends p1.A {
	p1.A.X x;
}
