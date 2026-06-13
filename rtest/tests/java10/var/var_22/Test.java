// .result=COMPILE_FAIL
// TODO

/*
    Capture types, from javac "incompatible types: Object cannot be converted to CAP#1"
*/
import java.util.*;
public class Test {
    public static void main (String[] args) {
        List<String> l1 = new ArrayList<>();
        l1.add("Hello");
        List<?> l2 = l1;

        var x = l2.get(0);
        System.out.println(x.getClass());
        l2.add(x); // error
    }

}