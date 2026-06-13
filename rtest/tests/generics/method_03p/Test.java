// Generic method most-specific algorithm test
class Test {
	public static void main(String[] args) {
		f(1, 2);
	}
	static <T> void f(Integer a, T b) {
	}
	static <T,U> void f(T a, U b) {
		throw new Error("wrong method called");
	}
}
