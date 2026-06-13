// Creating array with reifiable element type.
// https://bitbucket.org/jastadd/jastaddj/issue/108/creating-array-using-type-variable-as
// .result=COMPILE_PASS
public class Test<U, V> {
  Test<?, ?>[] array = new Test<?, ?>[10]; // Okay: the wildcard type is reifiable.
}
