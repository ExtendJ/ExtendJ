// .result=EXEC_PASS

/*
 * Tests that primitive types are inferred correctly, then use them to infer wrappers
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

        var inferByte       = Byte.valueOf(inferPrimitiveByte);
        var inferCharacter  = Character.valueOf(inferChar);
        var inferShort      = Short.valueOf(inferPrimitiveShort);
        var inferInteger    = Integer.valueOf(inferInt);
        var inferLong       = Long.valueOf(inferPrimitiveLong);
        var inferFloat      = Float.valueOf(inferPrimitiveFloat);
        var inferDouble     = Double.valueOf(inferPrimitiveDouble);
        var inferBoolean    = Boolean.valueOf(inferPrimitiveBoolean);
    }
}
