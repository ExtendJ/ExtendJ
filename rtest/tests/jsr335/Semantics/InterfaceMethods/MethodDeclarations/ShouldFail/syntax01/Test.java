// .result=COMPILE_FAIL
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Test {
	
	public interface TestInterface {
		@SomeAnnot
		default void m() { }
	}
	
	
	@Target({ElementType.PARAMETER}) 
	public @interface SomeAnnot {  
		String information() default "None";
	}
}