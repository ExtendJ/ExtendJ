package bytecode;



class CONSTANT_Utf8_Info extends CONSTANT_Info {
	public String string;

	public CONSTANT_Utf8_Info(Parser parser) {
		super(parser);
		string = p.readUTF();
	}

	public String toString() {
		return "Utf8Info: " + string;
	}

	public String string() {
		return string;
	}
}