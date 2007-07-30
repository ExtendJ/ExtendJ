package bytecode;

import AST.Expr;
import AST.StringLiteral;


class CONSTANT_Utf8_Info extends CONSTANT_Info {
	public String string;

	public CONSTANT_Utf8_Info(Parser parser) {
		super(parser);
		string = p.readUTF();
	}

	public String toString() {
		return "Utf8Info: " + string;
	}

  public Expr expr() {
    return new StringLiteral(string);
  }

	public String string() {
		return string;
	}
}
