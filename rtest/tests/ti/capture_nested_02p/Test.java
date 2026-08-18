// Test inference of a String result from a nested lower-wildcard argument.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <Be> Aythya<? super Be> ferina(Be marila);
  abstract <B> B fuligula(Aythya<? super B> nyroca);

  // The intersection instantiation of B becomes a lower bound in the lub for Be.
  String baeri = fuligula(ferina("kwek"));
}

interface Aythya<C> { }
