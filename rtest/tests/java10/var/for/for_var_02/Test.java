// .result=EXEC_PASS

/*
 * Index variables declared in enhanced for loops
 */
import java.util.*;
public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(1);
        for(var i : list){
            System.out.println(i);
        }
    }
}
