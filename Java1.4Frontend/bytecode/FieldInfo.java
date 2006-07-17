package bytecode;

import AST.BodyDecl;
import AST.FieldDeclaration;
import AST.Opt;

class FieldInfo {
	private Parser p;
	String name;
	int flags;
	private FieldDescriptor fieldDescriptor;
	private Attributes attributes;
	
	public FieldInfo(Parser parser) {
		p = parser;
		flags = p.u2();
    if(Parser.VERBOSE)
      p.print("Flags: " + flags);
		int name_index = p.u2();
		name = ((CONSTANT_Utf8_Info) p.constantPool[name_index]).string();
		
		fieldDescriptor = new FieldDescriptor(p, name);
		attributes = new Attributes(p);
	}
	
	
	public BodyDecl bodyDecl() {
		FieldDeclaration f = new FieldDeclaration(
				this.p.modifiers(flags),
				fieldDescriptor.type(),
				name,
				new Opt()
		);
		if(attributes.constantValue() != null)
			if(fieldDescriptor.isBoolean()) {
				f.setInit(attributes.constantValue().exprAsBoolean());
			}
			else {
				f.setInit(attributes.constantValue().expr());
			}
		return f;
	}
	
	public boolean isSynthetic() {
		return attributes.isSynthetic();
	}
	
}
