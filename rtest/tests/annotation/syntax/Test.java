// Annotations can come after public/protected/private modifiers
// .result=COMPILE_PASS
// .classpath=@TMP_DIR@
public class Test {
	public @Boop <A extends String> A toString(A str) {
		return str;
	}
}

@interface Boop {
}
