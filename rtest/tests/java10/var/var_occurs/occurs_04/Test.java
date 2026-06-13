// Variable being declared as a var cannot be referenced in the initializer
// .result=COMPILE_FAIL
public class Test {
    public void f(){
        int[] a = {1, 2, 3};
        var x = a[(x = 2)];
    }
}
