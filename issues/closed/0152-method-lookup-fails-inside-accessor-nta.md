# Method lookup fails inside accessor NTA

**Status:** resolved

**ExtendJ 8.0.1-89-g5b0de73**

There is a bug in code generation causing a NullPointerException to be thrown. The error is caused by method lookup failing inside method accessor NTAs. An example test case triggering the exception:

```java
// .result=COMPILE_PASS
public class Test {

  static class A {
    void f() {
      Object u = new Object();
      B.x(O.f(u));
    }
  }

  static class B {
    static private void x(Object u) {
    }
  }
}

class O {
  static Object f(Object u) {
    return u;
  }
}
```

In the above example, method lookup fails for `O.f(u)` when generating bytecode for the method access `B.x(O.f(u))`.

## Comments

### Jesper Öqvist - 2016-03-10

Fix type lookup error in transformed method access

fixes #152 (bitbucket)


→ <<cset 85f20a1713b1>>
