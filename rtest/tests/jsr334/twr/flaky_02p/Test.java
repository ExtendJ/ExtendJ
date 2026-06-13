// Test flaky compile error in exception checking.
// https://bitbucket.org/extendj/extendj/issues/260/flaky-compilation-failures-with-java-7-twr
// .result: COMPILE_PASS
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
public class Test {
  // These unrelated methods make the flaky error rate higher.
  void a1() {}
  void a2() {}
  void a3() {}
  void a4() {}
  void a5() {}
  void a6() {}
  void a7() {}
  void a8() {}
  void a9() {}
  void a10() {}
  void a12() {}
  void a13() {}
  void a14() {}
  void a15() {}
  void a16() {}
  void a17() {}
  void a18() {}
  void a19() {}
  public static void pass(Path path) throws IOException {
    try (ReadableByteChannel chan = Files.newByteChannel(path)) {
    }
  }
}
