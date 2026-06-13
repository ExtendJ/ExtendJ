// .result=EXEC_PASS

/*
 * Infer the wrapper trypes for the primitive types
 */
public class Test {
    public static void main (String[] args) {
        var inferByte       = Byte.valueOf((byte) 42);
        var inferCharacter  = Character.valueOf('c');
        var inferShort      = Short.valueOf((short) 42);
        var inferInteger    = Integer.valueOf(42);
        var inferLong       = Long.valueOf(42);
        var inferFloat      = Float.valueOf(42);
        var inferDouble     = Double.valueOf(42);
        var inferBoolean    = Boolean.valueOf(true);
        var inferString     = "Hello World";
    }
    
}
