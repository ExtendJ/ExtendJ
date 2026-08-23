// Test lub type creation and subtyping relation.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <U> U join(U a, U b);

  Base    s = join(new Left(), new Right());
  With<?> c = join(new Left(), new Right());
}

class Left  implements With<Left>, Base { }
class Right implements With<Right>, Base { }
interface Base { }
interface With<T> { }
