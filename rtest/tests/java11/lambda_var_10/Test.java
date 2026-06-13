// .result=COMPILE_FAIL

interface Add{
	int sum(int a, int b);
}
public class Test {
    public static void main (String[] args) {
       Add add  = (Integer a, var b)->a+b;
       int sum = add.sum(1, 2);
    }
}


