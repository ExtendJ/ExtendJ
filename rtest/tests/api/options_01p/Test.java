// Test that unknown non-standard options (-X) are reported as warnings.
// https://bitbucket.org/extendj/extendj/issues/305/some-command-line-arguments-fail-when-read
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.Options;

import static runtime.Test.testEqual;

public class Test {
  public static void main(String[] args) {
    Options opts = new Options();
    opts.addOptions(new String[] {
      // Javac options:
      "-XDuseUnsharedTable=true",
      "-Xlint",
      "-Xlint:deprecation",
      "-Xmaxerrs", "10",
      "-Xmaxwarns", "20",
      "-Xstdout", "filename",
      // Fake options:
      "-Xbork",
      "-Xoh:hi=mark"
    });
    // "10", "20", and "filename" are leftover arguments to -X options
    testEqual(3, opts.files().size());
  }
}
