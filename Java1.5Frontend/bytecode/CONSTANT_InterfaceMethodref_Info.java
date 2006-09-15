package bytecode;



class CONSTANT_InterfaceMethodref_Info extends CONSTANT_Info {
	public int class_index;
	public int name_and_type_index;

	public CONSTANT_InterfaceMethodref_Info(Parser parser) {
		super(parser);
		class_index = p.u2();
		name_and_type_index = p.u2();
	}

	public String toString() {
		return "InterfaceMethodRefInfo: " + p.constantPool[class_index] + " "
				+ p.constantPool[name_and_type_index];
	}
}