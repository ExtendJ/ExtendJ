// .result=EXEC_PASS
/*
 * Allow the diamond with anonymous classes if the argument type of the inferred type is denotable.
 * In this test the inferred type is Integer
 */

 interface Interf<T> {
    default T identity(T x) {
        return x;
    }
}

abstract class Anon<T> implements Interf<T> {
    abstract T add(T num1, T num2);
}

public class Test {
    public static void main(String[] args) {
        Anon<Integer> obj = new Anon<>() {
            Integer add(Integer n1, Integer n2)
            {
                return (n1 + n2);
            }
        };
        Integer result = obj.add(10, 20);
    }
}
