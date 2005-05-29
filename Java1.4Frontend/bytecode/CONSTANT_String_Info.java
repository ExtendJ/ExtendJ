package bytecode;

import AST.Expr;
import AST.StringLiteral;

class CONSTANT_String_Info extends CONSTANT_Info {
	public int string_index;

	public CONSTANT_String_Info(Parser parser) {
		super(parser);
		string_index = p.u2();
	}

  public Expr expr() {
    CONSTANT_Utf8_Info i = (CONSTANT_Utf8_Info)p.constantPool[string_index];
    return new StringLiteral(i.string);
  }

	public String toString() {
		return "StringInfo: " + p.constantPool[string_index];
	}
}
