// Can not create array with parameterized type elements.
// JLS SE7 $15.10: It is a compile-time error if the ClassOrInterfaceType does not denote a reifiable type ($4.7).
// https://bitbucket.org/jastadd/jastaddj/issue/108/creating-array-using-type-variable-as
// .result=COMPILE_FAIL
import java.util.*;
public class Test {
  LinkedList<Integer>[] array = new LinkedList<Integer>[10]; // Error: non reifiable element type.
}
