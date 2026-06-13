// .result=EXEC_PASS

/*
    Initializer cannot need its type inferred
*/
import java.util.*;
public class Test {
    public static void main (String[] args) {
		var l = new ArrayList<>();
    System.out.println(l);
    l.add("2");
    System.out.println(l.get(0));

    }

}