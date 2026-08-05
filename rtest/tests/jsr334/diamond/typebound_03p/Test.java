// Using parameterized class type to infer the diamond type.
// See issue 307.
// .result: COMPILE_PASS
import java.util.EnumMap;
public class Test {
  enum Beer {
    OMNIPOLLO_NOA,
    WISBY_LAGER,
  }
  EnumMap<Beer, String> beerKind = new EnumMap<>(Beer.class);
}
