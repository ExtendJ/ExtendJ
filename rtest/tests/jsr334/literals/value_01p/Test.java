// Test that the correct constant values are generated in bytecode.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    System.out.println("Decimal integers:");
    System.out.println(2_3_4_5_6);
    System.out.println(1__0);
    System.out.println(1___________________________0);
    System.out.println();

    System.out.println("Hexadecimal integers:");
    System.out.println(0x1_2);
    System.out.println(0xABC_DEF_01L);
    System.out.println(0xfffffffd);
    System.out.println(0xffffffff);
    System.out.println();

    System.out.println("Octal integers:");
    System.out.println(0_0);
    System.out.println(-0_127);
    System.out.println(012_7);
    System.out.println();

    System.out.println("Binary integers:");
    System.out.println(0b00_1L);
    System.out.println(0b1_0);
    System.out.println(-0b1111_00__00L);
    System.out.println(0b100000000000000000000000000000000000000000000000000000000000000L);
    System.out.println(0b1000000000000000000000000000000000000000000000000000000000000000L);
    System.out.println();

    System.out.println("Decimal floats:");
    System.out.println(3_0.);
    System.out.println(.0_5);
    System.out.println(.0_5e-1_00);
    System.out.println(5e+1_00);
    System.out.println(.7e+1__0);
    System.out.println(3_0.85d);
    System.out.println(0.123_4);
    System.out.println(1_0.0);
    System.out.println(1_0f);
    System.out.println(0.1__0D);
    System.out.println(1_2.f);
    System.out.println(-1_2.3);
    System.out.println();

    System.out.println("Hexadecimal floats:");
    System.out.println(0xA.F_FP1F);
    System.out.println(-0x4.A_B_CP1_4);
    System.out.println(0xF_000.P1_____4);
    System.out.println(0xC_a_f_e.B_a_b_eP1);
    System.out.println(0x12.34P42d);
  }
}
