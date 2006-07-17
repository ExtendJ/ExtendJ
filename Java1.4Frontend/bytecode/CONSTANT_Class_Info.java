package bytecode;

import AST.Access;
import AST.Dot;
import AST.IdUse;
import AST.ParseName;
import AST.List;


class CONSTANT_Class_Info extends CONSTANT_Info {
	public int name_index;

	public CONSTANT_Class_Info(Parser parser) {
		super(parser);
		name_index = p.u2();
	}

	public String toString() {
		return "ClassInfo: " + name();
	}

	public String name() {
		String name = ((CONSTANT_Utf8_Info) this.p.constantPool[name_index]).string();
		//name = name.replaceAll("\\/", ".");
		name = name.replace('/', '.');
		return name;
	}

	public String simpleName() {
		String name = name();
		name = name.replace('$', '.');
    int pos = name.lastIndexOf('.');
    return name.substring(pos + 1, name.length());
	}
	
	public String packageDecl() {
		String name = name();
		name = name.replace('$', '.');
    int pos = name.lastIndexOf('.');
    if(pos == -1)
      return "";
    return name.substring(0, pos);
	}

	public Access access() {
		String name = name();
		//name = name.replaceAll("\\$", ".");
		name = name.replace('$', '.');
		//String[] names = name.split("\\.");
		//Access result = new ParseName(new IdUse(names[0]));
		//for (int i = 1; i < names.length; i++)
		//	result = new Dot(result, new ParseName(new IdUse(names[i])));
		int index = -1;
    int pos = 0;
    Access result = null;
    do {
      pos = name.indexOf('.', index+1);
      if(pos == -1)
        pos = name.length();
      String s = name.substring(index+1, pos);
      if(index == -1) {
		    result = new ParseName(new IdUse(s));
      }
      else {
			  result = new Dot(result, new ParseName(new IdUse(s)));
      }
      index = pos;
    } while(pos != name.length());
    return result;
	}
}
