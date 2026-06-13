// .result=EXEC_PASS
/*
    Infer local variable type from a method call 
*/
import java.util.ArrayList;

public class Test {
    public static void main (String[] args) {
        Test test = new Test();
        var i = test.i();
        var s = test.s();
        var list = test.l();
        var test2 = test.t();
    }

    int i(){
        return 3;
    }

    String s(){
        return "str";
    }

    ArrayList<Integer> l(){
        return new ArrayList<Integer>();
    }

    Test2 t(){
        return new Test2();
    }

    class Test2{
    }

}