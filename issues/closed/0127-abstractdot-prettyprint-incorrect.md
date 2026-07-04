# AbstractDot.prettyPrint() incorrect

**Status:** resolved

Disclaimer: I don't fully understand the AST stucture of array accesses, so I don't know if the following is described correctly.
```
#!Java
class Test {
	public static void main(String[] args) {
		String[] test = new String[1];
		test[0].toString();
	}
}
```
is printed as
```
#!Java
class Test {
	public static void main(String[] args) {
		String[] test = new String[1];
		test.[0].toString();
	}
}
```
probably because the AST contains two nested Dots, so needsDot() returns true incorrectly.

For my example class I fixed it with:
```
#!Java
aspect FixedPrettyPrintUtil {
	refine PrettyPrintUtil eq AbstractDot.needsDot() {
		if (getRight() instanceof ArrayAccess) {
			return false;
		} else if (getRight() instanceof Dot) {
			return !(((Dot) getRight()).getLeft() instanceof ArrayAccess);
		} else {
			return true;
		}
	}
}
```

## Comments

### Jesper Öqvist - 2015-11-30

Thank you for this bug report. You can use `AbstractDot.rightSide()` to access the expression to the immediate right of a dot, which means this should fix the issue:

```
-  syn boolean AbstractDot.needsDot() = !(getRight() instanceof ArrayAccess);
+  syn boolean AbstractDot.needsDot() = !(rightSide() instanceof ArrayAccess);
```

### Jesper Öqvist - 2015-11-30

Fix incorrect dot in AbstractDot pretty printing

fixes #127 (bitbucket)


→ <<cset ffda99de8883>>

### Jesper Öqvist - 2015-11-30

By the way, if you want to see the AST for a particular Java file you can use `org.extendj.JavaDumpTree`. For example:

```
java -cp extendj.jar org.extendj.JavaDumpTree Test.java
```
