// .result=EXEC_PASS

/*
 * Index variables declared in enhanced for loops
 */
import java.util.*;
public class Test {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        iter(list);
    }

    public static <T extends CharSequence & Comparable<String>> void iter(ArrayList<T> list) {
        for(var s : list){
            System.out.println(s);
        }
    }
}
