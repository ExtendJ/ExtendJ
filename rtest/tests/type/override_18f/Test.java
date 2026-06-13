// https://bitbucket.org/jastadd/jastaddj/issue/91/inheriting-package-private-implementation
// .result=COMPILE_FAIL
import pkg1.A;
import pkg1.I;
class Test extends A implements I {
	// Error - method m() from A is package private, but also matches I.m().
  // TODO: this test may be giving a compile error for the wrong reason right now.
}
