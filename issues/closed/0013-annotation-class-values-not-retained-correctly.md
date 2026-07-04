# Annotation class values not retained correctly

**Status:** resolved

Class values in annotations are not retained correctly for annotation types with runtime retention policy.

Test case:
````java
import java.lang.annotation.*;
import java.lang.reflect.*;

public class Test {
	@Retention(RetentionPolicy.RUNTIME) @interface Testable { Class value(); }

	@Testable(Test.class) public void m() { }

	public static void main(String[] args) throws Exception {
		Method[] methods = Test.class.getDeclaredMethods();
		for (Method m: methods) {
			if (m.getName().equals("m")) {
				Object annotation = m.getAnnotation(Testable.class);
				System.out.println("annotation: " + annotation);
				Testable t = (Testable) annotation;
				System.out.println("value: " + t.value());
			}
		}
	}
}
````

Expected output:
````
annotation: @Test$Testable(value=class Test)
value: class Test
````

Actual output when compiled with JastAddJ `7.1.1-46-g0cd3fe5 Java SE 7`:
````
annotation: @Test$Testable(value=class java.lang.Class)
value: class java.lang.Class
````

## Comments

### Jesper Öqvist - 2013-06-03

The problem here is that the type descriptor for the class value is computed from the type of the class access expression, not the class access itself!

### Jesper Öqvist - 2013-06-03

Fixed annotation codegen bug

- added attribute classAccess to compute the hostType of a class access
- fixed error in annotation bytecode for Class-valued attributes
- fixes #13


→ <<cset 05952a055853>>
