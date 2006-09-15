package bytecode;

import AST.*;
import java.util.*;
import AST.List;

public class Signatures {
  // simple parser framework
  String data;
  int pos;
  public Signatures(String s) {
    data = s;
    pos = 0;
  }

  public boolean next(String s) {
    for(int i = 0; i < s.length(); i++)
      if(data.charAt(pos + i) != s.charAt(i))
        return false;
    return true;
  }

  public void eat(String s) {
    for(int i = 0; i < s.length(); i++)
      if(data.charAt(pos + i) != s.charAt(i))
        error(s);
    pos += s.length();
  }

  public void error(String s) {
    throw new Error("Expected " + s + " but found " + data.substring(pos));
  }

  public String identifier() {
    int i = pos;
    while(Character.isJavaIdentifierPart(data.charAt(i)))
      i++;
    String result = data.substring(pos, i);
    pos = i;
    return result;
  }

  public boolean eof() {
    return pos == data.length();
  }

  // 4.4.4 Signatures

  public static class ClassSignature extends Signatures {
    public ClassSignature(String s) {
      super(s);
      classSignature();
    }
    void classSignature() {
      if(next("<"))
        formalTypeParameters();
      superclassSignature();
      while(!eof()) {
        superinterfaceSignature();
      }
    }

    public boolean hasFormalTypeParameters() { return typeParameters != null; }
    public List typeParameters() { return typeParameters; }
  }

  public static class MethodSignature extends Signatures {
    public MethodSignature(String s) {
      super(s);
      methodTypeSignature();
    }
    void methodTypeSignature() {
      if(next("<"))
        formalTypeParameters();
      eat("(");
      while(!next(")")) {
        parameterTypes.add(typeSignature());
      }
      eat(")");
      returnType = parseReturnType();
      while(!eof()) {
        exceptionList.add(throwsSignature());
      }
    }
    Access parseReturnType() {
      if(next("V")) {
        eat("V");
        return new PrimitiveTypeAccess("void");
      }
      else {
        return typeSignature();
      }
    }

    Access throwsSignature() {
      eat("^");
      if(next("L")) {
        return classTypeSignature();
      }
      else {
        return typeVariableSignature();
      }
    }

    public boolean hasFormalTypeParameters() { return typeParameters != null; }
    public List typeParameters() { return typeParameters; }

    public Collection parameterTypes() { return parameterTypes; }
    protected Collection parameterTypes = new ArrayList();

    public List exceptionList() { return exceptionList; }
    public boolean hasExceptionList() { return exceptionList.getNumChild() != 0; }
    protected List exceptionList = new AST.List();

    protected Access returnType = null;
    public boolean hasReturnType() { return returnType != null; }
    public Access returnType() { return returnType; }
  }

  protected List typeParameters;

  void formalTypeParameters() {
    eat("<");
    typeParameters = new List();
    do {
      typeParameters.add(formalTypeParameter());
    } while(!next(">"));
    eat(">");
  }

  TypeVariable formalTypeParameter() {
    String id = identifier();
    List bounds = new List();
    bounds.add(classBound());
    while(next(":")) {
      bounds.add(interfaceBound());
    }
    return new TypeVariable(new Modifiers(new List()), id, new List(), bounds);
  }

  Access classBound() {
    eat(":");
    if(nextIsFieldTypeSignature()) {
      return fieldTypeSignature();
    }
    else {
      return new TypeAccess("java.lang", "Object");
    }
  }

  Access interfaceBound() {
    eat(":");
    return fieldTypeSignature();
  }

  void superclassSignature() {
    classTypeSignature();
  }

  void superinterfaceSignature() {
    classTypeSignature();
  }

  Access fieldTypeSignature() {
    if(next("L"))
      return classTypeSignature();
    else if(next("["))
      return arrayTypeSignature();
    else if(next("T"))
      return typeVariableSignature();
    else
      error("L or [ or T");
    return null; // error never returns
  }
  boolean nextIsFieldTypeSignature() {
    return next("L") || next("[") || next("T");
  }

  Access classTypeSignature() {
    eat("L");
    // Package and Type Name
    StringBuffer packageName = new StringBuffer();
    String typeName = identifier();
    while(next("/")) {
      eat("/");
      if(packageName.length() != 0)
        packageName.append(".");
      packageName.append(typeName);
      typeName = identifier();
    }
    String[] names = typeName.split("\\$");
    Access a = new TypeAccess(packageName.toString(), names[0]);
    for(int i = 1; i < names.length; i++)
      a = a.qualifiesAccess(new TypeAccess(names[i]));
    if(next("<")) { // type arguments of top level type
      a = new ParTypeAccess(a, typeArguments());
    }
    while(next(".")) { // inner classes
      a = a.qualifiesAccess(classTypeSignatureSuffix());
    }
    eat(";");
    return a;
  }

  Access classTypeSignatureSuffix() {
    eat(".");
    String id = identifier();
    String[] names = id.split("\\$");
    Access a = new TypeAccess(names[0]);
    for(int i = 1; i < names.length; i++)
      a = a.qualifiesAccess(new TypeAccess(names[i]));
    if(next("<")) {
      a = new ParTypeAccess(a, typeArguments());
    }
    return a;
  }
  
  Access typeVariableSignature() {
    eat("T");
    String id = identifier();
    eat(";");
    return new TypeAccess(id);
  }

  List typeArguments() {
    eat("<");
    List list = new List();
    do {
      list.add(typeArgument());
    } while(!next(">"));
    eat(">");
    return list;
  }

  Access typeArgument() {
    if(next("*")) {
      eat("*");
      return new Wildcard();
    }
    else if(next("+")) {
      eat("+");
      return new WildcardExtends(fieldTypeSignature());
    }
    else if(next("-")) {
      eat("-");
      return new WildcardSuper(fieldTypeSignature());
    }
    else {
      return fieldTypeSignature();
    }
  }

  Access arrayTypeSignature() {
    eat("[");
    return new ArrayTypeAccess(typeSignature(), 1);
  }

  Access typeSignature() {
    if(nextIsFieldTypeSignature()) {
      return fieldTypeSignature();
    }
    else {
      return baseType();
    }
  }

  Access baseType() {
    if(next("B")) { eat("B"); return new PrimitiveTypeAccess("byte"); }
    else if(next("C")) { eat("C"); return new PrimitiveTypeAccess("char"); }
    else if(next("D")) { eat("D"); return new PrimitiveTypeAccess("double"); }
    else if(next("F")) { eat("F"); return new PrimitiveTypeAccess("float"); }
    else if(next("I")) { eat("I"); return new PrimitiveTypeAccess("int"); }
    else if(next("J")) { eat("J"); return new PrimitiveTypeAccess("long"); }
    else if(next("S")) { eat("S"); return new PrimitiveTypeAccess("short"); }
    else if(next("Z")) { eat("Z"); return new PrimitiveTypeAccess("boolean"); }
    error("baseType");
    return null; // error never returns
  }
      
  public static void main(String[] args) {
    //new Signatures("<T:Ljava/lang/Object;:Ljava/lang/String;>Ljava/lang/Object;").classSignature();
    //new ClassSignature("<T:Ljava/lang/Object;:Ljava/lang/String;>Ljava/lang/Object;").classSignature();
    new ClassSignature("Ljava/lang/Object;Ljava/lang/Comparable<Ljava/lang/String;>;");
  }
}


