// .result=COMPILE_FAIL
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a, @SomeAnnot int b, int c) -> a + b + c;
    }
	
	@Target({ElementType.METHOD}) 
	public @interface SomeAnnot {  
		String information() default "None";
	}
}