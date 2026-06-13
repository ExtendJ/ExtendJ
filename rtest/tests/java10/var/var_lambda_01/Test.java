// .result=COMPILE_FAIL

/*
    Var can't be used in lambdas for java 10
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