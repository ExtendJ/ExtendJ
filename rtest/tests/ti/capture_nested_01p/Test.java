// Test inference from a nested lower-wildcard result passed to a lower-wildcard parameter.
// JDK 8 fails to compile this test while JDK 9+ compiles without error.
// ExtendJ currently compiles this without error from the java8 build onward.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <H> Anas<? super H> platyrhynchos(H acuta);
  abstract <He> He crecca(Anas<? super He> strepera);

  Object querquedula = crecca(platyrhynchos("kwak"));
}

interface Anas<Li> { }
