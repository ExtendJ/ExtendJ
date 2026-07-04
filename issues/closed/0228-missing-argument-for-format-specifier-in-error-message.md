# Missing argument for format specifier in error message

**Status:** resolved

*ExtendJ 8.0.1-228-gb4d4e4a Java SE 8*

In creating an error message for incompatible return types in inherited abstract methods, there is a missing argument to the errorf method, leading to exception when reporting an error.

Test case:

```java
// .result: COMPILE_FAIL
public abstract class Test extends Foo implements Bar {
  // Inherits two return-type incompatible methods _$().
}

interface Bar {
  int _$();
}

abstract class Foo {
  abstract public void _$();
}
```

Expected result: a nice error message about inheriting two incompatible method declarations. Something like this (javac):

```
tests/method/multidecl_01f/Test.java:2: error: types Bar and Foo are incompatible; both define _$(), but with unrelated return types
public abstract class Test extends Foo implements Bar {
                ^
1 error
```

Actual result: an exception stack trace:

```
Fatal exception:
java.util.MissingFormatArgumentException: Format specifier '%s'
        at java.util.Formatter.format(Formatter.java:2519)
        at java.util.Formatter.format(Formatter.java:2455)
        at java.lang.String.format(String.java:2940)
        at org.extendj.ast.ASTNode.errorf(ASTNode.java:268)
        at org.extendj.ast.TypeDecl.checkAbstractMethodDecls(TypeDecl.java:1268)
        at org.extendj.ast.ClassDecl.interfaceMethodCompatibleWithInherited(ClassDecl.java:108)
        at org.extendj.ast.ClassDecl.refined_TypeHierarchyCheck_ClassDecl_nameProblems(ClassDecl.java:971)
        at org.extendj.ast.ClassDecl.nameProblems(ClassDecl.java:2125)
```

## Comments

### Jesper Öqvist - 2017-12-12

Add missing argument to error message

Fixed a call to errorf - added missing argument to format specifier.

fixes #228 (bitbucket)


→ <<cset 3f4f98313bc6>>
