// Test constant switch expressions with various binary literals.
// .result=COMPILE_PASS
public class Test {
  void foo(int i) {
    switch (i) {
      case 0:
      case ((0b0 == 0) ? 1 : 0):
      case ((0b1 == 1) ? 2 : 0):
      case ((0b10 == 2) ? 3 : 0):
      case ((-0b000010 == -2) ? 4 : 0):
      case ((-0b111L == -7l) ? 5 : 0):
      case ((0b00000000_11110100_10001010_00000000 == 0x00F48A00) ? 6 : 0):
      case ((0b00000000_11110100_10001010_00000000_11001010_11111110_10111010_10111110L == 0x00F48A00_CafeBabeL) ? 7 : 0):
      case ((0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111L == 0xFFFFFFFF_FFFFFFFFl) ? 8 : 0):
      case ((0b11111111111111111111111111111111_11111111111111111111111111111111L == -1L) ? 9 : 0):
      case ((-0b11111111111111111111111111111111 == 1) ? 10 : 0):
    }
  }
}
