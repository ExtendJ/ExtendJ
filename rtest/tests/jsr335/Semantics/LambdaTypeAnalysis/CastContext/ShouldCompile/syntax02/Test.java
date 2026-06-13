// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a); 
	}
	
	public interface SubTestInterface extends TestInterface {
		public int functMethod(int a);
	}
	
	public static void main(String[] args) {
		TestInterface t = (SubTestInterface)(int a) -> a + 6;
    }
}