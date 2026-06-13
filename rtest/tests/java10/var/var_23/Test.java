// .result=EXEC_PASS

/*

*/
import java.util.*;
public class Test {
    public static void main (String[] args) {
        MyClass myObject = new MyClass();

        ArrayList<String> myStringList = new ArrayList<>();
        myStringList.add("Hello");
        myStringList.add("World");
        var result = myObject.myMethod(myStringList);
        System.out.println(result.getClass());
        // assertTrue(result instanceof String);

        ArrayList<Object> myObjectList = new ArrayList<>();
        myObjectList.add("Hello");
        myObjectList.add(123);
        var result2 = myObject.myMethod(myObjectList);
        System.out.println(result2.get(0).getClass());
        // assertTrue(result instanceof Object);
    }
}