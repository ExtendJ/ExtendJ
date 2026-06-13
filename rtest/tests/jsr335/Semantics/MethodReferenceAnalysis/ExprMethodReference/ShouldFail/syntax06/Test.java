// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(List<Integer> list);
	}
	
	public int method(ArrayList<Integer> list) {
		return 4;
	}
	
	public void testMethod() {
		Test t = new Test();
		A a = t::method;
	}
}