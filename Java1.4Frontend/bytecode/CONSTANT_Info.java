package bytecode;

import AST.Expr;


class CONSTANT_Info {
	protected Parser p;
	public CONSTANT_Info(Parser parser) {
		p = parser;
		
	}
	public Expr expr() {
		return null;
	}
	public Expr exprAsBoolean() {
		return expr();
	}
}