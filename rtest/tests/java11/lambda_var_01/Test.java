// .result=EXEC_PASS

/*
    Var can be used in lambdas in java 11
*/
interface Add{
	int sum(int a, int b);
}
public class Test {
    public static void main (String[] args) {
       Add add  = (var a, var b)->a+b;
       int sum = add.sum(1, 2);
    }
}