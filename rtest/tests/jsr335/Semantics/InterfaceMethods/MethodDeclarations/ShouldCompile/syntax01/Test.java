// .result=COMPILE_PASS
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Test {
	
	
	public interface TestInterface {
		@SomeAnnot
		default void m() { }
	}
	
	
	@Target({ElementType.METHOD}) 
	public @interface SomeAnnot {  
		String information() default "None";
	}
}