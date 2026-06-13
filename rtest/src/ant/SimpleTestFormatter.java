package ant;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

/**
 * An Ant output formatter for improved console logging of test results.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class SimpleTestFormatter implements JUnitResultFormatter {

	private PrintStream out = System.out;
	private int numRun = 0;
	private int numFail = 0;
	private int numErr = 0;
	private final Set<Test> failed = new HashSet<Test>();
	private static final boolean verbose = System.getProperty("verbose", "").equals("true");

	@Override
	public void addError(Test test, Throwable error) {
		failed.add(test);
		numErr += 1;
		logResult(test, "ERR");
		out.println(error.getMessage());
	}

	@Override
	public void addFailure(Test test, AssertionFailedError failure) {
		failed.add(test);
		numFail += 1;
		logResult(test, "FAIL");
		out.println(failure.getMessage());
	}

	@Override
	public void endTest(Test test) {
		if (verbose && !failed.contains(test)) {
			logResult(test, "PASS");
		}
	}

	@Override
	public void startTest(Test test) {
		numRun += 1;
	}

	@Override
	public void endTestSuite(JUnitTest testSuite) throws BuildException {
		out.println(String.format("Test completed: %d runs, %d failures, %d errors",
					numRun, numFail, numErr));
		out.flush();
	}

	@Override
	public void setOutput(OutputStream out) {
		this.out = new PrintStream(out);
	}

	@Override
	public void setSystemError(String err) {
		// don't echo test error output
	}

	@Override
	public void setSystemOutput(String out) {
		// don't echo test output
	}

	@Override
	public void startTestSuite(JUnitTest testSuite) throws BuildException {
		numRun = 0;
		numFail = 0;
		numErr = 0;
	}

	private void logResult(Test test, String result) {
		out.println("[" + result + "] " + String.valueOf(test));
		out.flush();
	}
}
