// .result=EXEC_PASS

/*
    Infer local variable type from a method call 
    Use another method call to ensure types are as expected
*/
import java.util.ArrayList;
public class Test {
    public static void main (String[] args) {
        Test test = new Test();
        var i = test.i();
        test.i2(i);
        var s = test.s();
        test.s2(s);
        var list = test.l();
        test.l2(list);
        var test2 = test.t();
        test.t2(test2);
    }

    int i(){
        return 3;
    }
    void i2(int i){
    }

    String s(){
        return "str";
    }
    void s2(String s){
    }

    ArrayList<Integer> l(){
        return new ArrayList<Integer>();
    }
    void l2(ArrayList<Integer> l){
    }

    Test2 t(){
        return new Test2();
    }
    void t2(Test2 t){
    }

    class Test2{

    }

}