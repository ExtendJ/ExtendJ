package bytecode;

import java.io.FileNotFoundException;

import AST.*;
import AST.InterfaceDecl;
import AST.List;
import AST.MemberClassDecl;
import AST.MemberInterfaceDecl;
import AST.MemberTypeDecl;
import AST.TypeDecl;
import AST.ElementValue;


class Attributes {
	protected Parser p;
	protected boolean isSynthetic;

	protected Attributes(Parser parser) {
    p = parser;
		isSynthetic = false;
	}

  protected void processAttribute(String attribute_name, int attribute_length) {
		if(attribute_name.equals("Synthetic")) {
			isSynthetic = true;
    } else {
			this.p.skip(attribute_length);
		}
  }

  protected void attributes() {
		int attributes_count = p.u2();
    if(Parser.VERBOSE)
      p.println("    " + attributes_count + " attributes:");
		for (int j = 0; j < attributes_count; j++) {
			int attribute_name_index = p.u2();
			int attribute_length = p.u4();
			String attribute_name = p.getCONSTANT_Utf8_Info(attribute_name_index).string();
      if(Parser.VERBOSE)
			  p.println("    Attribute: " + attribute_name + ", length: "
					+ attribute_length);
      processAttribute(attribute_name, attribute_length);
    }
  }

	public boolean isSynthetic() {
		return isSynthetic;
	}

  public static class FieldAttributes extends Attributes {
	  protected CONSTANT_Info constantValue;
    public FieldAttributes(Parser p) {
      super(p);
      attributes();
    }

    protected void processAttribute(String attribute_name, int attribute_length) {
		  if(attribute_name.equals("ConstantValue") && attribute_length == 2) {
		    int constantvalue_index = p.u2();
		    constantValue = p.getCONSTANT_Info(constantvalue_index);
      }
      else {
        super.processAttribute(attribute_name, attribute_length);
      }
    }

	  public CONSTANT_Info constantValue() {
		  return constantValue;
	  }
  }

  public static class MethodAttributes extends Attributes {
	  protected List exceptionList;
    protected ElementValue elementValue;
    public Signatures.MethodSignature methodSignature;

    public MethodAttributes(Parser p) {
      super(p);
      attributes();
    }

    protected void processAttribute(String attribute_name, int attribute_length) {
			if(attribute_name.equals("Exceptions")) {
				parseExceptions();
      } 
      else if(attribute_name.equals("AnnotationDefault")) {
        annotationDefault();
      }
      else if(attribute_name.equals("Signature")) {
        int signature_index = p.u2();
        String s = p.getCONSTANT_Utf8_Info(signature_index).string();
        methodSignature = new Signatures.MethodSignature(s);
      }
      else {
        super.processAttribute(attribute_name, attribute_length);
      }
    }

		private void parseExceptions() {
			int number_of_exceptions = p.u2();
      exceptionList = new List();
      if(Parser.VERBOSE)
        p.println("      " + number_of_exceptions + " exceptions:");
			for (int i = 0; i < number_of_exceptions; i++) {
				CONSTANT_Class_Info exception = p.getCONSTANT_Class_Info(p.u2());
        if(Parser.VERBOSE)
          p.println("        exception " + exception.name());
				exceptionList.add(exception.access());
			}
		}

		public List exceptionList() {
			return exceptionList != null ? exceptionList : new List();
		}

    public ElementValue elementValue() {
      return elementValue;
    }

    // 4.8.19
    private void annotationDefault() {
      elementValue = readElementValue();
    }
 
    // 4.8.15.1
    private ElementValue readElementValue() {
      char c = (char)p.u1();
      switch (c) {
        case 'e':
          Access typeAccess = new CONSTANT_Class_Info(this.p).access();
          int const_name_index = this.p.u2();
          String const_name = this.p.getCONSTANT_Utf8_Info(const_name_index).string();
          return new ElementConstantValue(typeAccess.qualifiesAccess(new VarAccess(const_name)));
        case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z': case 's':
          int const_value_index = p.u2();
          Expr e = this.p.getCONSTANT_Info(const_value_index).expr();
          return new ElementConstantValue(e);
        case 'c':
          int class_info_index = p.u2();
          String descriptor = this.p.getCONSTANT_Utf8_Info(class_info_index).string();
          e = new TypeDescriptor(p, descriptor).type();
          return new ElementConstantValue(e);
        case '@':
          return new ElementAnnotationValue(readAnnotation());
        case '[':
          int index = p.u2();
          List list = new List();
          for(int i = 0; i < index; i++) 
            list.add(readElementValue());
          return new ElementArrayValue(list);
        default:
          throw new Error("AnnotationDefault tag " + c + " not supported");
      }
    }
 
    // 4.8.15
    private Annotation readAnnotation() {
      Access typeAccess = new FieldDescriptor(p, "").type();
      int num_element_value_pairs = p.u2();
      List list = new List();
      for(int i = 0; i < num_element_value_pairs; i++) {
        int element_name_index = p.u2();
        String element_name = p.getCONSTANT_Utf8_Info(element_name_index).string();
        ElementValue element_value = readElementValue();
        list.add(new ElementValuePair(element_name, elementValue));
      }
      return new Annotation("Annotation", typeAccess, list);
    }

	
  }

  public static class TypeAttributes extends Attributes {
    TypeDecl typeDecl;
    TypeDecl outerTypeDecl;
    AST.Program classPath;
    public TypeAttributes(Parser p, TypeDecl typeDecl, TypeDecl outerTypeDecl, AST.Program classPath) {
      super(p);
      this.typeDecl = typeDecl;
      this.outerTypeDecl = outerTypeDecl;
      this.classPath = classPath;
      attributes();
    }

    protected void processAttribute(String attribute_name, int attribute_length) {
			if(attribute_name.equals("InnerClasses")) {
				innerClasses();
      }
		  else if(attribute_name.equals("Signature")) {
        int signature_index = p.u2();
        String s = p.getCONSTANT_Utf8_Info(signature_index).string();
        Signatures.ClassSignature classSignature = new Signatures.ClassSignature(s);
        if(classSignature.hasFormalTypeParameters())
          typeDecl = typeDecl.makeGeneric(classSignature.typeParameters());
      }
      else {
        super.processAttribute(attribute_name, attribute_length);
      }
    }
	
		protected void innerClasses() {
			int number_of_classes = this.p.u2();
      if(Parser.VERBOSE)
			  p.println("    Number of classes: " + number_of_classes);
      for (int i = 0; i < number_of_classes; i++) {
        if(Parser.VERBOSE)
          p.print("      " + i + "(" + number_of_classes + ")" +  ":");
        int inner_class_info_index = this.p.u2();
        int outer_class_info_index = this.p.u2();
        int inner_name_index = this.p.u2();
        int inner_class_access_flags = this.p.u2();
        String inner_name = "";
        if(inner_class_info_index > 0 && outer_class_info_index > 0 && inner_name_index >  0) {
          CONSTANT_Class_Info inner_class_info = this.p.getCONSTANT_Class_Info(inner_class_info_index);
          CONSTANT_Class_Info outer_class_info = this.p.getCONSTANT_Class_Info(outer_class_info_index);
          if(inner_class_info == null || outer_class_info == null) {
            System.out.println("Null");
          }
          String inner_class_name = inner_class_info.name();
          String outer_class_name = outer_class_info.name();
  
          if(Parser.VERBOSE)
            this.p.println("      inner: " + inner_class_name + ", outer: " + outer_class_name);
  
          if (inner_name_index != 0) {
            inner_name = this.p.getCONSTANT_Utf8_Info(inner_name_index).string();
          } else {
            inner_name = inner_class_info.simpleName();
          }
  
          if (inner_class_info.name().equals(p.classInfo.name())) {
            if(Parser.VERBOSE)
              p.println("      Class " + inner_class_name + " is inner");
            typeDecl.setID(inner_name);
            typeDecl.setModifiers(Parser.modifiers(inner_class_access_flags & 0x041f));
            if (this.p.outerClassInfo != null && this.p.outerClassInfo.name().equals(outer_class_info.name())) {
              MemberTypeDecl m = null;
              if (typeDecl instanceof ClassDecl) {
                m = new MemberClassDecl((ClassDecl)typeDecl);
                outerTypeDecl.addBodyDecl(m);
              } else if (typeDecl instanceof InterfaceDecl) {
                m = new MemberInterfaceDecl((InterfaceDecl)typeDecl);
                outerTypeDecl.addBodyDecl(m);
              }
            }
          }
          if (outer_class_info.name().equals(this.p.classInfo.name())) {
            if(Parser.VERBOSE)
              p.println("      Class " + this.p.classInfo.name()
                + " has inner class: " + inner_class_name);
            if(Parser.VERBOSE)
              p.println("Begin processing: " + inner_class_name);
            try {
              java.io.InputStream is = classPath.getInputStream(inner_class_name);
              if(is != null) {
                Parser p = new Parser(is, this.p.name);
                p.parse(typeDecl, outer_class_info, classPath, (inner_class_access_flags & bytecode.Flags.ACC_STATIC) == 0);
                is.close();
              }
              else {
                System.out.println("Error: ClassFile " + inner_class_name
                    + " not found");
              }
            } catch (FileNotFoundException e) {
              System.out.println("Error: " + inner_class_name
                  + " not found");
            } catch (Exception e) {
              e.printStackTrace();
              System.exit(1);
            }
            if(Parser.VERBOSE)
              p.println("End processing: " + inner_class_name);
          }
        }
  
      }
      if(Parser.VERBOSE)
        p.println("    end");
    }
	



  }

}
