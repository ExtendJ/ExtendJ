package bytecode;



class CONSTANT_String_Info extends CONSTANT_Info {
	public int string_index;

	public CONSTANT_String_Info(Parser parser) {
		super(parser);
		string_index = p.u2();
	}

	public String toString() {
		return "StringInfo: " + p.constantPool[string_index];
	}
}