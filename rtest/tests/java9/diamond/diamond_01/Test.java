// .result=EXEC_PASS
/*
 * Allow the diamond with anonymous classes if the argument type of the inferred type is denotable.
 * In this test the inferred type is Integer
 */

abstract class Anon<T, U> {
    abstract T add(T num1, U num2);
}

public class Test {
    public static void main(String[] args){
        Test test = new Test();
        test.testBasic();
    }
    public void testBasic(){
        Anon<Integer, String> obj = new Anon<>() {
            @Override
            Integer add(Integer n1, String n2)
            {
                return (n1);
            }
        };
        // Integer result = obj.add(10, 20);
        System.out.println("Diamond testBasic");
    }
}
