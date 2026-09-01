// Test inference of nested upper bounded wildcard inside qualified expression.
// .result: COMPILE_PASS
public abstract class Test {
  abstract Class<? extends Test> cls();
  abstract <U> U unc(Class<U> c);

  {
    unc(cls());
    Class<? extends Test> c = cls();
    unc(c);
    unc((Class<? extends Test>) cls());
    unc(this.cls());
    Test o = this;
    unc(o.getClass());
    unc(this.getClass());
  }

  void case2(Klass<? extends Test> k) {
    unc(k.klass);
  }
}

class Klass<T> {
  Class<T> klass;
}
