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
