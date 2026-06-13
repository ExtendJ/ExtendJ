// Test flaky compile error in exception checking.
// https://bitbucket.org/extendj/extendj/issues/260/flaky-compilation-failures-with-java-7-twr
// .result: COMPILE_PASS
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
public class Test {
  public static void pass(Path path) throws IOException {
    try (ReadableByteChannel chan = Files.newByteChannel(path)) {
    }
  }
}
