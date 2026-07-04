# Type inference regression

**Status:** resolved

**ExtendJ 8.0.1-202-g732653d Java SE 8**

The following test passed before commit 3ce541ad8f081e4e40f6eecf2c54b2809f3251c5:

```java
// .result: COMPILE_PASS
public abstract class Test {
        <T extends Note> T get(Class<T> note) {
                Note a = get(note);
                return (T) a;
        }
}

interface Note { }
```

In this test case, ExtendJ infers the parameterization `[T=Note]` for the `get(type)` call. However, the type of `note` inside the method declaration is `Class<T>`. In method call applicability testing, the type `Class<T>` is determined to not be a subtype of `Class<Note>`.

Expected result: no errors

Actual result:

```
    [junit] tests/generics/method_23p/Test.java:4: error: can not assign variable a of type Note a value of type Unknown
    [junit] tests/generics/method_23p/Test.java:4: error: no method named get(java.lang.Class<T>) in Test matches. However, there is a method get(java.lang.Class<T>)
```

## Comments

### Jesper Öqvist - 2017-11-21

The initial constraints for type inference in the method call `get(note)` are:

* `T <: Note`
* `T == T`

In the equality constraint, `T` refers to both the type variable for the method call, and the type variable in the context of the method call. These should probably be considered as two separate things but in the current implementation they point to the exact same object.

In the commit that made the test break, the equality constraint was removed because it was assumed to be redundant. However, this breaks the type inference because it causes the type of `T` to be inferred to `Note` using the remaining subtype constraint `T <: Note`.

### Jesper Öqvist - 2017-11-21

Revert "Skip adding redundant equality constraints"

This reverts commit 3ce541ad8f081e4e40f6eecf2c54b2809f3251c5.

In type inference it is possible that a constraint contains the same type
variable on the left and right hand side of the constraint.
For example, T = T.  This can happen when the generic method call is
recursive, calling the same method. In this case, one type variable
belongs to the generic method call, and the other represents a type
from the context of the method call. Even though they are the exact same
type variable, they represent slightly different things and a constraint like
T = T can not be removed because it removes information from the
type inference.

fixes #214


→ <<cset c24f7ceccba6>>
