// It is an error if a resource declaration initializer attempts to use a
// resource declared after the initializer's resource.
// .result=COMPILE_FAIL
public class Test {
  interface Resource extends AutoCloseable { }
  interface ResourceBuilder {
    Resource build();
    Resource build(Resource r);
  }
  void test(ResourceBuilder builder) {
    try (Resource r1 = builder.build(r2); Resource r2 = builder.build()) {
    } catch (Exception e) {
    }
  }
}
