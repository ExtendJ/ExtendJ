// Method invocation type inference.
// This test uses the Java 8 standard library and exposes a bug in ExtendJ type inference.
// The bug is essentially that ExtendJ does not infer the correct target type.
// Simplified versions of this test are in the tests method_02p, method_03p, and method_04p.
// https://bitbucket.org/extendj/extendj/issues/182/wrong-target-type-for-inferred-method
// .result=COMPILE_PASS
// .classpath: @RUNTIME_CLASSES@
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    List<Integer> list0 = new ArrayList<>();
    list0.add(11);
    list0.add(121);
    List<Integer> list1 = toList(list0.stream());

    testNotSame(list0, list1);
    testEquals(11, list1.get(0));
    testEquals(121, list1.get(1));
  }

  static List<Integer> toList(Stream<Integer> stream) {
    // ExtendJ computed the wrong type for 'Collectors.toList()',
    // leading to no matching method being found.
    //
    // Collectors.toList():
    //     <T> Collector<T, ?, List<T>> toList()
    //
    // Stream.collect():
    //     <R, A> R collect(Collector<? super T, A, R>)
    return stream.collect(Collectors.toList());
  }
}
