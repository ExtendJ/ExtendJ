// .result=COMPILE_FAIL
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Test {
	public interface TestInterface {
		public int functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (@SomeAnnot int a) -> a + 4;
    }
	
	@Target({ElementType.METHOD}) 
	public @interface SomeAnnot {  
		String information() default "None";
	}
}