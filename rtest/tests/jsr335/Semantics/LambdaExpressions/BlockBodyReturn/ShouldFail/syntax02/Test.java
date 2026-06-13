// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public String functMethod(); 
	}
	
	public static void main(String[] args) {
		boolean cond = true;
		TestInterface t = () -> { if (cond) return "done"; else return; };
    }
}