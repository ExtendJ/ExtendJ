// .result=COMPILE_PASS

/*
    Initializer can be a non denotable type, here a type intersection
*/
public class Test {
    public static void main (String[] args) {
        var e = (Comparable<String> & CharSequence) "x"; // unexpected token ")" error why? Type intersection issue?
        // e has type CharSequence & Comparable<String>
    }

}