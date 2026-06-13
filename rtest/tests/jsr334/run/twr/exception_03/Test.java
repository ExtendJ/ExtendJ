// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * A simple throw with no other exceptions during automatic closing of
 * resources will not add any suppressed exceptions to the originally
 * thrown exception.
 */
public class Test {
	static class BenignResource implements AutoCloseable {
		public boolean isClosed = false;
		public void close() {
			isClosed = true;
		}
	}

	public static void main(String[] args) {
		try (BenignResource r = new BenignResource()) {
			int i = 1;
			if (i == 1) throw new Exception();
		} catch (Exception e) {
			testEqual("suppressed exceptions",
					0, e.getSuppressed().length);
			return;
		}
		fail("Exception was expected");
	}
}
