package bytecode;

import AST.Access;


class FieldDescriptor {
	private Parser p;
	String typeDescriptor;
	
	public FieldDescriptor(Parser parser, String name) {
		p = parser;
		int descriptor_index = p.u2();
		typeDescriptor = ((CONSTANT_Utf8_Info) p.constantPool[descriptor_index]).string();
    if(Parser.VERBOSE)
		  p.println("  Field: " + name + ", " + typeDescriptor);
	}
	
	public Access type() {
		return new TypeDescriptor(p, typeDescriptor).type();
	}
	
	public boolean isBoolean() {
		return new TypeDescriptor(p, typeDescriptor).isBoolean();
	}
	
}
