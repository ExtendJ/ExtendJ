// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;
import java.io.Serializable;

public class Test {

    public interface A extends Serializable {
        void m();
    }

    public static void main(String[] args) {
        A a = () -> { };
        boolean wasInstance = false;
        if(a instanceof Serializable) {
        	wasInstance = true;
        }
        testTrue("Lambda", wasInstance);
    }
}
