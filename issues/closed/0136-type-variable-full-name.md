# Type Variable full name

**Status:** resolved

The code to compute the full name of a type variable is the following (java5/frontend/Generics.java):

```
#!java
eq TypeVariable.fullName() {
      if (getParent().getParent() instanceof TypeDecl) {
        TypeDecl typeDecl = (TypeDecl) getParent().getParent();
        return typeDecl.fullName() + "@" + name();
      }
      return super.fullName();
    }

```
however this means that in the following code the three type variables T have the same full name.

```
#!java

interface I<T> {
    <T> void m(T t);
    <T> void m2(T t);
}
```
I think this is a bug it could be fixed by adding this to the fullName equation  :

```
#!java
     if (getParent().getParent() instanceof GenericMethodDecl) {
          GenericMethodDecl genericMethodDecl =
                                  (GenericMethodDecl) getParent().getParent();
          return  genericMethodDecl.hostType().typeName()+"."+
                                genericMethodDecl.signature() + "@" + name();
      }
```

but then there is some pathological cases that cause a stackoverflow like this method of java.util.Comparator :

```
#!java
 <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor,
                                        Comparator<? super U> keyComparator);
```

## Comments

### Jesper Öqvist - 2016-02-18

Can you please provide a small example that causes the stack overflow exception? I tried creating a method with the same signature as the one you describe and causing an error message to print the fullName() attribute of both parameter types using this test class:

```java
import java.util.Comparator;
import java.util.function.Function;

class Test<T> {
  <U> Comparator<T> thenComparing(Function<? super T, ? extends U> a, Comparator<? super U> b) {
    a = b;
    return null;
  }
}
```

But when I use ExtendJ 8.0.1-45-gedaf31d Java SE 8, I get the following error message instead of a stack overflow exception:

```
Test.java:6: error: can not assign a of type java.util.function.Function<wildcards.? super Test@T, wildcards.? extends U> a value of type java.util.Comparator<wildcards.? super U>
```

Is the duplicate fullName for type variables a big issue? As far as I can tell ExtendJ only uses this for error reporting. Javac has a completely different way of reporting error messages involving type variables and gives them unique names like this:

```
Test.java:6: error: incompatible types: Comparator<CAP#1> cannot be converted to Function<? super T,? extends U>
    a = b;
        ^
  where U,T are type-variables:
    U extends Object declared in method <U>thenComparing(Function<? super T,? extends U>,Comparator<? super U>)
    T extends Object declared in class Test
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Object super: U from capture of ? super U
1 error
```

Something similar could be implemented in ExtendJ but the stack overflow exception is more important to fix, so I'd rather fix that right now and deal with the duplicate type variable names later.

### Jesper Öqvist - 2016-02-18

Added the ExtendJ error message in the previous comment.

### Loïc Girault - 2016-02-18

My project was using an older version of extendj. I'm doing the upgrade but the version I had was from before the change of grammar concerning variable and field multiple declarations. It broke a lot of things. Anyway as soon as I'have finished this task I try to reproduce the bug. For info the minimal exemple I'm using is the following :
```
#!java
interface Comparator<T> {
  <U> Comparator<T> thenComparing(Comparator<? super U> b);
}
```

### Loïc Girault - 2016-02-19

I confirm the StackOverflow error with the previous example. The recursivity comes from the signature() method and more precisely to the call to type() on the ParameterDeclaration

```
#!java
syn lazy String MethodDecl.signature() {
    StringBuilder sb = new StringBuilder();
    sb.append(name() + "(");
    for (int i = 0; i < getNumParameter(); i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(getParameter(i).type().typeName());
    }
    sb.append(")");
    return sb.toString();
  }
```
I use the following fix : instead of calling  getParameter(i).type().typeName(), I use getParameter(i).typeNameInSig() with the following code :

```
#!java
 public String ParamterDeclaration.typeNameInSig(){
	if(getTypeAccess() instanceof ParTypeAccess){
		ParTypeAccess pta = (ParTypeAccess) getTypeAccess();
		return pta.genericDecl().getID();
	}
	return type().name();
}
```

### Jesper Öqvist - 2016-02-27

Can you post the stack trace from the StackOverflow error please? I still have trouble reproducing the problem. Does this happen if you just run the regular `extendj.jar` on the `Comparator` interface alone?

### Jesper Öqvist - 2016-03-04

Thank you for providing the test @lorilan, I made some changes and implemented a similar test in a little different way. I made a change to the test framework so I can add `@EXTENDJ_LIB` in the test classpath for testing ExtendJ APIs, and then I added this test:

```java
// Test the fullName() attribute evaluated on a TypeVariable.
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@
import org.extendj.ast.BytecodeParser;
import org.extendj.ast.BytecodeReader;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericMethodDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeVariable;

import org.extendj.ast.ASTNode;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

import static runtime.Test.testEqual;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    test();
  }

  static void test() {
    String code = "package p;\n"
        + "interface Comparator<T> {"
        + "  <U> Comparator<T> thenComparing(Comparator<? super U> b);\n"
        + "}\n";
    CompilationUnit cu = parseCompilationUnit(code);
    GenericMethodDecl method = (GenericMethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
    TypeVariable var = method.getTypeParameter(0);
    testEqual("p.U", var.fullName());
  }

  /**
   * Parse a compilation unit.
   */
  static CompilationUnit parseCompilationUnit(String code) {
    try {
      JavaParser sourceParser = new JavaParser() {
        @Override
        public CompilationUnit parse(java.io.InputStream is, String fileName)
            throws IOException, beaver.Parser.Exception {
          return new org.extendj.parser.JavaParser().parse(is, fileName);
        }
      };
      BytecodeReader bytecodeParser = new BytecodeReader() {
        @Override
        public CompilationUnit read(InputStream is, String fullName, Program p)
            throws FileNotFoundException, IOException {
          return new BytecodeParser(is, fullName).parse(null, null, p);
        }
      };
      Program program = new Program();
      program.initBytecodeReader(bytecodeParser);
      program.initJavaParser(sourceParser);
      CompilationUnit unit = sourceParser.parse(
          new ByteArrayInputStream(code.getBytes("UTF-8")), "<no path>");
      program.addCompilationUnit(unit);
      unit = program.getCompilationUnit(0);
      unit.setClassSource(new FileClassSource(new SourceFolderPath("<no source>"), "<no path>"));
      unit.setFromSource(true);
      return unit;
    } catch (Exception e) {
      e.printStackTrace();
      fail("failed to parse test code");
    }
    // Failed.
    return null;
  }
}
```

As you can see, I test calling `fullName()` on the type variable. In the latest version of ExtendJ (commit 6dc24f7) this returns `p.U` without a stack overflow. I will update ExtendJ so that it prints `p.Comparator.thenComparing(Comparator)@U` as you check for in your test.

The test above is here: https://bitbucket.org/extendj/regression-tests/src/2c1ad2d199d15d65a77d7036f7917113874a394e/tests/api/fullname_01p/Test.java?at=master&fileviewer=file-view-default

I have been refactoring a lot in ExtendJ recently, I hope it does not cause too many problems for you or other people who use ExtendJ. The goal of these refactorings is to try to make ExtendJ more declarative with fewer side effects in attributes - that should make it safer to change attribute evaluation order and parallelize the compiler. Most of the big changes are done now though, and I will try to make a new stable version soon.

### Loïc Girault - 2016-03-04

Thanks for the update !

I'm building a dependency graph of the AST by traversing it. When you changed the grammar to generalize variable and field declaration and introduced the Declarator, it caused me some problem that took me some time to fix. But it is clear that the new abstract grammar is simpler, so it's for the best ^_^

Keep me posted about the result when you print ```p.Comparator.thenComparing(Comparator)@U```

### Jesper Öqvist - 2016-03-04

More descriptive TypeVariable.fullName() attribute

The TypeVariable.fullName() attribute now includes declaration context: either
the qualified type name of the host type declaration, or the qualified
signature of the host method/constructor.

see #136 (bitbucket)


→ <<cset 1cc28798662c>>

### Jesper Öqvist - 2016-03-04

@lorilan I have updated the behaviour of `TypeVariable.fullName()` now.

Let me know if there is still a stack overflow problem!

### Loïc Girault - 2016-03-07

I've rerun my test and no stack overflow for me !
Thank you !
