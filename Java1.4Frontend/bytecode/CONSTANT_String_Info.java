package bytecode;



class CONSTANT_String_Info extends CONSTANT_Info {
	public int string_index;

	public CONSTANT_String_Info(Parser parser) {
		super(parser);
		string_index = p.u2();
	}

  public Expr expr() {
    CONSTANT_Utf8_info i = p.constantPool[string_index];
    return i.string;
  }

	public String toString() {
		return "StringInfo: " + p.constantPool[string_index];
	}
}
