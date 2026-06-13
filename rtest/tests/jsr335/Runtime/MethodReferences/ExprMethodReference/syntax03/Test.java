// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	
	int i = 0;
	int j = 10;
	
	public interface A {
		void m();
	}
	
	public interface B {
		int m();
	}
	
	public interface C {
		void m(int i);
	}
	
	public A getA() {
		return () -> {
			for(int k = 0; k < j; k++)
				this.i = this.i + k;
		};
	}
	
	public int getI() {
		return i;
	}
	
	public void setJ(int j) {
		this.j = j;
	}
	
	public static Test getTest() {
		return new Test();
	}
	
	public static void main(String[] arg) throws InterruptedException {
		Test t = getTest();
		A a = t.getA();
		B b = t::getI;
		C c = t::setJ;
		t = new Test();
		c.m(6);
		t.setJ(20);
		Thread thread = new Thread((Runnable)a::m);
		thread.start();
		thread.join();
		testTrue("MethodRef", b.m() == 15);
	}
}
