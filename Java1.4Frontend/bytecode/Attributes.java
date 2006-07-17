package bytecode;

import java.io.FileNotFoundException;

import AST.ClassDecl;
import AST.InterfaceDecl;
import AST.List;
import AST.MemberClassDecl;
import AST.MemberInterfaceDecl;
import AST.MemberTypeDecl;
import AST.TypeDecl;


class Attributes {
	private Parser p;
	private List exceptionList;
	private boolean isSynthetic;
	private CONSTANT_Info constantValue;

	public Attributes(Parser parser) {
		this(parser, null, null, null);
	}
	
	public Attributes(Parser parser, TypeDecl typeDecl, TypeDecl outerTypeDecl, AST.Program classPath) {
		p = parser;
		exceptionList = new List();
		isSynthetic = false;
		
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
			if (attribute_name.equals("Exceptions")) {
				exceptions();
			} else if(attribute_name.equals("ConstantValue") && attribute_length == 2) {
				constantValues();
			} else if (attribute_name.equals("InnerClasses")) {
				innerClasses(typeDecl, outerTypeDecl, classPath);
			} else if (attribute_name.equals("Synthetic")) {
				isSynthetic = true;
			} else {
				this.p.skip(attribute_length);
			}
		}
		
	}

	
	public void innerClasses(TypeDecl typeDecl, TypeDecl outerTypeDecl, AST.Program classPath) {
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
              p.parse(typeDecl, outer_class_info, classPath);
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
	
	private void exceptions() {
		int number_of_exceptions = this.p.u2();
    if(Parser.VERBOSE)
      p.println("      " + number_of_exceptions + " exceptions:");
		for (int i = 0; i < number_of_exceptions; i++) {
			CONSTANT_Class_Info exception = this.p.getCONSTANT_Class_Info(this.p.u2());
      if(Parser.VERBOSE)
        p.println("        exception " + exception.name());
			exceptionList.add(exception.access());
		}
	}
	
	private void constantValues() {
		int constantvalue_index = this.p.u2();
		constantValue = this.p.getCONSTANT_Info(constantvalue_index);
	}
	
	public List exceptionList() {
		return exceptionList;
	}
	
	public CONSTANT_Info constantValue() {
		return constantValue;
	}
	
	public boolean isSynthetic() {
		return isSynthetic;
	}

}
