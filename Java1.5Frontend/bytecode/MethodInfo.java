package bytecode;

import AST.*;
import java.util.*;
import AST.List;

import bytecode.Attributes.MethodAttributes;

class MethodInfo {
	private Parser p;
	String name;
	int flags;
	private MethodDescriptor methodDescriptor;
	private MethodAttributes attributes;
	
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
		attributes = new MethodAttributes(p);
	}
	
	public BodyDecl bodyDecl() {
    Signatures.MethodSignature s = attributes.methodSignature;
    Access returnType = (s != null && s.hasReturnType()) ? s.returnType() : methodDescriptor.type();
    List parameterList;
    if(isConstructor() && p.isInnerClass) {
      parameterList = methodDescriptor.parameterListSkipFirst();
      if(s != null) {
        Iterator iter = s.parameterTypes().iterator();
        if(iter.hasNext()) iter.next();
        for(int i = 0; iter.hasNext(); i++) {
          Access a = (Access)iter.next();
          ((ParameterDeclaration)parameterList.getChildNoTransform(i)).setTypeAccess(a);
        }
      }
    }
    else {
      parameterList = methodDescriptor.parameterList();
      if(s != null) {
        int i = 0;
        for(Iterator iter = s.parameterTypes().iterator(); iter.hasNext(); i++) {
          Access a = (Access)iter.next();
          ((ParameterDeclaration)parameterList.getChildNoTransform(i)).setTypeAccess(a);
        }
      }
    }
    if((flags & Flags.ACC_VARARGS) != 0) {
      int lastIndex = parameterList.getNumChild() - 1;
      ParameterDeclaration p = (ParameterDeclaration)parameterList.getChildNoTransform(lastIndex);
      parameterList.setChild(
        new VariableArityParameterDeclaration(
          p.getModifiersNoTransform(),
          p.getTypeAccessNoTransform(),
          p.getID(),
          p.getEmptyBracketListNoTransform()
        ),
        lastIndex
      );
    }
    List exceptionList = (s != null && s.hasExceptionList()) ? s.exceptionList() : attributes.exceptionList();

		if(isConstructor()) {
			return new ConstructorDecl(p.modifiers(flags), name, parameterList, 
                                 exceptionList, new Opt(), new Block());
		}
    else if(attributes.elementValue() != null) {
      return new AnnotationMethodDecl(p.modifiers(flags), returnType, name, 
                                      parameterList, new List(), exceptionList, 
                                      new Opt(new Block()), new Opt(attributes.elementValue()));
    }
		else if(s != null && s.hasFormalTypeParameters()) {
			return new GenericMethodDecl(p.modifiers(flags), returnType, name, parameterList, 
                            new List(), exceptionList, new Opt(new Block()), s.typeParameters(), new List());
    }
    else {
			return new MethodDecl(p.modifiers(flags), returnType, name, parameterList, 
                            new List(), exceptionList, new Opt(new Block()));
		}
	}
	
	private boolean isConstructor() {
		return name.equals("<init>");
	}

	public boolean isSynthetic() {
		return attributes.isSynthetic() || (flags & 0x1000) != 0;
	}
	
}
