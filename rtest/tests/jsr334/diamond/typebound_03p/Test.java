// Using parameterized class type to infer the diamond type.
// https://bitbucket.org/extendj/extendj/issues/307/incorrect-warning-when-using-diamond
// .result: COMPILE_PASS
import java.util.EnumMap;
public class Test {
  enum Beer {
    OMNIPOLLO_NOA,
    WISBY_LAGER,
  }
  EnumMap<Beer, String> beerKind = new EnumMap<>(Beer.class);
}
