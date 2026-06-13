public class Test {
	public static void main(String[] args) {
		boolean[] in = { true, false };
		boolean[] out = { true, false };

		for (int i = 0; i < in.length; ++i) {
			if (op(in[i]) != out[i]) {
				throw new Error("Incorrect conditional expression result (expected " +
						out[i] + " but was " + op(in[i]) + ")");
			}
		}
	}

	private static final boolean op(boolean in) {
		return in ? true : false;
	}
}
