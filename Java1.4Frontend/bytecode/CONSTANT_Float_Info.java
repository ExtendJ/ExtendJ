package bytecode;

import AST.Expr;
import AST.FPLiteral;


class CONSTANT_Float_Info extends CONSTANT_Info {
	public float value;

	public CONSTANT_Float_Info(Parser parser) {
		super(parser);
		value = p.readFloat();
	}

	public String toString() {
		return "FloatInfo: " + Float.toString(value);
	}

	public Expr expr() {
		return new FPLiteral(Float.toString(value));
	}
}