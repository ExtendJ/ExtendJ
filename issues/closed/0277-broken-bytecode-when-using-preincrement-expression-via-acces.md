# Broken bytecode when using preincrement expression via accessor

**Status:** resolved

*ExtendJ 8.1.0-27-gcd7effa Java SE 5*

The following test generates bad bytecode:

```java
public class Test {
  public static void main(String[] args) {
    Loop loop = new Test().new Loop();
    System.out.println(loop.run(100));
  }

  private int count = 1;

  class Loop {
    public int run(int num) {
      int sum = 0;
      while (count < num) {
        sum += count * count;
        ++count;
      }
      return sum;
    }
  }
}
```

Expected result: should print `328350`.

Actual result: fails to run due to a verification error:

```
    [junit] [FAIL] runTest[run/while_04](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Exception in thread "main" java.lang.VerifyError: (class: Test$Loop, method: run signature: (I)I) Inconsistent stack height 1 != 0
    [junit]     at Test.main(Test.java:3)]>
```

## Comments

### Jesper Öqvist - 2018-01-07

Fix missing pop after preincrement/predecrement

fixes #277 (bitbucket)


→ <<cset 99d73bf85658>>
