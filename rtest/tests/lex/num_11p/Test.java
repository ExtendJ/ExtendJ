// Test various legal numeric literals.
// .result=COMPILE_PASS
public class Test {
	class E {
		double e, d;
		double e(E e5) {
			double d5e = .5-0- -0-5.;
			return .5e-5f-5.e+5d-d5e+e5.e;
		}
	}
}
