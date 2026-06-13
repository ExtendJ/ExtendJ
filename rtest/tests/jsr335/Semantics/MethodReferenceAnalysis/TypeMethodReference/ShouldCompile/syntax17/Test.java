// .result=COMPILE_PASS
import java.io.IOException;
import java.util.*;

public class Test {
    public void method(Test t, int i) { }
    public void method(int i) { }

    interface A {
            void m(Test t, int i);
    }

    public static void main(String[] arg) {
            A a = Test::method;
    }
}