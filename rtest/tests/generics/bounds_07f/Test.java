// Test conflicting bounds on upper bounded wildcards.
// .result=COMPILE_FAIL
public class Test {
  Big<? extends Gnu> text;
  Big<? extends Gnu[]> array;
  Jupiter<Number, ? extends Gnu> sibling;
}

class Gnu { }
class Big<E extends Number> { }
class Jupiter<E, F extends E> { }
