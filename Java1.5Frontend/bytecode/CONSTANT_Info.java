package bytecode;

import AST.Expr;


class CONSTANT_Info {
	protected Parser p;
	public CONSTANT_Info(Parser parser) {
		p = parser;
		
	}
	public Expr expr() {
    throw new Error("CONSTANT_info.expr() should not be computed for " + getClass().getName());
	}
	public Expr exprAsBoolean() {
		return expr();
	}
}
