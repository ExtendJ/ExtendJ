# Field access chain on a method invocation is resolved as a package name

*ExtendJ 11.0.0-g25eca834 Java SE 8*

When the right hand side of an assignment is a chain of two or more field
accesses on a method invocation, the chain is syntactically classified as a
package name instead of an expression name.

```java
class Thing {
  String label;
}

class Other {
  Thing thing;
}

public class Test {
  Other get() {
    return null;
  }

  void test() {
    String local;
    local = get().thing.label;
  }
}
```

Expected result: compiles.

Actual result:

```
error: package .thing not found.
error: no field named label is accessible
```

The name of the package in the message is empty because the qualifier is an
expression rather than a name. A longer chain names more of it, e.g.,
`get().thing.other.label` reports `package .thing.other not found`.

The classification only goes wrong for this one shape with 2+ field
accesses. For example,

* `String init = get().thing.label;` is accepted
* `local = get().thing;` is accepted - only 2+ field parts fail
* `local = get().thing.label.trim();` is accepted - the chain has to end with a field access
* `local = new Other().thing.label;` is accepted
* `Other other = get(); local = other.thing.label;` is accepted

Found while compiling jEdit 5.2.0 (issue 129), which hits it in
`org/gjt/sp/jedit/gui/PanelWindowContainer.java:233`:

```java
mostRecent = dockables.get(0).factory.name;
```

See regression test `rtest/tests/name/resolve_02p`.
