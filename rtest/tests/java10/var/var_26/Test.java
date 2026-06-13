// .result=COMPILE_PASS

/*
    Initializer may be an array
*/
public class Test {
    public static void main (String[] args) {
        int[] arr = {1, 2, 3};
        var x = arr;
    }

}