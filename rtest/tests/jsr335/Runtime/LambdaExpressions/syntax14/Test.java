// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public B m(boolean b);
	}
	
	public interface B {
		public StringBuilder m(int len);
	}
	
	public static A getA() {
		String v1 = "v1";
		String v2 = "v2";
		
		return b -> {
			if(b)
				return len -> {
					StringBuilder str = new StringBuilder();
					str.append(v1);
					char cont = v1.charAt(1);
					for(int i = 1; i < len; i++) {
						str.append(cont);
					}
					return str;
				};
			else
				return (int len) -> {
					StringBuilder str = new StringBuilder();
					str.append(v2);
					char cont = v2.charAt(1);
					for(int i = 1; i < len; i++) {
						str.append(cont);
					}
					return str;
				};
		};
	}
	
	public static void main(String[] arg) {
		A a = getA();
		B b1 = a.m(true);
		B b2 = a.m(false);
		
		String res1 = b1.m(5).toString();
		String res2 = b2.m(8).toString();
		System.out.println(res1);
		System.out.println(res2);
	}
}
