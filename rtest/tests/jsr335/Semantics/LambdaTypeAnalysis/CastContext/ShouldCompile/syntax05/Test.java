// .result=COMPILE_PASS
public class Test {
	public interface TestInterface {
		public String functMethod(int a, String b); 
	}
	
	public static String method(TestInterface t) {
		return t.functMethod(10, "Yes");
	}
	
	public static void main(String[] args) {
		System.out.println(method((TestInterface)(a,b) -> b.substring(a)));
    }
}