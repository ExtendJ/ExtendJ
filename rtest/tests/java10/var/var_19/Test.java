// .result=EXEC_PASS

/*
    Initializer cannot need its type inferred
*/
import java.util.*;
public class Test {
    public static void main(String[] args) {
		var x = "x".getClass();
    System.out.println(x);
    
    }

}