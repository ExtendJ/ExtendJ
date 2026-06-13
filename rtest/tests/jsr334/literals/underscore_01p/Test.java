// Literals with underscores.
// .result=COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    long foo, O_O;
    double bar;

    O_O = 0_0;// 0_0

    // Decimal literals:
    foo = 2_3_4_5_6;
    foo = 1__0;
    foo = 1___________________________0;

    // Hexadecimal literals:
    foo = 0x1_2;
    foo = 0xABC_DEF_01L;

    // Octal:
    foo = 0_127;
    foo = 012_7;

    // Binary:
    foo = 0b00_1L;
    foo = 0b1_0;
    foo = 0b1111_00__00L;

    // Floating point:
    bar = 3_0.;
    bar = .0_5;
    bar = .0_5e-1_00;
    bar = 5e+1_00;
    bar = .7e+1__0;
    bar = 3_0.85d;
    bar = 0.123_4;
    bar = 1_0.0;
    bar = 1_0f;
    bar = 0.1__0D;
    bar = 1_2.f;
    bar = 1_2.3;

    // Hexadecimal floating point:
    bar = 0xA.F_FP1F;
    bar = 0x4.A_B_CP1_4;
    bar = 0xF_000.P1_____4;
    bar = 0xC_a_f_e.B_a_b_eP1;
  }
}
