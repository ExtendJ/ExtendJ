// Test flaky compile error in exception checking.
// See issue 260.
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
