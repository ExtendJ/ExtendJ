// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		 
	}
	
	public static void main(String[] args) {
		TestInterface t = () -> {};
    }
}
