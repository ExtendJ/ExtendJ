// .result=COMPILE_FAIL
import java.io.*;

// Taken from JSR335, part A

public class Test {
	interface X { void m() throws IOException; }
	interface Y { void m() throws EOFException; }
	interface Z { void m() throws ClassNotFoundException; }
	interface XY extends X, Y {}
	interface XYZ extends X, Y, Z {}

	// XY has descriptor ()->void throws EOFException
	// XYZ has descriptor ()->void (throws nothing)
	
	public static void main(String[] arg) {
		XYZ xyz = () -> { throws new EOFException(); };
	}
}
