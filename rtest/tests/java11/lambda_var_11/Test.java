// .result=EXEC_PASS

interface UpperCase{
	String upperCase(String s);
}
public class Test {
    public static void main (String[] args) {
       UpperCase upper = s -> s.toUpperCase();
       String str = upper.upperCase("hello");
    }
}


