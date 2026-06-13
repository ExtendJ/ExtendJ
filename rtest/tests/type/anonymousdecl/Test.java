// .result=COMPILE_PASS
public class Test {
	void m() {
		f(new Object() { });
	}
	void f(Object... params) {
	}
}
