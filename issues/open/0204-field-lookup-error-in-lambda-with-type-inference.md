# Field lookup error in lambda with type inference

*ExtendJ 8.0.1-174-g53debda Java SE 8*

ExtendJ fails to find the `string` field in the following test case:

```java
import java.util.function.Function;

class Token {
  public final String string;
  public Token(String token) {
    this.string = token;
  }
}

interface Lambda4 {
  <T> String transform(T in, Function<T, String> fun);

  String replaceWords(String str, Function<String, String> fun);

  default void m(String string, String prefix) {
    transform(new Token(string), token -> prefix + token.string);
  }
}
```

The test case generates the following error:

```
java -jar extendj.jar scratch/Lambda4.java
scratch/Lambda4.java:16,58: error: no field named string is accessible
```

No error is reported by javac.
