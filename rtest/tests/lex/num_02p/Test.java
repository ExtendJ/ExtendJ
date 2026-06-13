// Test various legal numeric literals.
// .result=COMPILE_PASS
public class Test {
	float  _01 = 0x0.p0f;
	double _02 = 0x0P-010;
	double _03 = .0e+0;
	double _04 = 0x.Ep1;
	double _05 = 0xE.eEP+1020;
}
