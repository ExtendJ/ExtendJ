// Annotations may have one-dimensional array components.
// .result=COMPILE_PASS

@interface annot {
	String[] words();
}	

public class Test {
	@annot(words = { "array", "annotation", "component" })
	public void f() {
	}
}
