# Method with a raw argument is not applicable by unchecked conversion

**Status:** resolved

*ExtendJ 11.0.0-g25eca834 Java SE 8*

A raw argument makes unchecked conversion necessary for a generic method to be
applicable. ExtendJ reports the method as inapplicable when
another argument constrains the same inference variable.

```java
import java.util.List;

public class Test {
  void test(List raw, List<String> typed) {
    both(raw, typed);
  }

  static <X> void both(List<X> a, List<X> b) {
  }
}
```

Expected result: should compile with an unchecked warning.

Actual result:

```
error: no method named both(java.util.List, java.util.List<java.lang.String>) in Test matches.
  However, there is a method both(java.util.List<X>, java.util.List<X>)
```

`both(raw, raw)` and `single(raw)` for `static <X> void single(List<X> a)` are
accepted, so a raw argument on its own works. It is effectively the second argument
which also constrains `X` that makes the invocation inapplicable.

Issue 345 fixed the result type of an invocation that needs unchecked
conversion. This is the applicability of such an invocation, which is decided
before the result type is computed.

See regression test `rtest/tests/ti/raw_unchecked_06p`.

## Where it goes wrong

JLS SE8 §18.5.2 only erases the result type and the thrown types
when an unchecked conversion was necessary to make a generic method
applicable.

The fix for issue 345 made `MethodAccess.parameterizedDecl` fall back to
`rawMethodDecl()` when unchecked conversion was necessary. That erases the
result type as intended, but it also replaces each type parameter in the
*parameter* types by its erasure. In the above example, the formal parameters become
`List<Object>`. The applicability check then rejects the `List<String>`
argument because it is not a subtype of `List<Object>`.

## Resolution

`GenericMethodDecl.lookupUncheckedMethodDecl` builds the invocation type for
this case, constructing an `UncheckedMethodDecl` parameterized by the inferred type
arguments and whose `type()` is the erasure of the result type.

## Erasing the substituted type

§18.5.2 says the result type and the thrown types are the erasures of the ones
declared by the method, but that reading makes a declared type variable erase
to its bound and lose the inferred instantiation. Javac instead substitutes θ'
first and erases the result, which accepts strictly more programs. Eclipse JDT
made the same choice in [PR #477](https://github.com/eclipse-jdt/eclipse.jdt.core/pull/477)
("GH472: substitute and then erasure if unchecked conversion was necessary"),
where Stephan Herrmann notes that "erasing a type variable `T` provides
nothing", and deliberately followed javac over the specification text for
toolchain compatibility. The clause itself is unchanged in JLS SE9 and SE21, so
this is a compiler convention rather than a specification version difference.

Similarly, ExtendJ now follows javac and erases the substituted types. The difference is only
observable when the inferred type argument is a proper subtype of the declared
bound, which is what these regression tests cover:

- `rtest/tests/ti/raw_unchecked_09p` for the result type: `<X extends ZhtBaseA>
  X pick(..)` invoked with an argument that instantiates `X` as `ZhtSubB` has
  result type `ZhtSubB`, not `ZhtBaseA`.
- `rtest/tests/ti/raw_unchecked_08p` for the thrown types: `<X extends Ex> void
  thrower(..) throws X` invoked with an argument that instantiates `X` as
  `SubEx` throws `SubEx`, not `Ex`.

The thrown types need no separate handling. A thrown type is never a
parameterized type, since a generic class may not extend `Throwable`
(JLS SE8 §8.1.2), so erasing the substituted thrown types leaves them as the
substitution produced them.
