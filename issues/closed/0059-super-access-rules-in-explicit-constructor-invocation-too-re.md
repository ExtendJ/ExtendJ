# Super access rules in explicit constructor invocation too restrictive

**Status:** resolved

[JLSv7 $8.8.7.1](http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.8.7.1) states:

**An explicit constructor invocation statement in a constructor body may not refer to any instance variables or instance methods or inner classes declared in this class or any superclass, or use this or super in any expression; otherwise, a compile-time error occurs.**

The use of `this` and `super` in the above specification should be interpreted as meaning any expression evaluating to `this` and `super` of the enclosing class of the constructor, not just any occurrence of `this` or `super`. For example, the class `CustomRunnerTest` from JUnit 4.0 has the following constructor:

        public static class CustomRunner extends TestClassRunner {
                public CustomRunner(Class<?> klass) throws InitializationError {
                        super(klass, new TestClassMethodsRunner(klass) {
                                @Override
                                protected TestMethodRunner createMethodRunner(Object test, Method method, RunNotifier notifier) {
                                        return new TestMethodRunner(test, method, notifier,
                                                        methodDescription(method)) {
                                                @Override
                                                protected void executeMethodBody()
                                                                throws IllegalAccessException,
                                                                InvocationTargetException {
                                                        super.executeMethodBody();
                                                        assertGlobalStateIsValid();
                                                }
                                        };
                                }
                        });
                }
        }

There is a `super` access in the above constructor, nested inside an explicit constructor invocation, however the `super` access does not refer to the same class as the one containing the constructor in which the explicit constructor invocation in question is used.

JastAddJ reports the following error:

    JUnit4.0/tests/org/junit/tests/CustomRunnerTest.java:31,8:
      Semantic Error: super may not be accessed in an explicit constructor invocation

The error is of course correct using a naive interpretation of the Java specification, however the `super` access in this case should really not be an error. Further evidence to this claim is that javac 1.7.0_51 does not report an error for the same code.

## Comments

### Jesper Öqvist - 2014-02-26

Fixed typos.

### Jesper Öqvist - 2014-02-26

Test case:

    class SS {
            public SS(Object o) {
            }
    }
    class Test extends SS {
            public Test() {
                    super(new Runnable() {
                            @Override
                            public void run() {
                                    // legal super access -- not accessing super of Test:
                                    super.hashCode();
                            }
                    });
            }
    }

### Jesper Öqvist - 2014-02-26

Fixed problem in super/this access checking

fixes issue #59 (bitbucket)


→ <<cset 1290dcfac192>>
