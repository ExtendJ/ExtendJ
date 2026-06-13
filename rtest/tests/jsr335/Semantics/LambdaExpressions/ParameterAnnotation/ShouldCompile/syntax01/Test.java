// .result=COMPILE_PASS
import java.lang.annotation.*;

public class Test {
	public interface TestInterface {
		public int functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (@SomeAnnot int a) -> a + 4;
    }
	
	@Target({ElementType.PARAMETER}) 
	public @interface SomeAnnot {  
		String information() default "None";
	}
}