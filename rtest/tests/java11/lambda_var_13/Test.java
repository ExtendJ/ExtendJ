// .result=COMPILE_FAIL

interface Add{
	int sum(int a, int b, int c);
}
public class Test {
    public static void main (String[] args) {
       Add add  = (Integer a, var b, Integer c)->a+b+c;
       int sum = add.sum(1, 2, 3);
    }
}


