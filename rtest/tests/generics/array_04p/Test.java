// Creating array with reifiable element type.
// https://bitbucket.org/jastadd/jastaddj/issue/108/creating-array-using-type-variable-as
// .result=COMPILE_PASS
public class Test<T extends Integer> {
  Test[] array = new Test[10]; // Okay: raw parameterization.
}
