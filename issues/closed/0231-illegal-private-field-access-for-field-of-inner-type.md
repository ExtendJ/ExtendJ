# Illegal private field access for field of inner type

**Status:** resolved

*ExtendJ 8.0.1-232-g3a88c80 Java SE 8*

A test case using a builder pattern:

```java
public class Ju1 {
  public static final Ju1 DEFAULT_ACTION =
      builder()
      .withAction(new Action() {
        @Override public void doThing() {
          System.out.println("thing done");
        }
      })
    .build();

  private final Action action;

  Ju1(Builder builder) {
    this.action = builder.action;
  }

  private static Builder builder() {
    return new Builder();
  }

  private static class Builder {
    private Action action;

    private Builder withAction(Action action) {
      this.action = action;
      return this;
    }

    Ju1 build() {
      return new Ju1(this);
    }
  }

  void run() {
    action.doThing();
  }

  public static void main(String[] args) {
    DEFAULT_ACTION.run();
  }
}

interface Action {
  void doThing();
}
```

Expected result: no runtime error.

Actual result:

```
Exception in thread "main" java.lang.IllegalAccessError: tried to access field Ju1$Builder.action from class Ju1
        at Ju1.<init>(Ju1.java:14)
        at Ju1$Builder.build(Ju1.java:30)
        at Ju1.<clinit>(Ju1.java)
```

## Comments

### Jesper Öqvist - 2017-12-13

This issue is caused by an error in the `VarAccess.requiresAccessor()` attribute. The attribute uses the field name to determine if an accessor is required, but it should look at where the accessed field is declared instead:

```java
  syn boolean VarAccess.requiresAccessor() {
    Variable v = decl();
    if (!(v instanceof FieldDeclarator)) {
      return false;
    }
    FieldDeclarator f = (FieldDeclarator) v;
    if (f.isPrivate() && !hostType().hasField(v.name())) {
      return true;
    }
    if (f.isProtected() && !f.hostPackage().equals(hostPackage())
        && !hostType().hasField(v.name())) {
      return true;
    }
    return false;
  }
```

Compare to the `MethodAccess.requiresAccessor()` attribute:

```java
  syn boolean MethodAccess.requiresAccessor() {
    MethodDecl decl = decl();
    if (decl.isPrivate() && decl.hostType() != hostType()) {
      return true;
    }
    return decl.isProtected()
        && !decl.hostPackage().equals(hostPackage())
        && !hostType().hasMethod(decl);
  }
```

### Jesper Öqvist - 2017-12-13

Fix error in VarAccess.requiresAccessor attribute

The VarAccess.requiresAccessor attribute should test if the host type of a
field declaration is different than the host type of the current access, rather
than testing if a field with the same name is declared in the host type of the
access.

fixes #231 (bitbucket)


→ <<cset b805aab82ca8>>
