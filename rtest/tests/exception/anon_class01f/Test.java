// Check that uncaught exception is reported in anonymous class declaration
// .result=COMPILE_FAIL
class Test {
	void m() throws Exception {
		new Test() {
			void m() {
				throw new Exception("not declared thrown");// error
			}
		}.m();
	}
}
