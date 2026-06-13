// .result=COMPILE_FAIL

/*
    Dot access can contain an Access with same name being declared,
    but array access expr must not
*/

public class Test {
    public void f(){
        C[] a = {new C(), new C(), new C()};
        var x = a[(x=2)].x.a;
    }

    public class C{
        D x = new D();
    }
    public class D{
        int a;
    }


}