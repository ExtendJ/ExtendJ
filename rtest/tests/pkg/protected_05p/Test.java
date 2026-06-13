// Test using protected inner class outside this package.
// .result=COMPILE_PASS
class Test extends p1.A {
	p1.A.X x = new p1.A.X();
}
