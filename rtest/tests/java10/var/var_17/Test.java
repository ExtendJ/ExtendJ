// Initializer can be a non denotable type, here a type intersection
// Error in type intersection unrelated to type inference with var.
// https://bitbucket.org/extendj/extendj/issues/319/type-cast-to-intersection-type-has-no
// .result=COMPILE_PASS
import java.util.*;
public class Test {
  public static void main (String[] args) {
    var l = (AbstractCollection<Integer> & List<Integer> & Queue<Integer>) new ArrayList<Integer>();
  }
}
