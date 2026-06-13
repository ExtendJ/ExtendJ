// Test that variable lookup can find a previous resource declaration inside
// the resource declaration list of try-with-resources statement.
// .result=COMPILE_PASS
public class Test {
  interface Resource extends AutoCloseable { }
  interface ResourceBuilder {
    Resource build();
    Resource build(Resource r);
  }
  void test(ResourceBuilder builder) {
    try (Resource r1 = builder.build(); Resource r2 = builder.build(r1)) {
    } catch (Exception e) {
    }
  }
}
