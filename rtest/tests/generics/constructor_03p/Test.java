// Tests JastAddJ bug discovered by Erik Hogeman.
// Test case provided by Erik.
// See bitbucket issue #74
// .result=COMPILE_PASS
public class Test {
        public class B {
                public B(int i) { }
        }

        public <T extends Integer> void testMethod(T t) {
                /* javac does not generate an error here, neither
                   version 7 nor 8. JastAddJ do on the other hand.
                   If the "int" in B:s constructor is changed to Integer
                   there is no error though. */
                B b = new B(t);
        }
}
