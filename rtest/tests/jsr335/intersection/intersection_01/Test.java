// .result=COMPILE_FAIL

public class Test {
    public static void main (String[] args) {
        String e = (Comparable<String> & CharSequence) "x"; // unexpected token ")" error why? Type intersection issue?
        // e has type CharSequence & Comparable<String>
    }

}