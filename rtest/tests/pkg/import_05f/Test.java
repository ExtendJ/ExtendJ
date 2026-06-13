// Test single static import conflicting with local top-level type
// .result=COMPILE_FAIL

import static pkg.Alfa.Beta;

public class Test {
	Beta beta = new Beta();
}

// conflicts with single-static import
class Beta { }
