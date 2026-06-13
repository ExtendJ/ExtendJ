// Generic method applicability test:
// test that method invocation conversion considers type argument bounds
class Test {
	public static void main(String[] args) {
		f(1);
	}

	static <T extends Integer> void f(T __) {
	}
}
