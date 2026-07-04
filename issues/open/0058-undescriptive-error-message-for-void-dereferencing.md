# Undescriptive error message for void dereferencing

When dereferencing void JastAddJ should report a more descriptive error, similar to that of javac:

    class Test {
            void f(int i) {
            }
            void m() {
                    f(0x123).f(-11111);
            }
    }

With JastAddJ I get the following error:

    Test.java:5:
      Semantic Error: no method named f(int) in void matches.

Javac reports a more descriptive error:

    Test.java:5: error: void cannot be dereferenced
                    f(0x123).f(-11111);
                            ^
