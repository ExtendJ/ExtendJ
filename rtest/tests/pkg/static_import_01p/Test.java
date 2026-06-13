// .result=COMPILE_PASS
package pkg;

import pkg.subpkg.A;
import static pkg.subpkg.A.S;
import static pkg.B.S;

public class Test {
}

class B extends A {
}


