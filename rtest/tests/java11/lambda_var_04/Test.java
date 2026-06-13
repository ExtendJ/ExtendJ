// .result=COMPILE_FAIL

/*
    Var can be used in lambdas in java 11
*/
interface Add{
	int sum(int a);
}
public class Test {
    public static void main (String[] args) {
       Add add  = (var a, b)->a;
       int sum = add.sum(1);
    }
}