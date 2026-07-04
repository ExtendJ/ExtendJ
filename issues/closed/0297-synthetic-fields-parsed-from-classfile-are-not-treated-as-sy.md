# Synthetic fields parsed from classfile are not treated as synthetic

**Status:** resolved

*ExtendJ 8.1.1 Java SE 6*

ExtendJ allows synthetic fields like `$assertionsDisabled` to be accessed in source code. For example:

`Bar.java`:
```java
public class Bar {
  public static void main(String[] a) {
    assert args.length > 0;
  }
}
```

`Test.java`:
```java
public class Test {
  public static void main(String[] a) {
    System.out.println(Bar.$assertionsDisabled);
  }
}
```

The interesting case happens if `Bar.java` is compiled first with JavaC (or ExtendJ), then `Test.java` is compiled with ExtendJ:

```text
Σ 13:59:25 ~/git/regression-tests $ javac -version
javac 1.6.0_41
Σ 13:59:31 ~/git/regression-tests $ javac Bar.java
Σ 13:59:33 ~/git/regression-tests $ java -jar extendj.jar Test.java
Σ 13:59:37 ~/git/regression-tests $ java Test
true
```

Expected: ExtendJ should fail to compile `Test.java`.

Actual: ExtendJ does not complain about `Test.java` if `Bar.java` is compiled separately before compiling `Test.java` with ExtendJ.

## Comments

### Jesper Öqvist - 2018-01-29

Recognize synthetic fields marked ACC_SYNTHETIC

fixes #297 (bitbucket)


→ <<cset b3bb5e2e9783>>
