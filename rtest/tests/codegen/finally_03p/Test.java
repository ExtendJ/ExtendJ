// Tests code generation bug in JastAddJ for finally clause.
//
// The subroutine generated for the finally clause clobbers local variables.
// In this case the subroutine will clobber local variable 4 which is used
// after the subroutine returns. This bug should be fixed by simply not generating
// JSR/RET.
//
// https://bitbucket.org/jastadd/jastaddj/issue/14/finally-subroutine-clobbers-local
// .result=EXEC_PASS
public class Test {
	public static void main(String[] args) throws Throwable {
		new Test().runBare();
	}

    public void runBare() throws Throwable {
        Throwable exception = null;
        setUp();
        try {
            runTest();
        } catch (Throwable running) {
            exception = running;
        } finally {
            try {
                tearDown();
            } catch (Throwable tearingDown) {
                if (exception == null) exception = tearingDown;
            }
        }
        if (exception != null) throw exception;
    }

	public void setUp() {
	}

	public void tearDown() {
	}

	public void runTest() {
	}
}
