// Test that ExtendJ handles large expressions.
// .result=COMPILE_PASS
class Test {
  int i01,i02,i03,i04,i05,i06,i07,i08,i09,
      i11,i12,i13,i14,i15,i16,i17,i18,i19,
      i21,i22,i23,i24,i25,i26,i27,i28,i29,
      i31,i32,i33,i34,i35,i36,i37,i38,i39,
      i41,i42,i43,i44,i45,i46,i47,i48,i49,
      i51,i52,i53,i54,i55,i56,i57,i58,i59,
      i61,i62,i63,i64,i65,i66,i67,i68,i69,
      i71,i72,i73,i74,i75,i76,i77,i78,i79,
      i81,i82,i83,i84,i85,i86,i87,i88,i89,
      i91,i92,i93,i94,i95,i96,i97,i98,i99;
  int f() {
    return i01+i02+i03+i04+i05+i06+i07+i08+i09
        +i11+i12+i13+i14+i15+i16+i17+i18+i19
        +i21+i22+i23+i24+i25+i26+i27+i28+i29
        +i31+i32+i33+i34+i35+i36+i37+i38+i39
        +i41+i42+i43+i44+i45+i46+i47+i48+i49
        +i51+i52+i53+i54+i55+i56+i57+i58+i59
        +i61+i62+i63+i64+i65+i66+i67+i68+i69
        +i71+i72+i73+i74+i75+i76+i77+i78+i79
        +i81+i82+i83+i84+i85+i86+i87+i88+i89
        +i91+i92+i93+i94+i95+i96+i97+i98+i99;
  }
}
