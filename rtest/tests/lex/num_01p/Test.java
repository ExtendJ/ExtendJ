// Test integer literal lexing.
// .result=COMPILE_PASS
public class Test {
	int    x = 0;
	int    y = 0xE-x;
	int    z = 0xe+x;
	double f = 0x.ep1-x;
	double g = 0xE.P9+x;
}
