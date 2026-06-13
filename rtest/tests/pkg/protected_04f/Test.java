// Test using protected inner class outside this package.
// It works if we extend the outer class, but we can not access
// the constructor.
// https://bitbucket.org/jastadd/jastaddj/issue/94/should-not-be-able-to-use-default
// .result=COMPILE_FAIL
class Test extends p1.A {
	p1.A.X x = new p1.A.X();
}
