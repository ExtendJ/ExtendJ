// .result=COMPILE_FAIL
public class Test {
    private class _ {
        _(int i) {
        }
        _(int i) { // duplicate constructor name and type
        }
    }
}
