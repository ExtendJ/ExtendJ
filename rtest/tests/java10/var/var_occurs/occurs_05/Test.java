// .result=COMPILE_PASS

/*
    Dot access can contain an Access with same name being declared
*/

public class Test {
    public void f(){
        C[] a = {new C(), new C(), new C()};
        var x = a[2].x.a;
    }

    public class C{
        D x = new D();
    }
    public class D{
        int a;
    }


}