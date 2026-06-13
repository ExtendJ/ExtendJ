// .result=COMPILE_PASS

public class Test {
	
	interface A {
		B m(int a);
	}
	interface B {
		int m(int b);
	}
	
	public int meth1(int b) {
		return b + 5;
	}
	public short meth2(int b) {
		if(b > 4)
			return 3;
		else
			return 2;
	}
	
	public void method(int i) {
		Test t = new Test();
		A a = p -> {
			if(p < 2)
				return this::meth1;
			else
				return t::meth2;
		};
	}
}