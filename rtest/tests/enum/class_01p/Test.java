// Test the type of the class literal for an enum type.
// .result=COMPILE_PASS
public class Test {
	enum Beer {
    OMNIPOLLO,
    WISBY
  }

	Class<Beer> c = Beer.class;
}
