// Test a NullPointerException bug in ExtendJ caused by an error in method type inference.
// See issue 172.
// .result=COMPILE_PASS
public class Test {
    int i = 0;

    public <U extends Test> U a() {
        return b();
    }

    <T extends Test> T b() {
        return null;
    }
}
