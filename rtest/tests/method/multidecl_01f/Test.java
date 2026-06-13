// .result: COMPILE_FAIL
public abstract class Test extends Foo implements Bar {
  // Inherits two return-type incompatible methods _$().
}

interface Bar {
  int _$();
}

abstract class Foo {
  abstract public void _$();
}
