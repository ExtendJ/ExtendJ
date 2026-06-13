// Can not create array with type variable as element type.
// JLS SE7 $15.10: It is a compile-time error if the ClassOrInterfaceType does not denote a reifiable type ($4.7).
// .result=COMPILE_FAIL
public class Test<T extends Integer> {
  T[] array = new T[10]; // Error: non reifiable array element type.
}
