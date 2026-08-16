// Test argument constraint with capture conversion.
// .result: COMPILE_PASS
public abstract class Test {
  abstract Khasekhemwy<?> sahure();
  abstract <Tv> void nyuserre(Khasekhemwy<Tv> l);

  {
    // The wildcard in the argument type Khasekhemwy<?> is capture-converted before
    // the argument is constrained against the parameter type Khasekhemwy<Tv>.
    nyuserre(sahure());
  }
}

interface Khasekhemwy<Uv> { }
