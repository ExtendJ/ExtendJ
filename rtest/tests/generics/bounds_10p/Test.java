// Test compatible wildcard intersections and dependent bounds.
// .result=COMPILE_PASS
import java.io.Serializable;

public class Test {
  INTer<? extends Ring<?>> intersection; // OK because the capture of ? extends Ring<?> is glb(Ring<?>, Ring<?> & Port)
  rRight<?, ?> unbounded;
  eLeft<?, ?> dependent;
  eLeft<? extends Object, ?> objectSibling;
  bounD<? extends Integer> narrower;
  bounD<? extends Object> wider;
  bounD<? super Integer> lower;
  rRight<Number, ? extends Integer> concreteNarrower;
  rRight<Integer, ? extends Number> concreteWider;
  B<? extends Ring<?>> issue312; // Issue 312 regression test.
}

interface Ring<E> { }
interface Port { }
class INTer<E extends Ring<?> & Port> { }
class rRight<E, F extends E> { }
class eLeft<E extends F, F> { }
class bounD<E extends Number> { }
class B<R extends Ring<R>> { }
