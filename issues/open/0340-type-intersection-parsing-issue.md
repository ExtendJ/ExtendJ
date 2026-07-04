# Type intersection parsing issue

‌

Type intersections are get unexpected parse and name errors.

‌

```java
public class Test {
    public static void main (String[] args) {
        System.out.println((Comparable<String> & CharSequence) "x"); // Ex 1
        //  System.out.println((java.lang.CharSequence & Comparable<String> ) "x"); // Ex 2
    }
}
```

Expected result: No compilation errors

Actual result Ex 1:

Test.java:8,50: error: no field named CharSequence is accessible

Actual result Ex 2:

Test.java:9,73: error: unexpected token "\)"
Test.java:9,79: error: unexpected token ";"

‌

This is potentially caused by this if statement from the Java 8 JavaScanner.java

```java
    if(firstLookahead.getId() == Terminals.IDENTIFIER &&
        secondLookahead.getId() == Terminals.AND) {
      return true;
    }
    else {
      return false;
    }
```

‌
