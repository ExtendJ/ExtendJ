// Trying to assign an incompatible result type from raw generic method
// Test case provided by Erik Hogeman
// See https://bitbucket.org/jastadd/jastaddj/issue/73/incompatible-bounds-allowed-in-assignment
// .result=COMPILE_FAIL
public class Test {
        public <T extends Number> T method() {
                return null;
        }

        public void testMethod() {
                /* Should generate error, the bound on T
                   makes String an invalid type to assign to */
                String s = method();
        }
}

