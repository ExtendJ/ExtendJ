# Definitive assignment of a final field in instance initializer not found in the constructor

**Status:** resolved

extendj v8.0.1-4-gcc1f6e3

I tried to compile jEdit5.2.0 using extendj. While javac accept it, extendj find an error in org/gjt/sp/jedit/buffer/JEditBuffer.java. Here is a minimal example :

```
#!java
class B {   int value(){return 0;}    }

class A {
    //instance initializer
    {
        b=new B();
    }

    int j;

    A(){
        j = b.value();
    }

    final B b;
}

```

A final field assigned in an instance intializer and used in a constructor is not recognized as already assigned. This error appears when the field is declared after the initializer and the constructor.

I fixed it using the joined Assign aspect and by adding the following lines in java4/frontend /DefiniteAssignment.jrag l 300

```
#!java

if (b instanceof ConstructorDecl) {
        if(v.isInstanceVariable())
          for (BodyDecl bdecl : getBodyDeclList()){
            if (bdecl instanceof InstanceInitializer
                && ((InstanceInitializer)bdecl).assign(v))
              return true;

          }
        search = false;
 }
```
However I fear that the Assign aspect is not written in an idiomatic way. I would be happy to see it rewritten.

## Attachments

- [Assign.jadd](../attachments/128/Assign.jadd) (uploaded by Loïc Girault)

## Comments

### Jesper Öqvist - 2015-12-02

Interesting! I was able to shrink  the test case a bit more:

```
class B1 {
    {
        b = 0;
    }

    B1() {
        m(b);
    }

    void m(int x) {
    }

    final int b;
}
```

The definite assignment analysis has definitely been in need of cleanup a long while now. I actually pushed a commit earlier today that tidies up the code a little.

### Loïc Girault - 2015-12-02

I'm happy you find it interesting. Took me a few hour to fix it.

### Jesper Öqvist - 2015-12-03

Thank you for the bug report! Bug reports really help to improve ExtendJ!

I have fixed this issue now. The issue was caused in part by incorrect equations for assignedAfter and unassignedAfter on FieldDeclaration, and in part by incorrect handling of constructor calls in the definite assignment analysis. See the fix commit for more details (should be linked here soon).

### Loïc Girault - 2015-12-03

You're welcome. Actually I'm phd student building a refactoring tool on top of the frontend of extendj (with the name locking aspect done by Max Schäfer). So I really appreciate any improvement. (It's already a pretty cool compiler)

### Jesper Öqvist - 2015-12-03

Fix definite assignment error for blank finals

Fixed a definite assignment analysis error for blank final instance variables.

The problem was caused by not searching all instance initializers and instance
variable initializers for assignments to the given variable. Partly this was
caused by not incorrect handling of alternate constructor and implicit and
explicit super constructor calls, partly it was caused by FieldDeclaration
having incorrect equations for assignedAfter and unassignedAfter.

fixes #128 (bitbucket)


→ <<cset 472713ca9e9d>>

### Loïc Girault - 2015-12-03

thank you for the link !
