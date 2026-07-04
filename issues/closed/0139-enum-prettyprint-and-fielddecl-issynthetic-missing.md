# Enum PrettyPrint and FieldDecl.isSynthetic missing

**Status:** resolved

Just parsing and pretty printing back this code :
```
#!java
enum E {
    A, B, C;
}
```
gives the following code :
```
#!java
enum E {
A(),
B(),
C(),
;
private static final synthetic E[] $VALUES = new E[0x3] { p.E.A, p.E.B, p.E.C };
public static final synthetic E[] values() {
  return (E[]) p.E.$VALUES.clone();
}
public static synthetic E valueOf(String s) {
  return (E) java.lang.Enum.valueOf(E.class, s);
}
}
```

To correct it and get the following printing :

```
#!java
enum E {
A,
B,
C
;
}
```

eq FieldDecl.isSynthetic() = getModifiers().isSynthetic(); needs to be added in java4/frontend/Modifiers.jrag

MethodDecl.prettyPrint and FieldDecl.prettyPrint bodies have to be enclosed in a if(!isSynthetic()) conditionnal and I modified EnumDecl.prettyPrint and EnumConstant.prettyPrint in java5/fronted/Enums.jrag
```
#!java
 public void EnumDecl.prettyPrint(PrettyPrinter out) {
    if (!docComment.isEmpty()) {
      out.print(docComment);
    }
    out.print(getModifiers());
    out.print("enum " + name());
    if (getNumImplements() > 0) {
      out.print(" implements ");
      out.join(getImplementsList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(" {");
    for (int i = 0; i < getNumBodyDecl(); i++) {
      BodyDecl d = getBodyDecl(i);
      out.print(d);
      if (d instanceof EnumConstant) {
        if (i + 1 < getNumBodyDecl()) {
          if (getBodyDecl(i + 1) instanceof EnumConstant)
            out.print(",");
          else {
            out.println();
            out.print(";");
          }
        }
      }
    }
    out.println();
    out.print("}");
  }

  public void EnumConstant.prettyPrint(PrettyPrinter out) {
    out.println();
    out.print(getModifiers());
    out.print(getID());
    if (getNumArg() > 0) {
        out.print("(");
        out.join(getArgList(), new PrettyPrinter.Joiner() {
          @Override
          public void printSeparator(PrettyPrinter out) {
            out.print(", ");
          }
        });
        out.print(")");
    }
    if (getNumBodyDecl() > 0) {
      out.print(" {");
      for (int i=0; i < getNumBodyDecl(); i++) {
        BodyDecl d = getBodyDecl(i);
        out.print(d);
      }
      out.print("}");
    }
  }
```

## Comments

### Jesper Öqvist - 2016-02-23

There are several things that need to be fixed here:

* Enum constructors are transformed by adding implicit parameters. The original version of the enum constructors should be printed rather than the transformed version.
* Synthetic body declarations should not be printed (`values`, `getOf`).
* The enum constant argument lists can be skipped if empty.

The first point is difficult to fix because it requires some refactoring of the way enum constructors are transformed. The other points are simpler to fix.

### Jesper Öqvist - 2016-02-23

Improve enum pretty printing

Improved enum pretty printing indentation, skip printing synthetic enum
members, skip argument list of enum constant when not needed.

see #139 (bitbucket)


→ <<cset 7b453550125e>>

### Jesper Öqvist - 2016-02-23

Refactor enum constructor transforms

Replaced enum constructor transformation side effects by using NTAs.

fixes #139 (bitbucket)


→ <<cset d6d323d39a24>>
