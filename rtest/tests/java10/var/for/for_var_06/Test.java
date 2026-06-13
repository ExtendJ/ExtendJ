// .result=EXEC_PASS

/*
 * Index variables declared in enhanced for loops
 */
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        int[] intArray = IntStream.range(1, 3).toArray();
        for (var i : intArray) {
            System.out.println("i = " + i);
        }
    }
}
