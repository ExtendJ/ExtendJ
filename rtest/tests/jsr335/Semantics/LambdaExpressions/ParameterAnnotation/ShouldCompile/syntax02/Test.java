// .result=COMPILE_PASS
import java.lang.annotation.*;

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a, int b, @SomeAnnot int c) -> a + b + c;
    }
	
	@Target({ElementType.PARAMETER}) 
	public @interface SomeAnnot {  
		String information() default "None";
	}
}