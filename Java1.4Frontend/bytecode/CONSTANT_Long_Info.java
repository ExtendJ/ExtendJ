package bytecode;

import AST.Expr;
import AST.LongLiteral;


class CONSTANT_Long_Info extends CONSTANT_Info {
	public long value;

	public CONSTANT_Long_Info(Parser parser) {
		super(parser);
		value = p.readLong();
	}

	public String toString() {
		return "LongInfo: " + Long.toString(value);
	}

	public Expr expr() {
		//return new LongLiteral(Long.toString(value));
		return new LongLiteral("0x" + Long.toHexString(value));
	}
}
