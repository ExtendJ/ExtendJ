// Creating array with non-reifiable element type.
// https://bitbucket.org/jastadd/jastaddj/issue/108/creating-array-using-type-variable-as
// .result=COMPILE_FAIL
public class Test<U, V> {
  Test<?, ? extends Integer>[] array = new Test<?, ? extends Integer>[10];
}
