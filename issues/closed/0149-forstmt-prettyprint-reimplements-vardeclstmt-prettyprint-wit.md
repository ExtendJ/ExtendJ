# ForStmt.prettyPrint reimplements VarDeclStmt.prettyPrint with errors

**Status:** resolved

Parsing this sample of code :
```
#!java
package p;

import java.util.Enumeration;
import java.util.Vector;

class A {
    void m(Vector<Object> v){
        for(Enumeration<?> e = v.elements(); e.hasMoreElements();){
            Object o = e.nextElement();
        }
    }
}
```
and then pretty printing it gives :
```
#!java
package p;
import java.util.Enumeration;
import java.util.Vector;

class A {
  void m(Vector<Object> v) {
    for (java.util.Enumeration<wildcards.? extends java.lang.Object> e = v.elements(); e.hasMoreElements(); ) {
      Object o = e.nextElement();
    }
  }
}
```
The problem is in java4/frontend/PrettyPrintUtil.jrag. Instead of reusing VarDeclStmt.prettyprint the ForStmt.prettyPrint reimplements it  with some kind of special case for arrays.

To solve it, I've replaced
```
#!java
public void ForStmt.prettyPrint(PrettyPrinter out) {
    out.print("for (");
    if (getNumInitStmt() > 0) {
      if (getInitStmt(0) instanceof VarDeclStmt) {
        /* 36 lines of code (clone ?) */
      } else if (getInitStmt(0) instanceof ExprStmt) {
       /* code */
      } else {
        throw new Error("Unexpected initializer in for loop: " + getInitStmt(0));
      }
    }
    out.print("; ");

    /*end of the method*/
}
```

by the following
```
#!java
public void ForStmt.prettyPrint(PrettyPrinter out) {
    out.print("for (");
    if (getNumInitStmt() > 0) {
      if (getInitStmt(0) instanceof VarDeclStmt) {
           out.print((VarDeclStmt)getInitStmt(0));
      } else if (getInitStmt(0) instanceof ExprStmt) {
       /* code */
       out.print("; ");
      } else {
        throw new Error("Unexpected initializer in for loop: " + getInitStmt(0));
      }
    }

    /*end of the method*/
}
```

## Comments

### Jesper Öqvist - 2016-03-08

Thank you for the bug report and the fix works nicely!

### Jesper Öqvist - 2016-03-08

Simplified ForStmt pretty printing

Simplified the printing of the initializer part of for statements.

fixes #149 (bitbucket)


→ <<cset fa6a449d1c97>>

### Loïc Girault - 2016-03-11

In the end my fix has a problem. I've encoutered a for stmt without init stmt. The result is a missing semicolon
Solution: add an else branch in the pretty printing
```
#!java
public void ForStmt.prettyPrint(PrettyPrinter out) {
    out.print("for (");
    if (getNumInitStmt() > 0) {
     /* ... */
    }
    else
       out.print("; ");

    /* ... */
}
```

### Jesper Öqvist - 2016-03-12

Fix error in ForStmt pretty printing

Fixed missing semicolon when there is no init statement.

see #149 (bitbucket)


→ <<cset e82120a7331d>>
