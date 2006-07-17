package bytecode;

import AST.Block;
import AST.BodyDecl;
import AST.ConstructorDecl;
import AST.List;
import AST.MethodDecl;
import AST.Opt;


class MethodInfo {
	private Parser p;
	String name;
	int flags;
	private MethodDescriptor methodDescriptor;
	private Attributes attributes;
	
	public MethodInfo(Parser parser) {
		p = parser;
		flags = p.u2();
    if(Parser.VERBOSE)
      p.print("  Flags: " + Integer.toBinaryString(flags));
		int name_index = p.u2();
    CONSTANT_Info info = p.constantPool[name_index];
    if(info == null || !(info instanceof CONSTANT_Utf8_Info)) {
      System.err.println("Expected CONSTANT_Utf8_Info but found: " + info.getClass().getName());
      //if(info instanceof CONSTANT_Class_Info) {
      //  System.err.println(" found CONSTANT_Class_Info: " + ((CONSTANT_Class_Info)info).name());
      //  name = ((CONSTANT_Class_Info)info).name();
      //}
    } 
    name = ((CONSTANT_Utf8_Info)info).string();
		methodDescriptor = new MethodDescriptor(p, name);
		attributes = new Attributes(p);
	}
	
	public BodyDecl bodyDecl() {
		if(isConstructor()) {
			return new ConstructorDecl(
				this.p.modifiers(flags),
				name,
        Parser.isInnerClass ? methodDescriptor.parameterListSkipFirst() : methodDescriptor.parameterList(),
				attributes.exceptionList(),
        new Opt(),
				new Block()
			);
		}
		else {
			return new MethodDecl(
				this.p.modifiers(flags),
				methodDescriptor.type(),
				name,
				methodDescriptor.parameterList(),
				new List(),
				attributes.exceptionList(),
				new Opt(new Block())
			);
		}
		
	}
	
	private boolean isConstructor() {
		return name.equals("<init>");
	}

	public boolean isSynthetic() {
		return attributes.isSynthetic() || (flags & 0x1000) != 0;
	}
	
}
