// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {

    public interface A<T> {
        T m();
    }

    public static class HelperClass<T> {
        T var;
        HelperClass(T v) { 
        	this.var = v; 
        }
    }

    public static void main(String[] args) {
        A<Integer> a = () -> new HelperClass<Integer>(44).var;
        testTrue("Lambda", a.m() instanceof Integer);
        testTrue("Lambda", a.m() == 44);
    }
}
