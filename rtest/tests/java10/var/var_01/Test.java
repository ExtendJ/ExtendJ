// .result=EXEC_PASS

/*
 * Tests that primitive types are inferred correctly
 */
public class Test {
    public static void main(String[] args) {
        var inferPrimitiveByte      = (byte) 42;
        var inferChar               = 'c';
        var inferPrimitiveShort     = (short) 42;
        var inferInt                = 42;
        var inferPrimitiveLong      = 42l;
        var inferPrimitiveFloat     = 42.0f;
        var inferPrimitiveDouble    = 42.0;
        var inferPrimitiveBoolean   = true;
    }
}
