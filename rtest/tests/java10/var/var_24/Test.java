// .result=COMPILE_PASS

import java.util.ArrayList;
import java.util.List;

/**
 * The key thing we're testing here is that the compiler is able to correctly infer the type of 
 * combinedList based on the types of its elements, even though we didn't explicitly specify the 
 * type. If the compiler is performing type projections correctly, then this test case should pass. 
 */

public class Test {

    public void Test() {
        // create a list of integers
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        // create a list of objects
        List<Object> objectList = new ArrayList<>();
        objectList.add("four");
        objectList.add(true);
        objectList.add(5);

        // use var to declare a list of elements from both lists
        var combinedList = new ArrayList<>();
        combinedList.addAll(integerList);
        combinedList.addAll(objectList);
    }
}
