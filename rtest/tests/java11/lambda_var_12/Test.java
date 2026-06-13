// .result=COMPILE_FAIL

interface UpperCase{
	String upperCase(String s);
}
public class Test {
    public static void main (String[] args) {
       UpperCase upper = var s -> s.toUpperCase();
       String str = upper.upperCase("hello");
    }
}


