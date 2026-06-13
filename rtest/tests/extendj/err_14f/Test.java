// Test that ExtendJ types aren't accessible when bootclasspath isn't set.
// Fork is necessary, otherwise 'sun.boot.class.path' is set and used as a default bootclasspath.
// .result: COMPILE_FAIL
// .fork=true
public class Test {
  org.extendj.ast.Program p;
}
