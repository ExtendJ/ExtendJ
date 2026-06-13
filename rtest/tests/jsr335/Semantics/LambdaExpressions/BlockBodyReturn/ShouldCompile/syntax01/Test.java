// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public String functMethod(); 
	}
	
	//Taken from JSR335 section B, 15.27.2
	public static void main(String[] args) {
		TestInterface t = () -> { return "done"; };
    }
}